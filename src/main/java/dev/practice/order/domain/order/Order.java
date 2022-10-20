package dev.practice.order.domain.order;

import dev.practice.order.common.exception.IllegalStatusException;
import dev.practice.order.common.util.TokenGenerator;
import dev.practice.order.domain.AbstractEntity;
import dev.practice.order.domain.order.item.OrderItem;
import dev.practice.order.domain.order.deliveryFragment.DeliveryFragment;
import dev.practice.order.domain.order.payment.PayMethod;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "orders")
public class Order extends AbstractEntity {
    private static final String ORDER_PREFIX = "ord_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderToken;
    private Long userId;
    @Enumerated(EnumType.STRING)
    private PayMethod payMethod;
    @Enumerated(EnumType.STRING)
    private Status status;
    private ZonedDateTime OrderedAt;
    @Embedded
    private DeliveryFragment deliveryFragment;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.PERSIST)
    private List<OrderItem> orderItemList;

    @RequiredArgsConstructor
    @Getter
    public enum Status {
        INIT("주문시작"),
        ORDER_COMPLETE("주문완료"),
        DELIVERY_PREPARE("배송준비"),
        IN_DELIVERY("배송중"),
        DELIVERY_COMPLETE("배송완료");

        private final String description;
    }

    @Builder
    public Order(
            Long userId,
            PayMethod payMethod,
            DeliveryFragment deliveryFragment

    ) {
        this.orderToken = TokenGenerator.randomCharacterWithPrefix(ORDER_PREFIX);
        this.userId = userId;
        this.payMethod = payMethod;
        this.deliveryFragment = deliveryFragment;
        this.OrderedAt = ZonedDateTime.now();
        this.status = Status.INIT;
    }

    //주문 상태 변경
    public void orderComplete() {
        if (this.status != Status.INIT) throw new IllegalStatusException();
        this.status = Status.ORDER_COMPLETE;
    }

    //주문 총 가격 계산
    public Long getTotalPrice() {
        return orderItemList.stream()
                .mapToLong(OrderItem::calculatePrice)
                .sum();
    }

    public boolean isAlreadyPaymentComplete() {
        return status == Status.ORDER_COMPLETE;
    }

    public void updateDeliveryFragment(
            String receiverName,
            String receiverPhone,
            String receiverZipcode,
            String receiverAddress1,
            String receiverAddress2,
            String etcMessage
    ) {
        this.deliveryFragment = DeliveryFragment.builder()
                .receiverName(receiverName)
                .receiverPhone(receiverPhone)
                .receiverZipcode(receiverZipcode)
                .receiverAddress1(receiverAddress1)
                .receiverAddress2(receiverAddress2)
                .etcMessage(etcMessage)
                .build();
    }

    public void deliveryPrepare() {
        if (this.status != Status.ORDER_COMPLETE) throw new IllegalStatusException();
        this.status = Status.DELIVERY_PREPARE;
        this.getOrderItemList().forEach(OrderItem::deliveryPrepare);
    }
}
