package ru.tinkoff.edu.java.scrapper.dtos;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public final class GithubRepoResponse {
    private String fullName;
    private String description;
    private String htmlUrl;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
