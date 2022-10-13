package dev.practice.order.domain.item;

import dev.practice.order.domain.item.optiongroup.ItemOptionGroup;

import java.util.List;

public interface ItemReader {
    Item getItemBy(String itemToken);
    List<ItemInfo.ItemOptionGroupInfo> getItemOptionSeries(Item item);
}
