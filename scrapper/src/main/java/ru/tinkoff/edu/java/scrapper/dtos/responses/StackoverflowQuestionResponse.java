package ru.tinkoff.edu.java.scrapper.dtos.responses;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
public class StackoverflowQuestionResponse extends LinkProperties {
    public static final String DISCRIMINATOR = "link_properties.stackoverflow_question";

    private Long ownerId;
    private String ownerName;
    private String title;
    private Integer answerCount;
    private Integer score;
    private List<String> tags;
    private OffsetDateTime creationDate;
    private OffsetDateTime lastActivityDate;

    public String getDifferenceMessageBetween(StackoverflowQuestionResponse newStackoverflowQuestionResponse) {
        StringBuilder updateDescription = new StringBuilder();
        updateDescription.append("Question ").append(newStackoverflowQuestionResponse.getTitle()).append(" has a new update!\n");
        if (Objects.equals(newStackoverflowQuestionResponse.getTitle(), title)) {
            updateDescription.append("Title has been changed from \"").append(title).append("\" to \"").append(newStackoverflowQuestionResponse.getTitle()).append("\".\n");
        }
        if (!Objects.equals(newStackoverflowQuestionResponse.getAnswerCount(), answerCount)) {
            updateDescription.append("Answer count has been changed from \"").append(answerCount).append("\" to \"").append(newStackoverflowQuestionResponse.getAnswerCount()).append("\".\n");
        }
        if (!Objects.equals(newStackoverflowQuestionResponse.getScore(), score)) {
            updateDescription.append("Score has been changed from \"").append(score).append("\" to \"").append(newStackoverflowQuestionResponse.getScore()).append("\".\n");
        }
        if (!Objects.equals(newStackoverflowQuestionResponse.getTags(), tags)) {
            updateDescription.append("Tags has been changed from \"").append(tags).append("\" to \"").append(newStackoverflowQuestionResponse.getTags()).append("\".\n");
        }
        return updateDescription.toString();
    }

    @Override
    public String getType() {
        return DISCRIMINATOR;
    }
}
