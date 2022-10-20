package dev.practice.order.application.order.gift;

import dev.practice.order.domain.notification.NotificationService;
import dev.practice.order.domain.order.OrderCommand;
import dev.practice.order.domain.order.OrderService;
import dev.practice.order.domain.order.gift.GiftOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GiftFacade {
    private final GiftOrderService giftOrderService;
    private final NotificationService notificationService;
    public void paymentOrder(OrderCommand.PaymentRequest paymentRequest) {
        giftOrderService.paymentOrder(paymentRequest);
        notificationService.sendKakao("01012345678", "ORDER_COMPLETE");
    }
}
