package dev.trangelier.oci.ocistreaming.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicConfiguration {

    @Value("${spring.kafka.topics}")
    private String topic;

    @Bean
    public String springBootTopic() {
        return topic;
    }

    @Bean
    public NewTopic usersTopic(String springBootTopic) {
        return TopicBuilder
                .name(springBootTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }
}
