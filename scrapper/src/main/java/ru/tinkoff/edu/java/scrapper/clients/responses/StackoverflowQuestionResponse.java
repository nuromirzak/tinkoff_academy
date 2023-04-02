package ru.tinkoff.edu.java.scrapper.clients.responses;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class StackoverflowQuestionResponse {
    private Long ownerId;
    private String ownerName;
    private String title;
    private Integer answerCount;
    private Integer score;
    private List<String> tags;
    private OffsetDateTime creationDate;
    private OffsetDateTime lastActivityDate;
}
