package dev.practice.order.interfaces.order.gift;

import dev.practice.order.domain.order.OrderCommand;
import dev.practice.order.domain.order.OrderInfo;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface GiftOrderDtoMapper {

    @Mapping(source = "buyerUserId", target = "userId")
    OrderCommand.RegisterOrder of(GiftOrderDto.RegisterOrderRequest request);
    OrderCommand.RegisterOrderItem of(GiftOrderDto.RegisterOrderItem request);
    OrderCommand.RegisterOrderItemOptionGroup of(GiftOrderDto.RegisterOrderItemOptionGroupRequest request);
    OrderCommand.RegisterOrderItemOption of(GiftOrderDto.RegisterOrderItemOptionRequest request);
    GiftOrderDto.RegisterResponse of(String orderToken);
    OrderCommand.PaymentRequest of(GiftOrderDto.PaymentRequest request);
}
