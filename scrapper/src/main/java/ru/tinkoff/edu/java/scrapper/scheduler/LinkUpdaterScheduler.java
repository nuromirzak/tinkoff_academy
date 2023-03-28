package ru.tinkoff.edu.java.scrapper.scheduler;

import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@Log4j2
public class LinkUpdaterScheduler {
    private int iteration = 0;

    @Scheduled(fixedDelayString = "#{@schedulerIntervalMs}")
    public void update() {
        int currentIteration = ++iteration;
        log.info("{}th iteration of link update process started", currentIteration);

        // Имитируем обновление ссылок
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("{}th iteration of link update process finished", currentIteration);
    }
}
