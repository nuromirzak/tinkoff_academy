package ru.tinkoff.edu.java.scrapper.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class LinkUpdaterScheduler {
    protected static final Logger LOGGER = LoggerFactory.getLogger(LinkUpdaterScheduler.class);
    private int iteration = 0;

    @Scheduled(fixedDelayString = "#{@schedulerIntervalMs}")
    public void update() {
        int currentIteration = ++iteration;
        LOGGER.info("{}th iteration of link update process started", currentIteration);

        // Имитируем обновление ссылок
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LOGGER.info("{}th iteration of link update process finished", currentIteration);
    }

    public static void main(String[] args) {
        LinkUpdaterScheduler scheduler = new LinkUpdaterScheduler();
        scheduler.update();
    }
}
