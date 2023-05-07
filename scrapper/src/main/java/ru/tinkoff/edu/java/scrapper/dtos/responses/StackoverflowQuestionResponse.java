package ru.tinkoff.edu.java.scrapper.dtos.responses;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class StackoverflowQuestionResponse extends LinkProperties {
    public static final String DISCRIMINATOR = "link_properties.stackoverflow_question";

    private static final String TO = "\" to \"";
    private static final String DOT_NEWLINE = "\".\n";

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
        updateDescription.append("Question ").append(newStackoverflowQuestionResponse.getTitle())
            .append(" has a new update!\n");
        if (Objects.equals(newStackoverflowQuestionResponse.getTitle(), title)) {
            updateDescription.append("Title has been changed from \"").append(title).append(TO)
                .append(newStackoverflowQuestionResponse.getTitle()).append(DOT_NEWLINE);
        }
        if (!Objects.equals(newStackoverflowQuestionResponse.getAnswerCount(), answerCount)) {
            updateDescription.append("Answer count has been changed from \"").append(answerCount).append(TO)
                .append(newStackoverflowQuestionResponse.getAnswerCount()).append(DOT_NEWLINE);
        }
        if (!Objects.equals(newStackoverflowQuestionResponse.getScore(), score)) {
            updateDescription.append("Score has been changed from \"").append(score).append(TO)
                .append(newStackoverflowQuestionResponse.getScore()).append(DOT_NEWLINE);
        }
        if (!Objects.equals(newStackoverflowQuestionResponse.getTags(), tags)) {
            updateDescription.append("Tags has been changed from \"").append(tags).append(TO)
                .append(newStackoverflowQuestionResponse.getTags()).append(DOT_NEWLINE);
        }
        return updateDescription.toString();
    }

    @Override
    public String getType() {
        return DISCRIMINATOR;
    }
}
