package dev.practice.order.domain.order;

import dev.practice.order.domain.order.deliveryFragment.DeliveryFragment;
import dev.practice.order.domain.order.payment.PayMethod;
import lombok.Builder;
import lombok.NoArgsConstructor;

public class OrderCommand {

    @Builder
    public static class RegisterOrder {
        private final Long userId;
        private final PayMethod payMethod;
        private final String receiverName;
        private final String receiverPhone;
        private final String receiverZipcode;
        private final String receiverAddress1;
        private final String receiverAddress2;
        private final String etcMessage;

        public Order toEntity() {
            DeliveryFragment deliveryFragment = DeliveryFragment.builder()
                    .receiverName(receiverName)
                    .receiverPhone(receiverPhone)
                    .receiverZipcode(receiverZipcode)
                    .receiverAddress1(receiverAddress1)
                    .receiverAddress2(receiverAddress2)
                    .etcMessage(etcMessage)
                    .build();

            return Order.builder()
                    .userId(userId)
                    .payMethod(payMethod)
                    .deliveryFragment(deliveryFragment)
                    .build();
        }
    }

    @Builder
    public static class PaymentRequest {
        private final String orderToken;
        private final Long amount;
        private final PayMethod payMethod;
    }
}
