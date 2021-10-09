package com.verifymycoin.VerificationManager.common.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value(value = "${spring.kafka.consumer.transaction-topic}")
    private String verificationTopic;

    @Value(value = "${spring.kafka.consumer.transaction-topic}")
    private String userTopic;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic newTopicTransaction() {
        return new NewTopic(verificationTopic, 1, (short) 2);
    }

    @Bean
    public NewTopic newTopicVerification() {
        return new NewTopic(userTopic, 1, (short) 2);
    }
}
