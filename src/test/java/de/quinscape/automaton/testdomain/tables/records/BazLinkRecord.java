/*
 * This file is generated by jOOQ.
*/
package de.quinscape.automaton.testdomain.tables.records;


import de.quinscape.automaton.testdomain.tables.BazLink;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
@Entity
@Table(name = "baz_link", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"baz_id", "value_id"})
}, indexes = {
    @Index(name = "baz_link_baz_id_value_id_key", unique = true, columnList = "baz_id ASC, value_id ASC"),
    @Index(name = "pk_baz_link", unique = true, columnList = "id ASC")
})
public class BazLinkRecord extends UpdatableRecordImpl<BazLinkRecord> implements Record3<String, String, String> {

    private static final long serialVersionUID = 1215473770;

    /**
     * Setter for <code>public.baz_link.id</code>.
     */
    public void setId(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.baz_link.id</code>.
     */
    @Id
    @Column(name = "id", unique = true, nullable = false, length = 36)
    @NotNull
    @Size(max = 36)
    public String getId() {
        return (String) get(0);
    }

    /**
     * Setter for <code>public.baz_link.baz_id</code>.
     */
    public void setBazId(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.baz_link.baz_id</code>.
     */
    @Column(name = "baz_id", nullable = false, length = 36)
    @NotNull
    @Size(max = 36)
    public String getBazId() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.baz_link.value_id</code>.
     */
    public void setValueId(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.baz_link.value_id</code>.
     */
    @Column(name = "value_id", nullable = false, length = 36)
    @NotNull
    @Size(max = 36)
    public String getValueId() {
        return (String) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<String> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<String, String, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<String, String, String> valuesRow() {
        return (Row3) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return BazLink.BAZ_LINK.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return BazLink.BAZ_LINK.BAZ_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return BazLink.BAZ_LINK.VALUE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getBazId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getValueId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getBazId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getValueId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BazLinkRecord value1(String value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BazLinkRecord value2(String value) {
        setBazId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BazLinkRecord value3(String value) {
        setValueId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BazLinkRecord values(String value1, String value2, String value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached BazLinkRecord
     */
    public BazLinkRecord() {
        super(BazLink.BAZ_LINK);
    }

    /**
     * Create a detached, initialised BazLinkRecord
     */
    public BazLinkRecord(String id, String bazId, String valueId) {
        super(BazLink.BAZ_LINK);

        set(0, id);
        set(1, bazId);
        set(2, valueId);
    }
}