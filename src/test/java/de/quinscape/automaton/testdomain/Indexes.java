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

import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.Internal;


/**
 * A class modelling indexes of tables of the <code>public</code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index PK_APP_USER = Indexes0.PK_APP_USER;
    public static final Index UC_APP_USER_LOGIN = Indexes0.UC_APP_USER_LOGIN;
    public static final Index FKI_BAZ_OWNER_ID = Indexes0.FKI_BAZ_OWNER_ID;
    public static final Index PK_BAZ = Indexes0.PK_BAZ;
    public static final Index BAZ_LINK_BAZ_ID_VALUE_ID_KEY = Indexes0.BAZ_LINK_BAZ_ID_VALUE_ID_KEY;
    public static final Index PK_BAZ_LINK = Indexes0.PK_BAZ_LINK;
    public static final Index PK_BAZ_VALUE = Indexes0.PK_BAZ_VALUE;
    public static final Index PK_FOO = Indexes0.PK_FOO;
    public static final Index FOO_TYPE_NAME_KEY = Indexes0.FOO_TYPE_NAME_KEY;
    public static final Index PK_FOO_TYPE = Indexes0.PK_FOO_TYPE;
    public static final Index PK_NODE = Indexes0.PK_NODE;
    public static final Index UC_NODE_NAME = Indexes0.UC_NODE_NAME;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Indexes0 {
        public static Index PK_APP_USER = Internal.createIndex("pk_app_user", AppUser.APP_USER, new OrderField[] { AppUser.APP_USER.ID }, true);
        public static Index UC_APP_USER_LOGIN = Internal.createIndex("uc_app_user_login", AppUser.APP_USER, new OrderField[] { AppUser.APP_USER.LOGIN }, true);
        public static Index FKI_BAZ_OWNER_ID = Internal.createIndex("fki_baz_owner_id", Baz.BAZ, new OrderField[] { Baz.BAZ.OWNER_ID }, false);
        public static Index PK_BAZ = Internal.createIndex("pk_baz", Baz.BAZ, new OrderField[] { Baz.BAZ.ID }, true);
        public static Index BAZ_LINK_BAZ_ID_VALUE_ID_KEY = Internal.createIndex("baz_link_baz_id_value_id_key", BazLink.BAZ_LINK, new OrderField[] { BazLink.BAZ_LINK.BAZ_ID, BazLink.BAZ_LINK.VALUE_ID }, true);
        public static Index PK_BAZ_LINK = Internal.createIndex("pk_baz_link", BazLink.BAZ_LINK, new OrderField[] { BazLink.BAZ_LINK.ID }, true);
        public static Index PK_BAZ_VALUE = Internal.createIndex("pk_baz_value", BazValue.BAZ_VALUE, new OrderField[] { BazValue.BAZ_VALUE.ID }, true);
        public static Index PK_FOO = Internal.createIndex("pk_foo", Foo.FOO, new OrderField[] { Foo.FOO.ID }, true);
        public static Index FOO_TYPE_NAME_KEY = Internal.createIndex("foo_type_name_key", FooType.FOO_TYPE, new OrderField[] { FooType.FOO_TYPE.NAME }, true);
        public static Index PK_FOO_TYPE = Internal.createIndex("pk_foo_type", FooType.FOO_TYPE, new OrderField[] { FooType.FOO_TYPE.ORDINAL }, true);
        public static Index PK_NODE = Internal.createIndex("pk_node", Node.NODE, new OrderField[] { Node.NODE.ID }, true);
        public static Index UC_NODE_NAME = Internal.createIndex("uc_node_name", Node.NODE, new OrderField[] { Node.NODE.NAME }, true);
    }
}
