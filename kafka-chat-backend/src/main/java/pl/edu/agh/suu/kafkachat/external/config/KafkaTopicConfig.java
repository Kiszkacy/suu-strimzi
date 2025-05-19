package pl.edu.agh.suu.kafkachat.external.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic topic() {
        return new NewTopic("messages", 1, (short) 1);
    }
}