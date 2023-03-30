package ru.tinkoff.edu.java.bot.components;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.tinkoff.edu.java.bot.clients.ScrapperClient;
import ru.tinkoff.edu.java.bot.dtos.ListLinkResponse;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TextMessageHandler {
    private static final String COMMAND_START = "/start";
    private static final String COMMAND_LIST = "/list";
    private static final String COMMAND_HELP = "/help";
    private static final String COMMAND_TRACK = "/track";
    private static final String COMMAND_UNTRACK = "/untrack";
    private static final String COMMAND_DELETE = "/delete";
    private static final String COMMAND_TEST = "/test";

    private static final String ERROR_MESSAGE_COMMAND_NOT_FOUND = "Введите команду для получения списка доступных команд введите команду /help.";
    private static final String ERROR_MESSAGE_LINK_NOT_ADDED = "Не было добавлено ссылок, возможно вы уже отслеживаете эту ссылку.";
    private static final String ERROR_MESSAGE_LINK_NOT_REMOVED = "Не было удалено ссылок, возможно вы не отслеживаете эту ссылку.";
    private static final String ERROR_MESSAGE_UNHANDLED = "Произошла непредвиденная ошибка, обратитесь к администратору бота.";
    private static final String ERROR_MESSAGE_NOT_REGISTERED = "Вы должны быть зарегистрированы, для получения списка доступных команд введите команду /help.";
    private static final String ERROR_MESSAGE_INVALID_COMMAND_ARGUMENTS = "Применение команды: %s <ссылка>";
    private static final String SUCCESS_MESSAGE_DELETE = "Вы успешно отписались от всех ссылок, для получения списка доступных команд введите команду /help.";
    private static final String SECRET_COMMAND_USERNAME = "nrmkhd";
    private static final String SECRET_COMMAND_RESPONSE = "Вы нашли секретную команду, но у вас нет прав на ее использование))";
    private static final String ERROR_MESSAGE_NO_LINKS = "У вас нет отслеживаемых ссылок, для получения списка доступных команд введите команду /help.";

    private final Map<String, String> commands = Map.of(
            COMMAND_START, "Добро пожаловать в бота, %s! Вы успешно зарегистрировались, для подробной информации введите команду /help",
            COMMAND_HELP, BotCommands.HELP_TEXT,
            COMMAND_TRACK, "Вы успешно подписались на ссылку %s, теперь вы будете получать уведомления о изменениях",
            COMMAND_UNTRACK, "Вы успешно отписались от ссылки %s, теперь вы не будете получать уведомления о изменениях",
            COMMAND_LIST, "Вот список ваших отслеживаемых ссылок:\n%s"
    );

    private final ScrapperClient scrapperClient;

    public String handleTextMessage(Update update, String message) {
        String[] firstAndRemainingWords = getFirstAndRemainingWords(message);
        String chatId = update.getMessage().getChatId().toString();

        if (firstAndRemainingWords.length < 1) {
            return ERROR_MESSAGE_COMMAND_NOT_FOUND;
        }

        return switch (firstAndRemainingWords[0]) {
            case COMMAND_START -> handleStartCommand(update, chatId, firstAndRemainingWords);
            case COMMAND_LIST -> handleListCommand(update, chatId, firstAndRemainingWords);
            case COMMAND_HELP -> handleHelpCommand(firstAndRemainingWords);
            case COMMAND_TRACK -> handleTrackCommand(update, chatId, firstAndRemainingWords);
            case COMMAND_UNTRACK -> handleUntrackCommand(update, chatId, firstAndRemainingWords);
            case COMMAND_DELETE -> handleDeleteCommand(update, chatId, firstAndRemainingWords);
            case COMMAND_TEST -> handleTestCommand(update, chatId, firstAndRemainingWords);
            default -> ERROR_MESSAGE_COMMAND_NOT_FOUND;
        };
    }

    private String handleStartCommand(Update update, String chatId, String[] firstAndRemainingWords) {
        String text = commands.get(firstAndRemainingWords[0]);

        if (scrapperClient.registerChat(chatId)) {
            return String.format(text, update.getMessage().getFrom().getFirstName());
        } else {
            return ERROR_MESSAGE_UNHANDLED;
        }
    }

    private String handleListCommand(Update update, String chatId, String[] firstAndRemainingWords) {
        ListLinkResponse linkUpdateRequest = scrapperClient.getTrackedLinks(chatId);
        if (linkUpdateRequest.size() == 0) {
            return ERROR_MESSAGE_NO_LINKS;
        }
        return String.format(commands.get(firstAndRemainingWords[0]), linkUpdateRequest);
    }

    private String handleHelpCommand(String[] firstAndRemainingWords) {
        return commands.get(firstAndRemainingWords[0]);
    }

    private String handleTrackCommand(Update update, String chatId, String[] firstAndRemainingWords) {
        String text = commands.get(firstAndRemainingWords[0]);
        if (firstAndRemainingWords[1] == null) {
            return String.format(ERROR_MESSAGE_INVALID_COMMAND_ARGUMENTS, firstAndRemainingWords[0]);
        } else {
            Long longId = scrapperClient.addLinkToTrack(chatId, firstAndRemainingWords[1]);

            if (longId == null) {
                return ERROR_MESSAGE_LINK_NOT_ADDED;
            } else if (longId == -1) {
                return ERROR_MESSAGE_UNHANDLED;
            } else {
                return String.format(text, firstAndRemainingWords[1]);
            }
        }
    }

    private String handleUntrackCommand(Update update, String chatId, String[] firstAndRemainingWords) {
        String text = commands.get(firstAndRemainingWords[0]);
        if (firstAndRemainingWords[1] == null) {
            return String.format(ERROR_MESSAGE_INVALID_COMMAND_ARGUMENTS, firstAndRemainingWords[0]);
        } else {
            Long longId = scrapperClient.removeLinkFromTrack(chatId, firstAndRemainingWords[1]);

            if (longId == null) {
                return ERROR_MESSAGE_LINK_NOT_REMOVED;
            } else if (longId == -1) {
                return ERROR_MESSAGE_UNHANDLED;
            } else {
                return String.format(text, firstAndRemainingWords[1]);
            }
        }
    }

    private String handleDeleteCommand(Update update, String chatId, String[] firstAndRemainingWords) {
        if (scrapperClient.removeChat(update.getMessage().getChatId().toString())) {
            return SUCCESS_MESSAGE_DELETE;
        } else {
            return ERROR_MESSAGE_NOT_REGISTERED;
        }
    }

    private String handleTestCommand(Update update, String chatId, String[] firstAndRemainingWords) {
        String username = update.getMessage().getFrom().getUserName();
        if (!username.equals(SECRET_COMMAND_USERNAME)) {
            return SECRET_COMMAND_RESPONSE;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Получаем информацию о сообщении:\n");
        String userId = update.getMessage().getFrom().getId().toString();
        sb.append("UserId: ").append(userId).append("\n");
        sb.append("ChatId: ").append(chatId).append("\n");
        sb.append("MessageId: ").append(update.getMessage().getMessageId()).append("\n");
        if (update.getMessage().hasPhoto()) {
            List<String> photoIds = update.getMessage().getPhoto().stream().map(PhotoSize::getFileId).toList();
            sb.append("PhotoIds: ").append(photoIds).append("\n");
        }
        if (update.getMessage().hasDocument()) {
            String documentId = update.getMessage().getDocument().getFileId();
            sb.append("DocumentId: ").append(documentId).append("\n");
        }
        return sb.toString();
    }

    private String[] getFirstAndRemainingWords(String text) {
        if (text == null) {
            return new String[0];
        }

        String[] words = text.split(" ", 2);
        if (words.length > 1) {
            return words;
        } else {
            return new String[]{words[0], null};
        }
    }
}