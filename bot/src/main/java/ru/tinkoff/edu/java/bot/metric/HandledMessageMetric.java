package ru.tinkoff.edu.java.bot.metric;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

public class HandledMessageMetric {
    private final Counter counter;

    public HandledMessageMetric(MeterRegistry registry) {
        counter = Counter.builder("handled_messages")
            .description("Number of successfully handled messages")
            .register(registry);
    }

    public void increment() {
        counter.increment();
    }
}
