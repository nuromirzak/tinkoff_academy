/*
 * This file is generated by jOOQ.
 */

package ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records;


import jakarta.validation.constraints.Size;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Field;
import org.jooq.JSON;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Link;
import javax.annotation.processing.Generated;
import java.beans.ConstructorProperties;
import java.time.LocalDateTime;


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
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class LinkRecord extends UpdatableRecordImpl<LinkRecord>
        implements Record5<Integer, String, LocalDateTime, LocalDateTime, JSON> {

    private static final long serialVersionUID = 1L;

    /**
     * Create a detached LinkRecord
     */
    public LinkRecord() {
        super(Link.LINK);
    }

    /**
     * Create a detached, initialised LinkRecord
     */
    @ConstructorProperties({"linkId", "url", "lastUpdated", "lastScrapped", "jsonProps"})
    public LinkRecord(@NotNull Integer linkId, @NotNull String url, @NotNull LocalDateTime lastUpdated,
                      @Nullable LocalDateTime lastScrapped, @Nullable JSON jsonProps) {
        super(Link.LINK);

        setLinkId(linkId);
        setUrl(url);
        setLastUpdated(lastUpdated);
        setLastScrapped(lastScrapped);
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
            setLastScrapped(value.getLastScrapped());
            setJsonProps(value.getJsonProps());
        }
    }

    /**
     * Getter for <code>LINK.LINK_ID</code>. Уникальный идентификатор ссылки в
     * базе данных
     */
    @NotNull
    public Integer getLinkId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>LINK.LINK_ID</code>. Уникальный идентификатор ссылки в
     * базе данных
     */
    public void setLinkId(@NotNull Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>LINK.URL</code>. URL отслеживаемой ссылки
     */
    @jakarta.validation.constraints.NotNull
    @Size(max = 256)
    @NotNull
    public String getUrl() {
        return (String) get(1);
    }

    /**
     * Setter for <code>LINK.URL</code>. URL отслеживаемой ссылки
     */
    public void setUrl(@NotNull String value) {
        set(1, value);
    }

    /**
     * Getter for <code>LINK.LAST_UPDATED</code>. Время последнего действия с
     * этой ссылкой
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public LocalDateTime getLastUpdated() {
        return (LocalDateTime) get(2);
    }

    /**
     * Setter for <code>LINK.LAST_UPDATED</code>. Время последнего действия с
     * этой ссылкой
     */
    public void setLastUpdated(@NotNull LocalDateTime value) {
        set(2, value);
    }

    /**
     * Getter for <code>LINK.LAST_SCRAPPED</code>. Время последнего скрапинга
     * этой ссылки
     */
    @Nullable
    public LocalDateTime getLastScrapped() {
        return (LocalDateTime) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * Setter for <code>LINK.LAST_SCRAPPED</code>. Время последнего скрапинга
     * этой ссылки
     */
    public void setLastScrapped(@Nullable LocalDateTime value) {
        set(3, value);
    }

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    /**
     * Getter for <code>LINK.JSON_PROPS</code>. Дополнительные свойства ссылки в
     * формате JSON
     */
    @Nullable
    public JSON getJsonProps() {
        return (JSON) get(4);
    }

    /**
     * Setter for <code>LINK.JSON_PROPS</code>. Дополнительные свойства ссылки в
     * формате JSON
     */
    public void setJsonProps(@Nullable JSON value) {
        set(4, value);
    }

    @Override
    @NotNull
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    @Override
    @NotNull
    public Row5<Integer, String, LocalDateTime, LocalDateTime, JSON> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    @Override
    @NotNull
    public Row5<Integer, String, LocalDateTime, LocalDateTime, JSON> valuesRow() {
        return (Row5) super.valuesRow();
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
    public Field<LocalDateTime> field4() {
        return Link.LINK.LAST_SCRAPPED;
    }

    @Override
    @NotNull
    public Field<JSON> field5() {
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
    public LocalDateTime component4() {
        return getLastScrapped();
    }

    @Override
    @Nullable
    public JSON component5() {
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
    public LocalDateTime value4() {
        return getLastScrapped();
    }

    @Override
    @Nullable
    public JSON value5() {
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

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public LinkRecord value4(@Nullable LocalDateTime value) {
        setLastScrapped(value);
        return this;
    }

    @Override
    @NotNull
    public LinkRecord value5(@Nullable JSON value) {
        setJsonProps(value);
        return this;
    }

    @Override
    @NotNull
    public LinkRecord values(@NotNull Integer value1, @NotNull String value2, @NotNull LocalDateTime value3,
                             @Nullable LocalDateTime value4, @Nullable JSON value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }
}
