package ru.tinkoff.edu.java.bot.configuration;

import java.util.HashMap;
import java.util.Map;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.support.converter.ClassMapper;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.bot.dtos.LinkUpdateRequest;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "true")
public class RabbitMQConfiguration {
    private final static String DEAD_LETTER_EXCHANGE_SUFFIX = ".dlx";
    private final static String DEAD_LETTER_QUEUE_SUFFIX = ".dlq";
    private final String queueName;
    private final String exchangeName;

    public RabbitMQConfiguration(ApplicationConfig applicationConfiguration) {
        this.queueName = applicationConfiguration.queueName();
        this.exchangeName = applicationConfiguration.exchangeName();
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(exchangeName);
    }

    @Bean
    public Queue queue() {
        return QueueBuilder.nonDurable(queueName)
            .withArgument("x-dead-letter-exchange", exchangeName + DEAD_LETTER_EXCHANGE_SUFFIX).build();
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(directExchange()).with(queueName);
    }

    @Bean
    public DirectExchange deadDirectExchange() {
        return new DirectExchange(exchangeName + DEAD_LETTER_EXCHANGE_SUFFIX);
    }

    @Bean
    public Queue deadQueue() {
        return new Queue(queueName + DEAD_LETTER_QUEUE_SUFFIX);
    }

    @Bean
    public Binding deadBinding() {
        return BindingBuilder.bind(deadQueue()).to(deadDirectExchange()).with(queueName + DEAD_LETTER_QUEUE_SUFFIX);
    }

    @Bean
    public ClassMapper classMapper() {
        Map<String, Class<?>> mappings = new HashMap<>();
        mappings.put("ru.tinkoff.edu.java.scrapper.dtos.requests.LinkUpdateRequest", LinkUpdateRequest.class);

        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setTrustedPackages("ru.tinkoff.edu.java.scrapper.service.dto.*");
        classMapper.setIdClassMapping(mappings);
        return classMapper;
    }

    @Bean
    public MessageConverter jsonMessageConverter(ClassMapper classMapper) {
        Jackson2JsonMessageConverter jsonConverter = new Jackson2JsonMessageConverter();
        jsonConverter.setClassMapper(classMapper);
        return jsonConverter;
    }
}
