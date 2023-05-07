/*
 * This file is generated by jOOQ.
 */

package ru.tinkoff.edu.java.scrapper.domain.jooq.tables.pojos;

import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
public class Chat implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long chatId;
    private LocalDateTime regDate;

    public Chat() {
    }

    public Chat(Chat value) {
        this.chatId = value.chatId;
        this.regDate = value.regDate;
    }

    @ConstructorProperties({"chatId", "regDate"})
    public Chat(
        @NotNull Long chatId,
        @Nullable LocalDateTime regDate
    ) {
        this.chatId = chatId;
        this.regDate = regDate;
    }

    /**
     * Getter for <code>CHAT.CHAT_ID</code>. Telegram ID чата
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public Long getChatId() {
        return this.chatId;
    }

    /**
     * Setter for <code>CHAT.CHAT_ID</code>. Telegram ID чата
     */
    public void setChatId(@NotNull Long chatId) {
        this.chatId = chatId;
    }

    /**
     * Getter for <code>CHAT.REG_DATE</code>. Дата регистрации чата
     */
    @Nullable
    public LocalDateTime getRegDate() {
        return this.regDate;
    }

    /**
     * Setter for <code>CHAT.REG_DATE</code>. Дата регистрации чата
     */
    public void setRegDate(@Nullable LocalDateTime regDate) {
        this.regDate = regDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Chat other = (Chat) obj;
        if (this.chatId == null) {
            if (other.chatId != null) {
                return false;
            }
        } else if (!this.chatId.equals(other.chatId)) {
            return false;
        }
        if (this.regDate == null) {
            if (other.regDate != null) {
                return false;
            }
        } else if (!this.regDate.equals(other.regDate)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.chatId == null) ? 0 : this.chatId.hashCode());
        result = prime * result + ((this.regDate == null) ? 0 : this.regDate.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Chat (");

        sb.append(chatId);
        sb.append(", ").append(regDate);

        sb.append(")");
        return sb.toString();
    }
}
