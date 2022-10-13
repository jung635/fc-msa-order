package dev.practice.order.infrastructure.item;

import dev.practice.order.domain.item.Item;
import dev.practice.order.domain.item.ItemCommand;
import dev.practice.order.domain.item.ItemOptionSeriesFactory;
import dev.practice.order.domain.item.option.ItemOption;
import dev.practice.order.domain.item.option.ItemOptionStore;
import dev.practice.order.domain.item.optiongroup.ItemOptionGroup;
import dev.practice.order.domain.item.optiongroup.ItemOptionGroupStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class ItemOptionSeriesFactoryImpl implements ItemOptionSeriesFactory {
    private final ItemRepository itemRepository;
    private final ItemOptionStore itemOptionStore;
    private final ItemOptionGroupStore itemOptionGroupStore;

    @Override
    public List<ItemOptionGroup> store(ItemCommand.RegisterItemRequest request, Item item) {
        List<ItemCommand.RegisterItemOptionGroupRequest> itemOptionGroupRequestList = request.getItemOptionGroupRequestList();
        if(CollectionUtils.isEmpty(itemOptionGroupRequestList)) return Collections.emptyList();

        return itemOptionGroupRequestList.stream()
                .map(itemOptionGroupRequest -> {
                    ItemOptionGroup initItemOptionGroup = itemOptionGroupRequest.toEntity(item);
                    ItemOptionGroup itemOptionGroup = itemOptionGroupStore.store(initItemOptionGroup);

//                    itemOptionGroupRequest.getItemOptionRequestList().stream()
//                            .map(itemOptionRequest -> {
//                                ItemOption initItemOption = itemOptionRequest.toEntity(itemOptionGroup);
//                                return itemOptionStore.store(initItemOption);
//                            });

                    /* 아래와 같이 리팩터링 */
                    itemOptionGroupRequest.getItemOptionRequestList().forEach(itemOptionRequest -> {
                        ItemOption initItemOption = itemOptionRequest.toEntity(itemOptionGroup);
                        itemOptionStore.store(initItemOption);
                    });

                    return itemOptionGroup;
                }).collect(Collectors.toList());
    }
}
