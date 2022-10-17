package dev.practice.order.infrastructure.order;

import dev.practice.order.domain.item.Item;
import dev.practice.order.domain.item.ItemReader;
import dev.practice.order.domain.order.*;
import dev.practice.order.domain.order.item.OrderItem;
import dev.practice.order.domain.order.item.OrderItemOptionGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderItemSeriesFactoryImpl implements OrderItemSeriesFactory {
    private final OrderStore orderStore;
    private final ItemReader itemReader;

    @Override
    public List<OrderItem> store(Order order, OrderCommand.RegisterOrder registerOrder) {
        return registerOrder.getOrderItemList().stream()
                .map(registerOrderItem -> {
                    //order item 저장
                    Item item = itemReader.getItemBy(registerOrderItem.getItemToken());
                    OrderItem orderItem = orderStore.store(registerOrderItem.toEntity(order, item));
                    registerOrderItem.getOrderItemOptionGroupList().forEach(registerOrderItemOptionGroup -> {
                        //option group 저장
                        OrderItemOptionGroup orderItemOptionGroup = orderStore.store(registerOrderItemOptionGroup.toEntity(orderItem));
                        registerOrderItemOptionGroup.getOrderItemOptionList().forEach(registerOrderItemOption -> {
                            //option 저장
                            orderStore.store(registerOrderItemOption.toEntity(orderItemOptionGroup));
                        });
                    });
                    return orderItem;
                }).collect(Collectors.toList());
    }
}
