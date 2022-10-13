package dev.practice.order.domain.order;

import dev.practice.order.domain.order.deliveryFragment.DeliveryFragment;
import dev.practice.order.domain.order.payment.PayMethod;
import lombok.Builder;

import java.time.ZonedDateTime;
import java.util.List;

public class OrderInfo {

    @Builder
    public static class Main {
        private final Long orderId;
        private final Long OrderToken;
        private final Long userId;
        private final PayMethod payMethod;
        private final String status;
        private final ZonedDateTime OrderedAt;
        private final DeliveryFragment deliveryFragment;
        private final List<OrderItem> orderItemList;
    }

    @Builder
    public static class OrderItem {
        private final Integer orderCount;
        private final Long partnerId;
        private final Long itemId;
        private final String itemName;
        private final Long totalAmount;
        private final Long itemPrice;
        private final String deliveryStatus;
        private final String deliveryStatusDescription;
        private final List<OrderItemOptionGroup> orderItemOptionGroupList;
    }

    @Builder
    public static class OrderItemOptionGroup {
        private final Integer ordering;
        private final String itemOptionGroupName;
        private final List<OrderItemOption> orderItemOptionList;
    }

    @Builder
    public static class OrderItemOption {
        private final Integer ordering;
        private final String itemOptionName;
        private final Long itemOptionPrice;
    }
}
