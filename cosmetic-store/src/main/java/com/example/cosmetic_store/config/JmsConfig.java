package com.example.cosmetic_store.config;

import com.example.cosmetic_store.model.AuditEvent;
import com.example.cosmetic_store.model.NotificationEvent;
import jakarta.jms.Topic;
import jakarta.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;


import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJms // поддержка jms аннотаций
public class JmsConfig {
    
    @Bean
    // фабрика ActiveMQ
    public ConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL("tcp://localhost:61616");
        connectionFactory.setTrustAllPackages(true);
        return connectionFactory;
    }
    
    @Bean
    public Topic storeEventsTopic() {
        return new ActiveMQTopic("store.events.topic");
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory());
        // преобразование объекта в jms сообщение
        template.setMessageConverter(jacksonJmsMessageConverter());
        template.setPubSubDomain(true);
        return template;
    }
    
    @Bean
    // фабрика для слушателей
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setMessageConverter(jacksonJmsMessageConverter());
        factory.setConcurrency("1-1");
        // топик для слушателей
        factory.setPubSubDomain(true);
        return factory;
    }
    
    @Bean
    // конверт для преобразований
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        
        Map<String, Class<?>> typeIdMappings = new HashMap<>();
        typeIdMappings.put("auditEvent", AuditEvent.class);
        typeIdMappings.put("notificationEvent", NotificationEvent.class);
        converter.setTypeIdMappings(typeIdMappings);
        
        return converter;
    }
}