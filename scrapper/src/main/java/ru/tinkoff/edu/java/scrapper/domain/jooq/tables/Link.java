/*
 * This file is generated by jOOQ.
 */

package ru.tinkoff.edu.java.scrapper.domain.jooq.tables;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.NotNull;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function5;
import org.jooq.Identity;
import org.jooq.JSON;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row5;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import ru.tinkoff.edu.java.scrapper.domain.jooq.DefaultSchema;
import ru.tinkoff.edu.java.scrapper.domain.jooq.Keys;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.LinkRecord;

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
public class Link extends TableImpl<LinkRecord> {

    /**
     * The reference instance of <code>LINK</code>
     */
    public static final Link LINK = new Link();
    private static final long serialVersionUID = 1L;
    /**
     * The column <code>LINK.LINK_ID</code>. Уникальный идентификатор ссылки в
     * базе данных
     */
    public final TableField<LinkRecord, Integer> LINK_ID = createField(DSL.name("LINK_ID"),
        SQLDataType.INTEGER.nullable(false).identity(true),
        this,
        "Уникальный идентификатор ссылки в базе данных"
    );
    /**
     * The column <code>LINK.URL</code>. URL отслеживаемой ссылки
     */
    public final TableField<LinkRecord, String> URL =
        createField(DSL.name("URL"), SQLDataType.VARCHAR(256).nullable(false), this, "URL отслеживаемой ссылки");
    /**
     * The column <code>LINK.LAST_UPDATED</code>. Время последнего действия с
     * этой ссылкой
     */
    public final TableField<LinkRecord, LocalDateTime> LAST_UPDATED = createField(DSL.name("LAST_UPDATED"),
        SQLDataType.LOCALDATETIME(6).nullable(false),
        this,
        "Время последнего действия с этой ссылкой"
    );
    /**
     * The column <code>LINK.LAST_SCRAPPED</code>. Время последнего скрапинга
     * этой ссылки
     */
    public final TableField<LinkRecord, LocalDateTime> LAST_SCRAPPED = createField(DSL.name("LAST_SCRAPPED"),
        SQLDataType.LOCALDATETIME(6).nullable(false),
        this,
        "Время последнего скрапинга этой ссылки"
    );
    /**
     * The column <code>LINK.JSON_PROPS</code>. Дополнительные свойства ссылки в
     * формате JSON
     */
    public final TableField<LinkRecord, JSON> JSON_PROPS =
        createField(DSL.name("JSON_PROPS"), SQLDataType.JSON, this, "Дополнительные свойства ссылки в формате JSON");

    private Link(Name alias, Table<LinkRecord> aliased) {
        this(alias, aliased, null);
    }

    private Link(Name alias, Table<LinkRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>LINK</code> table reference
     */
    public Link(String alias) {
        this(DSL.name(alias), LINK);
    }

    /**
     * Create an aliased <code>LINK</code> table reference
     */
    public Link(Name alias) {
        this(alias, LINK);
    }

    /**
     * Create a <code>LINK</code> table reference
     */
    public Link() {
        this(DSL.name("LINK"), null);
    }

    public <O extends Record> Link(Table<O> child, ForeignKey<O, LinkRecord> key) {
        super(child, key, LINK);
    }

    /**
     * The class holding records for this type
     */
    @Override
    @NotNull
    public Class<LinkRecord> getRecordType() {
        return LinkRecord.class;
    }

    @Override
    @NotNull
    public Schema getSchema() {
        return aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    @NotNull
    public Identity<LinkRecord, Integer> getIdentity() {
        return (Identity<LinkRecord, Integer>) super.getIdentity();
    }

    @Override
    @NotNull
    public UniqueKey<LinkRecord> getPrimaryKey() {
        return Keys.CONSTRAINT_2;
    }

    @Override
    @NotNull
    public List<UniqueKey<LinkRecord>> getUniqueKeys() {
        return Arrays.asList(Keys.CONSTRAINT_23);
    }

    @Override
    @NotNull
    public Link as(String alias) {
        return new Link(DSL.name(alias), this);
    }

    @Override
    @NotNull
    public Link as(Name alias) {
        return new Link(alias, this);
    }

    @Override
    @NotNull
    public Link as(Table<?> alias) {
        return new Link(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    @NotNull
    public Link rename(String name) {
        return new Link(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    @NotNull
    public Link rename(Name name) {
        return new Link(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    @NotNull
    public Link rename(Table<?> name) {
        return new Link(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row5 type methods
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Row5<Integer, String, LocalDateTime, LocalDateTime, JSON> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function5<? super Integer, ? super String, ? super LocalDateTime, ? super LocalDateTime, ? super JSON, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(
        Class<U> toType,
        Function5<? super Integer, ? super String, ? super LocalDateTime, ? super LocalDateTime, ? super JSON, ? extends U> from
    ) {
        return convertFrom(toType, Records.mapping(from));
    }
}
