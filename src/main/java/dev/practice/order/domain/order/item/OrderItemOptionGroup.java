package dev.practice.order.domain.order.item;

import dev.practice.order.common.exception.InvalidParamException;
import dev.practice.order.domain.AbstractEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.List;

@Slf4j
@Getter
@Entity
@NoArgsConstructor
@Table(name = "order_item_option_groups")
public class OrderItemOptionGroup extends AbstractEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer ordering;
    private String itemOptionGroupName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orderItemOptionGroup", cascade = CascadeType.PERSIST)
    List<OrderItemOption> itemOptionList;

    @ManyToOne
    @JoinColumn(name = "order_item_id")
    OrderItem orderItem;

    @Builder
    public OrderItemOptionGroup (
            OrderItem orderItem,
            String itemOptionGroupName,
            int ordering
    ) {
        if (orderItem == null) throw new InvalidParamException();
        if (ordering == 0) throw new InvalidParamException();
        if (StringUtils.isEmpty(itemOptionGroupName)) throw new InvalidParamException();

        this.orderItem = orderItem;
        this.itemOptionGroupName = itemOptionGroupName;
        this.ordering = ordering;
    }

    public Long calculateTotalAmount() {
        return itemOptionList.stream()
                .mapToLong(OrderItemOption::getItemOptionPrice)
                .sum();
    }
}
