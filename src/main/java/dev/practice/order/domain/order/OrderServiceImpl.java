package dev.practice.order.domain.order;

import dev.practice.order.domain.order.payment.PaymentProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final OrderStore orderStore;
    private final OrderItemSeriesFactory orderItemSeriesFactory;
    private final OrderReader orderReader;
    private final OrderInfoMapper orderInfoMapper;
    private final PaymentProcessor paymentProcessor;
    @Override
    @Transactional
    public String registerOrder(OrderCommand.RegisterOrder registerOrder) {
        Order order = orderStore.store(registerOrder.toEntity());
        orderItemSeriesFactory.store(order, registerOrder);
        return order.getOrderToken();
    }

    @Override
    @Transactional
    public void paymentOrder(OrderCommand.PaymentRequest paymentRequest) {
        String orderToken = paymentRequest.getOrderToken();
        Order order = orderReader.getOrder(orderToken);
        paymentProcessor.pay(order, paymentRequest);
        order.orderComplete();
    }

    @Override
    @Transactional(readOnly = true)
    public OrderInfo.Main retrieveOrder(String orderToken) {
        Order order = orderReader.getOrder(orderToken);
        return orderInfoMapper.of(order, order.getOrderItemList());
    }
}
