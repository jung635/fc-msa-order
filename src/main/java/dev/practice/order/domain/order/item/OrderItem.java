package dev.practice.order.domain.order.item;

import dev.practice.order.common.exception.IllegalStatusException;
import dev.practice.order.common.exception.InvalidParamException;
import dev.practice.order.domain.AbstractEntity;
import dev.practice.order.domain.order.Order;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Slf4j
@Table(name = "order_items")
public class OrderItem extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String itemToken;
    private Long partnerId;
    private String itemName;
    private Long itemPrice;
    private Long itemId;
    private Integer orderCount;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orderItem", cascade = CascadeType.PERSIST)
    private List<OrderItemOptionGroup> orderItemOptionGroupList;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @Getter
    @AllArgsConstructor
    public enum DeliveryStatus {
        BEFORE_DELIVERY("배송전"),
        DELIVERY_PREPARE("배송준비중"),
        DELIVERING("배송중"),
        COMPLETE_DELIVERY("배송완료");

        private final String description;
    }


    @Builder
    public OrderItem(
            Order order,
            Long partnerId,
            String itemName,
            Long itemPrice,
            String itemToken,
            Long itemId,
            Integer orderCount
    ) {
        if(partnerId == null) throw new InvalidParamException("OrderItem.partnerId");
        if(StringUtils.isBlank(itemName)) throw new InvalidParamException("OrderItem.itemName");
        if(itemPrice == null) throw new InvalidParamException("OrderItem.itemPrice");
        if(order == null) throw new InvalidParamException("OrderItem.order");
        if(StringUtils.isBlank(itemToken)) throw new InvalidParamException("OrderItem.itemToken");
        if(itemId == null) throw new InvalidParamException("OrderItem.itemId");
        if(orderCount == null) throw new InvalidParamException("OrderItem.itemCount");

        this.order = order;
        this.partnerId = partnerId;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemToken = itemToken;
        this.itemPrice = itemPrice;
        this.orderCount = orderCount;
        this.deliveryStatus = DeliveryStatus.BEFORE_DELIVERY;
    }

    public Long calculatePrice() {
        Long itemOptionTotalAmount = orderItemOptionGroupList.stream()
                .mapToLong(OrderItemOptionGroup::calculateTotalAmount)
                .sum();
        return (itemPrice + itemOptionTotalAmount) * orderCount;
    }

    public void deliveryPrepare() {
        if (this.deliveryStatus != DeliveryStatus.BEFORE_DELIVERY) throw new IllegalStatusException();
        this.deliveryStatus = DeliveryStatus.DELIVERY_PREPARE;
    }
}


