package dev.practice.order.infrastructure.order.gift;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.practice.order.config.KafkaProducerConfig;
import dev.practice.order.domain.order.gift.GiftMessageChannelSender;
import dev.practice.order.domain.order.gift.GiftPaymentCompleteMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GiftMessageKafkaSender implements GiftMessageChannelSender {
    private final String TOPIC_NAME = "fc.msa.order-payComplete-live.fifo";
    private final KafkaProducerConfig kafkaProducerConfig;

    @Override
    public void paymentComplete(GiftPaymentCompleteMessage message) throws JsonProcessingException {
        Message<GiftPaymentCompleteMessage> kafkaMessage = MessageBuilder
                .withPayload(message)
                .setHeader(KafkaHeaders.TOPIC, TOPIC_NAME)
                .build();
        log.info("GiftMessageKafkaSender.paymentComplete kafkaMessage, {}", kafkaMessage.toString());
        KafkaTemplate<String, Object> kafkaTemplate = kafkaProducerConfig.kafkaTemplate();
        kafkaTemplate.send(kafkaMessage);
    }
}
