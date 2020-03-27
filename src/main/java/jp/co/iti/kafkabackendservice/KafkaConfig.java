package jp.co.iti.kafkabackendservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

/**
 * Kafka 関連の各種設定を担当するクラス
 * 参考：
 * https://asbnotebook.com/2020/01/05/synchronous-request-reply-using-apache-kafka-spring-boot/
 */
@Configuration
public class KafkaConfig {
    // レスポンスを受けるための Consumer に指定するグループID
    @Value("${kafka.group.id}")
    private String groupId;

    // レスポンスデータが格納される topic 名
    @Value("${kafka.reply.topic}")
    private String replyTopic;

    // Consumer 用（リクエストを Kafka から pull して、レスポンスを再度 Kafka に push するほう）の KafkaTemplate オブジェクト
    @Bean
    public KafkaTemplate<Long, String> replyTemplate(ProducerFactory<Long, String> pf,
                                                            ConcurrentKafkaListenerContainerFactory<Long, String> factory) {
        KafkaTemplate<Long, String> kafkaTemplate = new KafkaTemplate<>(pf);
        factory.getContainerProperties().setMissingTopicsFatal(false);
        factory.setReplyTemplate(kafkaTemplate);
        return kafkaTemplate;
    }}
