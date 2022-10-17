package dev.practice.order.domain.order;

import dev.practice.order.domain.order.item.OrderItem;
import dev.practice.order.domain.order.item.OrderItemOption;
import dev.practice.order.domain.order.item.OrderItemOptionGroup;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface OrderInfoMapper {

    @Mappings({
            @Mapping(source = "order.id", target = "orderId"),
            @Mapping(expression = "java(order.getTotalPrice())", target = "totalAmount"),
            @Mapping(expression = "java(order.getStatus().name())", target = "status"),
            @Mapping(expression = "java(order.getStatus().getDescription())", target = "statusDescription"),
            @Mapping(expression = "java(order.getPayMethod().name())", target = "payMethod"),
            @Mapping(source = "order.deliveryFragment", target = "deliveryInfo")
    })
    OrderInfo.Main of(Order order, List<OrderItem> orderItemList);

    @Mappings({
            @Mapping(expression = "java(orderItem.getDeliveryStatus().name())", target = "deliveryStatus"),
            @Mapping(expression = "java(orderItem.getDeliveryStatus().getDescription())", target = "deliveryStatusDescription"),
            @Mapping(expression = "java(orderItem.calculatePrice())", target = "totalAmount")
    })
    OrderInfo.OrderItem of(OrderItem orderItem);

    OrderInfo.OrderItemOptionGroup of(OrderItemOptionGroup orderItemOptionGroup);
    OrderInfo.OrderItemOption of(OrderItemOption orderItemOption);
}
