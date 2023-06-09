package ru.tinkoff.edu.java.scrapper.dtos;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "link")
public final class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "link_id")
    private Long linkId;

    @Column(name = "url")
    private String url;

    @Column(name = "last_updated")
    private OffsetDateTime lastUpdated;

    @Type(JsonType.class)
    @Column(columnDefinition = "json", name = "json_props")
    private String jsonProps;

    @Column(name = "last_scrapped")
    private OffsetDateTime lastScrapped;
}
