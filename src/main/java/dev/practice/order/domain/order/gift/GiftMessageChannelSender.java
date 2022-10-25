package dev.practice.order.domain.order.gift;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface GiftMessageChannelSender {
    void paymentComplete(GiftPaymentCompleteMessage message) throws JsonProcessingException;
}
