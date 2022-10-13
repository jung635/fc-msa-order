package dev.practice.order.domain.order;

public interface OrderService {
    //주문 상태 변경
    void changeOrderStatus(String orderToken);
    //주문 총 가격 계산
    Long getTotalPrice(String orderToken);
}
