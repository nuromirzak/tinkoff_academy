package ru.tinkoff.edu.java.scrapper.services;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.dtos.StackoverflowQuestionResponse;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class StackoverflowClient {
    private WebClient webClient;

    public StackoverflowQuestionResponse getQuestionById(Long id) {
        return webClient.get()
                .uri(uri -> uri.path("/questions/{id}")
                        .queryParam("site", "stackoverflow")
                        .build(id))
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(this::convertJsonToStackoverflowQuestion)
                .block();
    }

    private StackoverflowQuestionResponse convertJsonToStackoverflowQuestion(JsonNode jsonNode) {
        StackoverflowQuestionResponse question = new StackoverflowQuestionResponse();
        JsonNode itemsArray = jsonNode.get("items");
        if (itemsArray != null && itemsArray.isArray() && itemsArray.size() > 0) {
            JsonNode firstItem = itemsArray.get(0);
            if (firstItem != null) {
                JsonNode owner = firstItem.get("owner");
                if (owner != null) {
                    JsonNode userIdNode = owner.get("user_id");
                    if (userIdNode != null) {
                        question.setOwnerId(userIdNode.asLong());
                    }
                    JsonNode displayNameNode = owner.get("display_name");
                    if (displayNameNode != null) {
                        question.setOwnerName(displayNameNode.asText());
                    }
                }
                JsonNode titleNode = firstItem.get("title");
                if (titleNode != null) {
                    question.setTitle(titleNode.asText());
                }
                JsonNode answerCountNode = firstItem.get("answer_count");
                if (answerCountNode != null) {
                    question.setAnswerCount(answerCountNode.asInt());
                }
                JsonNode scoreNode = firstItem.get("score");
                if (scoreNode != null) {
                    question.setScore(scoreNode.asInt());
                }
                JsonNode tags = firstItem.get("tags");
                if (tags != null && tags.isArray()) {
                    List<String> tagsList = new ArrayList<>();
                    for (JsonNode tag : tags) {
                        if (tag != null) {
                            tagsList.add(tag.asText());
                        }
                    }
                    question.setTags(tagsList);
                }
                JsonNode creationDateNode = firstItem.get("creation_date");
                if (creationDateNode != null) {
                    Date date = new Date(creationDateNode.asLong() * 1000);
                    OffsetDateTime offsetDateTime = date.toInstant().atOffset(ZoneOffset.UTC);
                    question.setCreationDate(offsetDateTime);
                }
            }
        }
        return question;
    }

    @Autowired
    public void setWebClient(@Qualifier("stackoverflow_client") WebClient webClient) {
        this.webClient = webClient;
    }
}

