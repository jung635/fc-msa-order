package dev.practice.order.interfaces.order.gift;

import dev.practice.order.domain.order.payment.PayMethod;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class GiftOrderDto {

    @Getter
    @Setter
    @ToString
    public static class RegisterOrderRequest {
        @NotNull(message = "buyerUserId 는 필수값입니다")
        private Long buyerUserId;
        @NotBlank(message = "payMethod 는 필수값입니다")
        private String payMethod;
        private String receiverName = "TEMP_VALUE";
        private String receiverPhone = "TEMP_VALUE";
        private String receiverZipcode = "TEMP_VALUE";
        private String receiverAddress1 = "TEMP_VALUE";
        private String receiverAddress2 = "TEMP_VALUE";
        private String etcMessage = "TEMP_VALUE";

        private List<RegisterOrderItem> orderItemList;

    }

    @Getter
    @Setter
    @ToString
    public static class RegisterOrderItem {
        @NotNull(message = "orderCount 는 필수값입니다")
        private Integer orderCount;
        @NotBlank(message = "itemToken 는 필수값입니다")
        private String itemToken;
        @NotBlank(message = "itemName 는 필수값입니다")
        private String itemName;
        @NotNull(message = "itemPrice 는 필수값입니다")
        private Long itemPrice;

        private List<RegisterOrderItemOptionGroupRequest> orderItemOptionGroupList;
    }

    @Getter
    @Setter
    @ToString
    public static class RegisterOrderItemOptionGroupRequest {
        @NotNull(message = "ordering 는 필수값입니다")
        private Integer ordering;
        @NotBlank(message = "itemOptionGroupName 는 필수값입니다")
        private String itemOptionGroupName;

        private List<RegisterOrderItemOptionRequest> orderItemOptionList;
    }

    @Getter
    @Setter
    @ToString
    public static class RegisterOrderItemOptionRequest {
        @NotNull(message = "ordering 는 필수값입니다")
        private Integer ordering;
        @NotBlank(message = "itemOptionName 는 필수값입니다")
        private String itemOptionName;
        @NotNull(message = "itemOptionPrice 는 필수값입니다")
        private Long itemOptionPrice;
    }

    @Getter
    @Builder
    @ToString
    public static class RegisterResponse {
        private final String orderToken;
    }
}
