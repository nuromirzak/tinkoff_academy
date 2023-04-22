/*
 * This file is generated by jOOQ.
 */
package ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records;


import jakarta.validation.constraints.Size;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;

import javax.annotation.processing.Generated;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Field;
import org.jooq.JSON;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;

import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Link;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.17.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class LinkRecord extends UpdatableRecordImpl<LinkRecord> implements Record4<Integer, String, LocalDateTime, JSON> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>LINK.LINK_ID</code>.
     */
    public void setLinkId(@NotNull Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>LINK.LINK_ID</code>.
     */
    @NotNull
    public Integer getLinkId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>LINK.URL</code>.
     */
    public void setUrl(@NotNull String value) {
        set(1, value);
    }

    /**
     * Getter for <code>LINK.URL</code>.
     */
    @jakarta.validation.constraints.NotNull
    @Size(max = 256)
    @NotNull
    public String getUrl() {
        return (String) get(1);
    }

    /**
     * Setter for <code>LINK.LAST_UPDATED</code>.
     */
    public void setLastUpdated(@NotNull LocalDateTime value) {
        set(2, value);
    }

    /**
     * Getter for <code>LINK.LAST_UPDATED</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public LocalDateTime getLastUpdated() {
        return (LocalDateTime) get(2);
    }

    /**
     * Setter for <code>LINK.JSON_PROPS</code>.
     */
    public void setJsonProps(@Nullable JSON value) {
        set(3, value);
    }

    /**
     * Getter for <code>LINK.JSON_PROPS</code>.
     */
    @Nullable
    public JSON getJsonProps() {
        return (JSON) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Row4<Integer, String, LocalDateTime, JSON> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    @Override
    @NotNull
    public Row4<Integer, String, LocalDateTime, JSON> valuesRow() {
        return (Row4) super.valuesRow();
    }

    @Override
    @NotNull
    public Field<Integer> field1() {
        return Link.LINK.LINK_ID;
    }

    @Override
    @NotNull
    public Field<String> field2() {
        return Link.LINK.URL;
    }

    @Override
    @NotNull
    public Field<LocalDateTime> field3() {
        return Link.LINK.LAST_UPDATED;
    }

    @Override
    @NotNull
    public Field<JSON> field4() {
        return Link.LINK.JSON_PROPS;
    }

    @Override
    @NotNull
    public Integer component1() {
        return getLinkId();
    }

    @Override
    @NotNull
    public String component2() {
        return getUrl();
    }

    @Override
    @NotNull
    public LocalDateTime component3() {
        return getLastUpdated();
    }

    @Override
    @Nullable
    public JSON component4() {
        return getJsonProps();
    }

    @Override
    @NotNull
    public Integer value1() {
        return getLinkId();
    }

    @Override
    @NotNull
    public String value2() {
        return getUrl();
    }

    @Override
    @NotNull
    public LocalDateTime value3() {
        return getLastUpdated();
    }

    @Override
    @Nullable
    public JSON value4() {
        return getJsonProps();
    }

    @Override
    @NotNull
    public LinkRecord value1(@NotNull Integer value) {
        setLinkId(value);
        return this;
    }

    @Override
    @NotNull
    public LinkRecord value2(@NotNull String value) {
        setUrl(value);
        return this;
    }

    @Override
    @NotNull
    public LinkRecord value3(@NotNull LocalDateTime value) {
        setLastUpdated(value);
        return this;
    }

    @Override
    @NotNull
    public LinkRecord value4(@Nullable JSON value) {
        setJsonProps(value);
        return this;
    }

    @Override
    @NotNull
    public LinkRecord values(@NotNull Integer value1, @NotNull String value2, @NotNull LocalDateTime value3, @Nullable JSON value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached LinkRecord
     */
    public LinkRecord() {
        super(Link.LINK);
    }

    /**
     * Create a detached, initialised LinkRecord
     */
    @ConstructorProperties({ "linkId", "url", "lastUpdated", "jsonProps" })
    public LinkRecord(@NotNull Integer linkId, @NotNull String url, @NotNull LocalDateTime lastUpdated, @Nullable JSON jsonProps) {
        super(Link.LINK);

        setLinkId(linkId);
        setUrl(url);
        setLastUpdated(lastUpdated);
        setJsonProps(jsonProps);
    }

    /**
     * Create a detached, initialised LinkRecord
     */
    public LinkRecord(ru.tinkoff.edu.java.scrapper.domain.jooq.tables.pojos.Link value) {
        super(Link.LINK);

        if (value != null) {
            setLinkId(value.getLinkId());
            setUrl(value.getUrl());
            setLastUpdated(value.getLastUpdated());
            setJsonProps(value.getJsonProps());
        }
    }
}