package dev.practice.order.infrastructure.item;

import dev.practice.order.common.exception.EntityNotFoundException;
import dev.practice.order.domain.item.Item;
import dev.practice.order.domain.item.ItemInfo;
import dev.practice.order.domain.item.ItemReader;
import dev.practice.order.domain.item.optiongroup.ItemOptionGroup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class ItemReaderImpl implements ItemReader {
    private final ItemRepository itemRepository;

    @Override
    public Item getItemBy(String itemToken) {
        return itemRepository.findByItemToken(itemToken)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<ItemInfo.ItemOptionGroupInfo> getItemOptionSeries(Item item) {
        List<ItemOptionGroup> itemOptionGroupList = item.getItemOptionGroupList();
//        List<ItemInfo.ItemOptionGroupInfo> itemOptionGroupInfoList = new ArrayList<>();
//        for(ItemOptionGroup itemOptionGroup : itemOptionGroupList) {
//            List<ItemOption> itemOptionList = itemOptionGroup.getItemOptionList();
//            List<ItemInfo.ItemOptionInfo> itemOptionInfoList = new ArrayList<>();
//            for(ItemOption itemOption : itemOptionList) {
//                itemOptionInfoList.add(new ItemInfo.ItemOptionInfo(itemOption));
//            }
//            itemOptionGroupInfoList.add(new ItemInfo.ItemOptionGroupInfo(itemOptionGroup, itemOptionInfoList));
//        }
//
//        return itemOptionGroupInfoList;

        /* 아래와 같이 리팩터링 */
        return itemOptionGroupList.stream()
                .map(itemOptionGroup -> {
                    List<ItemInfo.ItemOptionInfo> itemOptionInfoList = itemOptionGroup.getItemOptionList().stream()
                            .map(ItemInfo.ItemOptionInfo::new)
                            .collect(Collectors.toList());
                    return new ItemInfo.ItemOptionGroupInfo(itemOptionGroup, itemOptionInfoList);
                }).collect(Collectors.toList());

    }
}
