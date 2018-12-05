package de.quinscape.automaton.runtime.provider;

import com.google.common.collect.Maps;
import de.quinscape.automaton.runtime.auth.AutomatonAuthentication;
import de.quinscape.automaton.runtime.config.ClientCrsfToken;
import de.quinscape.automaton.runtime.config.ScopeTableConfig;
import de.quinscape.automaton.runtime.i18n.TranslationService;
import de.quinscape.automaton.runtime.util.Base32;
import de.quinscape.automaton.runtime.util.LocaleUtil;
import de.quinscape.automaton.runtime.util.ProcessUtil;
import de.quinscape.automaton.runtime.ws.AutomatonClientConnectionImpl;
import de.quinscape.automaton.runtime.ws.AutomatonWebSocketHandler;
import de.quinscape.domainql.jsonb.JSONB;
import de.quinscape.spring.jsview.JsViewContext;
import de.quinscape.spring.jsview.JsViewProvider;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.csrf.CsrfToken;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static org.jooq.impl.DSL.*;

/**
 * Provides the process injections and all necessary infrastructure data to execute a process on the client side.
 */
public final class AutomatonJsViewProvider
    implements JsViewProvider
{

    private final static Logger log = LoggerFactory.getLogger(AutomatonJsViewProvider.class);

    /**
     * Key for current app name
     */
    private static final String APP_NAME = "appName";

    /**
     * Key for current locale
     */
    private static final String LOCALE = "locale";

    /**
     * Key for current process name
     */
    private static final String PROCESS_NAME = "processName";

    /**
     * Translation data key
     */
    private static final String TRANSLATIONS = "translations";

    /**
     * Name under which automaton-js process expects the initial process data
     */
    private static final String INJECTIONS = "injections";

    private static final String INPUT = "input";

    private static final String CONTEXT_PATH = "contextPath";

    private static final String APP_SCOPE = "appScope";

    private static final String USER_SCOPE = "userScope";

    private static final String AUTHENTICATION = "authentication";

    private static final String CSRF_TOKEN = "csrfToken";

    private static final String VALUE_PROP = "_value";

    private final ProcessInjectionService processInjectionService;

    private final boolean websocketEnabled;

    private final AutomatonWebSocketHandler automatonTestWebSocketHandler;

    private final TranslationService translationService;

    private final ScopeTableConfig scopeTableConfig;

    private final DSLContext dslContext;


    /**
     * Creates a new AutomatonJsViewProvider instance.
     *
     * @param dslContext                        JOOQ DSL context
     * @param processInjectionService           project injection service
     * @param translationService                translation service implementation
     * @param automatonTestWebSocketHandler     web socket handler, can be <code>null</code>
     * @param scopeTableConfig                  scope table config
     */
    public AutomatonJsViewProvider(
        DSLContext dslContext,
        ProcessInjectionService processInjectionService,
        TranslationService translationService,
        AutomatonWebSocketHandler automatonTestWebSocketHandler,
        ScopeTableConfig scopeTableConfig
    )
    {
        this.dslContext = dslContext;
        this.scopeTableConfig = scopeTableConfig;
        if (processInjectionService == null)
        {
            throw new IllegalArgumentException("processInjectionService can't be null");
        }

        if (translationService == null)
        {
            throw new IllegalArgumentException("translationService can't be null");
        }

        this.processInjectionService = processInjectionService;
        this.websocketEnabled = automatonTestWebSocketHandler != null;
        this.automatonTestWebSocketHandler = automatonTestWebSocketHandler;
        this.translationService = translationService;
    }


    private static Map<String, Object> userConfigAsMap(Record userConfigRecord)
    {
        final Field<?>[] fields = userConfigRecord.fields();
        final Map<String, Object> map = Maps.newHashMapWithExpectedSize(fields.length);
        for (Field<?> field : fields)
        {
            map.put(field.getName(), userConfigRecord.get(field));
        }

        return map;
    }


    @Override
    public void provide(JsViewContext context) throws Exception
    {
        provideProcessInjections(context);
        provideCommonData(context);
    }


    private void provideCommonData(JsViewContext context)
    {
        final CsrfToken token = (CsrfToken) context.getRequest().getAttribute("_csrf");
        final AutomatonAuthentication auth = AutomatonAuthentication.current();

        context.provideViewData(CONTEXT_PATH, context.getRequest().getContextPath());
        context.provideViewData(AUTHENTICATION, auth);
        context.provideViewData(CSRF_TOKEN, new ClientCrsfToken(token));

        final String appName = context.getJsView().getEntryPoint();
        context.provideViewData(APP_NAME, appName);
        context.provideViewData(APP_SCOPE, getAppScope(appName));
        context.provideViewData(USER_SCOPE, getUserScope(auth.getLogin()));

        if (websocketEnabled)
        {
            final String connectionId = Base32.uuid();
            context.provideViewData("connectionId", connectionId);

            automatonTestWebSocketHandler.register(
                new AutomatonClientConnectionImpl(
                    connectionId,
                    auth
                )
            );
        }
    }


    private JSONB getAppScope(String appName)
    {
        final Record record = dslContext.select()
            .from(
                scopeTableConfig.getAppScopeTable()
            )
            .where(
                field("name").eq(appName)

            ).fetchOne();

        if (scopeTableConfig.getAppScopeTable().field("scope").getType().equals(JSONB.class))
        {
            return record != null ? (JSONB) record.get("scope") : new JSONB();
        }

        return record != null ? JSONB.forValue(
            String.valueOf(
                record.get("scope")
            )
        ) : new JSONB();
    }


    private Map<String, Object> getUserScope(String login)
    {
        return dslContext.select()
            .from(
                scopeTableConfig.getUserScopeTable()
            )
            .where(
                field("login").eq(login)

            )
            .fetchOne(AutomatonJsViewProvider::userConfigAsMap);
    }


    private void provideProcessInjections(JsViewContext context) throws java.io.IOException
    {
        final String processName = getProcessName(context);

        log.debug("Provide for process '{}'", processName);

        final String appName = context.getJsView().getEntryPoint();

        final Map<String, Map<String, Object>> injections = processInjectionService.getProcessInjections(appName, processName, inputFromParams(context.getRequest().getParameterMap()));

        final String locale = LocaleUtil.localeCode(context.getRequest().getLocale());
        context.provideViewData(INJECTIONS, injections);
        context.provideViewData(INPUT, ProcessUtil.flattenParameterMap(context.getRequest()));
        context.provideViewData(LOCALE, locale);
        context.provideViewData(PROCESS_NAME, processName);

        context.provideViewData(
            TRANSLATIONS,
            translationService.getTranslations(
                locale,
                appName + "/" + processName
            )
        );
    }





    private Object inputFromParams(Map<String, String[]> parameterMap)
    {
        if (parameterMap.size() == 1)
        {
            final String[] value = parameterMap.get(VALUE_PROP);
            if (value != null)
            {
                // SCALAR INPUT
            }
        }

        // Object input
        return null;
    }


    private String getProcessName(JsViewContext context)
    {
        final String uri = getURI(context);

        final int endOfFirst = uri.indexOf('/', 1);
        if (endOfFirst < 0)
        {
            throw new IllegalStateException("Local URI does not match /{app}/**");
        }
        final int length = uri.length();
        if (endOfFirst == length - 1)
        {
            return context.getJsView().getEntryPoint();
        }
        final int start = endOfFirst + 1;
        final int pos = uri.indexOf('/', start);
        if (pos < 0)
        {
            return uri.substring(start);
        }
        return uri.substring(start, pos);
    }


    private String getURI(JsViewContext context)
    {
        final HttpServletRequest request = context.getRequest();
        return request.getRequestURI().substring(request.getContextPath().length());
    }
}
