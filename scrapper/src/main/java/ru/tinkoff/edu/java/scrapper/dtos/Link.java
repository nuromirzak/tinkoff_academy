package ru.tinkoff.edu.java.scrapper.dtos;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public final class Link {
    @Id
    private Long linkId;
    private String url;
    private OffsetDateTime lastUpdated;
    private Object jsonProps;
}
