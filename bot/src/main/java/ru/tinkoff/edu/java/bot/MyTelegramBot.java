package ru.tinkoff.edu.java.bot;

import io.micrometer.core.instrument.MeterRegistry;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.tinkoff.edu.java.bot.components.BotCommands;
import ru.tinkoff.edu.java.bot.components.CommandRouter;
import ru.tinkoff.edu.java.bot.configuration.ApplicationConfig;
import ru.tinkoff.edu.java.bot.metric.HandledMessageMetric;

@Component
@Log4j2
public class MyTelegramBot extends TelegramLongPollingBot {
    private final CommandRouter commandRouter;
    private final String botName;
    private final HandledMessageMetric handledMessageMetric;

    @Autowired
    public MyTelegramBot(ApplicationConfig applicationConfig, CommandRouter commandRouter, MeterRegistry registry) {
        super(applicationConfig.botToken());
        this.commandRouter = commandRouter;
        this.botName = applicationConfig.botUsername();
        try {
            this.execute(new SetMyCommands(BotCommands.LIST_OF_COMMANDS, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        this.handledMessageMetric = new HandledMessageMetric(registry);
        log.info("Bot successfully started");
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage()) {
            return;
        }

        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId().toString());

        String messageText = update.getMessage().hasText() ? update.getMessage().getText() : null;
        String answerText = commandRouter.routeCommand(update, messageText);
        message.setText(answerText);

        try {
            execute(message);
            handledMessageMetric.increment();
        } catch (TelegramApiException e) {
            log.error(e);
        }
    }

    @Override
    public String getBotUsername() {
        return this.botName;
    }

    public void sendMailing(String message, List<Long> tgChatIds) {
        tgChatIds.forEach(chatId -> {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId.toString());
            sendMessage.setText(message);
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                log.error(e);
            }
        });
    }
}
