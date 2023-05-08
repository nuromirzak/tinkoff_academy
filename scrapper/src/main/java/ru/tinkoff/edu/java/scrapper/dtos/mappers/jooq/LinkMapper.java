package ru.tinkoff.edu.java.scrapper.dtos.mappers.jooq;

import java.time.ZoneOffset;
import org.jooq.RecordMapper;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.LinkRecord;
import ru.tinkoff.edu.java.scrapper.dtos.Link;

public class LinkMapper implements RecordMapper<LinkRecord, Link> {
    @Override
    public Link map(LinkRecord linkRecord) {
        Link link = new Link();
        link.setLinkId(Long.valueOf(linkRecord.getLinkId()));
        link.setUrl(linkRecord.getUrl());
        link.setLastUpdated(linkRecord.getLastUpdated().atOffset(ZoneOffset.UTC));
        link.setLastScrapped(linkRecord.getLastScrapped().atOffset(ZoneOffset.UTC));
        link.setJsonProps(linkRecord.getJsonProps() == null ? null : linkRecord.getJsonProps().data());
        return link;
    }
}
