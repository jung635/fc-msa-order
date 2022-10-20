package dev.practice.order.domain.order;

public interface OrderService {
    //주문
    String registerOrder(OrderCommand.RegisterOrder registerOrder);
    //결제
    void paymentOrder(OrderCommand.PaymentRequest paymentRequest);
    //조회
    OrderInfo.Main retrieveOrder(String orderToken);

    void updateReceiverInfo(String orderToken, OrderCommand.UpdateReceiverInfoRequest request);
}
