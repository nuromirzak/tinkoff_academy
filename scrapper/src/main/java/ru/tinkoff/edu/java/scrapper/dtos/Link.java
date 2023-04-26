package ru.tinkoff.edu.java.scrapper.dtos;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import ru.tinkoff.edu.java.scrapper.dtos.responses.LinkProperties;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "link")
public final class Link {
    @Id
    @Column(name = "link_id")
    private Long linkId;

    @Column(name = "url")
    private String url;

    @Column(name = "last_updated")
    private OffsetDateTime lastUpdated;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb", name = "json_props")
    private LinkProperties jsonProps;
}
