package de.quinscape.automaton.runtime.logic;

import de.quinscape.domainql.DomainQL;
import de.quinscape.domainql.annotation.GraphQLLogic;
import de.quinscape.domainql.annotation.GraphQLMutation;
import de.quinscape.domainql.generic.DomainObject;
import de.quinscape.domainql.util.DomainObjectUtil;
import org.jooq.DSLContext;
import org.jooq.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;

import static org.jooq.impl.DSL.*;

@GraphQLLogic
public class AutomatonStandardLogic
{
    private final static Logger log = LoggerFactory.getLogger(AutomatonStandardLogic.class);


    private final DSLContext dslContext;
    private final DomainQL domainQL;


    public AutomatonStandardLogic(
        DSLContext dslContext,
        DomainQL domainQL
    )
    {
        this.dslContext = dslContext;
        this.domainQL = domainQL;
    }


    @GraphQLMutation
    public boolean storeDomainObject(
        @NotNull
        DomainObject domainObject
    )
    {
        log.debug("storeDomainObject: {}", domainObject);

        return DomainObjectUtil.insertOrUpdate(dslContext, domainQL, domainObject) == 1;
    }


    @GraphQLMutation
    public boolean deleteDomainObject(
        @NotNull
            String type,
        @NotNull
            String id
    )
    {
        final Table<?> jooqTable = domainQL.getJooqTable(type);
        final int count = dslContext.deleteFrom( jooqTable)
            .where(
                field(
                    name("id")
                )
                .eq(id)
            )
            .execute();

        return count == 1;
    }
}
