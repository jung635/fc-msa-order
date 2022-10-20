package dev.practice.order.domain.order.gift;

import dev.practice.order.common.exception.IllegalStatusException;
import dev.practice.order.domain.order.Order;
import dev.practice.order.domain.order.OrderCommand;
import dev.practice.order.domain.order.OrderReader;
import dev.practice.order.domain.order.payment.PaymentProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GiftOrderServiceImpl implements GiftOrderService{
    private final OrderReader orderReader;
    private final PaymentProcessor paymentProcessor;

    @Override
    @Transactional
    public void paymentOrder(OrderCommand.PaymentRequest paymentRequest) {
        log.info("GiftOrderServiceImpl.paymentOrder request = {}", paymentRequest);
        String orderToken = paymentRequest.getOrderToken();
        Order order = orderReader.getOrder(orderToken);

        //보상트랜잭션 발생 막기
        if (order.getStatus() != Order.Status.INIT) throw new IllegalStatusException();

        paymentProcessor.pay(order, paymentRequest);
        order.orderComplete();
    }
}
