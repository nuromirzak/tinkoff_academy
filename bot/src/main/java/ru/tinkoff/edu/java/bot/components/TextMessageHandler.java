package ru.tinkoff.edu.java.bot.components;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;

@Component
public class TextMessageHandler {
    private final Map<String, String> commands = Map.of(
            "/start", "Добро пожаловать в бота, %s! Вы успешно зарегистрировались, для подробной информации введите команду /help",
            "/help", BotCommands.HELP_TEXT,
            "/track", "Вы успешно подписались на ссылку %s, теперь вы будете получать уведомления о изменениях",
            "/untrack", "Вы успешно отписались от ссылки %s, теперь вы не будете получать уведомления о изменениях",
            "/list", "Вот список ваших отслеживаемых ссылок:\n%s"
    );

    public String handleTextMessage(Update update, String message) {
        String[] firstAndRemainingWords = getFirstAndRemainingWords(message);

        return switch (firstAndRemainingWords[0]) {
            case "/start" -> {
                String firstName = update.getMessage().getFrom().getFirstName();
                String text = commands.get(firstAndRemainingWords[0]);
                yield String.format(text, firstName);
            }
            case "/list", "/help" -> {
                yield commands.get(firstAndRemainingWords[0]);
            }
            case "/track", "/untrack" -> {
                String text = commands.get(firstAndRemainingWords[0]);
                if (firstAndRemainingWords[1] == null) {
                    yield String.format("Применение команды: [%s <ссылка>]", firstAndRemainingWords[0]);
                } else {
                    yield String.format(text, firstAndRemainingWords[1]);
                }
            }
            case "/test" -> {
                String username = update.getMessage().getFrom().getUserName();
                if (!username.equals("nrmkhd")) {
                    yield "Вы нашли секретную команду, но у вас нет прав на ее использование))";
                }

                StringBuilder sb = new StringBuilder();
                sb.append("Получаем информацию о сообщении:\n");
                String userId = update.getMessage().getFrom().getId().toString();
                sb.append("UserId: ").append(userId).append("\n");
                String chatId = update.getMessage().getChatId().toString();
                sb.append("ChatId: ").append(chatId).append("\n");
                if (update.getMessage().hasPhoto()) {
                    List<String> photoIds = update.getMessage().getPhoto().stream().map(PhotoSize::getFileId).toList();
                    sb.append("PhotoIds: ").append(photoIds).append("\n");
                }
                if (update.getMessage().hasDocument()) {
                    String documentId = update.getMessage().getDocument().getFileId();
                    sb.append("DocumentId: ").append(documentId).append("\n");
                }
                yield sb.toString();
            }
            default -> """
                    Команда не распознана, для получения списка доступных команд введите команду /help.
                    """;
        };
    }


    private String[] getFirstAndRemainingWords(String text) {
        if (text == null) {
            return new String[0];
        }

        String[] words = text.split(" ");
        String[] firstAndRemainingWords = new String[2];
        firstAndRemainingWords[0] = words[0];
        if (words.length > 1) {
            firstAndRemainingWords[1] = text.substring(words[0].length() + 1);
        }
        return firstAndRemainingWords;
    }
}