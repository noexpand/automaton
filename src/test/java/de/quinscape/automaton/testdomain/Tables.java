/*
 * This file is generated by jOOQ.
*/
package de.quinscape.automaton.testdomain;


import de.quinscape.automaton.testdomain.tables.AppUser;
import de.quinscape.automaton.testdomain.tables.Baz;
import de.quinscape.automaton.testdomain.tables.BazLink;
import de.quinscape.automaton.testdomain.tables.BazValue;
import de.quinscape.automaton.testdomain.tables.Foo;
import de.quinscape.automaton.testdomain.tables.FooType;
import de.quinscape.automaton.testdomain.tables.Node;

import javax.annotation.Generated;


/**
 * Convenience access to all tables in public
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * The table <code>public.app_user</code>.
     */
    public static final AppUser APP_USER = de.quinscape.automaton.testdomain.tables.AppUser.APP_USER;

    /**
     * The table <code>public.baz</code>.
     */
    public static final Baz BAZ = de.quinscape.automaton.testdomain.tables.Baz.BAZ;

    /**
     * The table <code>public.baz_link</code>.
     */
    public static final BazLink BAZ_LINK = de.quinscape.automaton.testdomain.tables.BazLink.BAZ_LINK;

    /**
     * The table <code>public.baz_value</code>.
     */
    public static final BazValue BAZ_VALUE = de.quinscape.automaton.testdomain.tables.BazValue.BAZ_VALUE;

    /**
     * The table <code>public.foo</code>.
     */
    public static final Foo FOO = de.quinscape.automaton.testdomain.tables.Foo.FOO;

    /**
     * The table <code>public.foo_type</code>.
     */
    public static final FooType FOO_TYPE = de.quinscape.automaton.testdomain.tables.FooType.FOO_TYPE;

    /**
     * The table <code>public.node</code>.
     */
    public static final Node NODE = de.quinscape.automaton.testdomain.tables.Node.NODE;
}
