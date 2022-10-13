package dev.practice.order.domain.item;

import dev.practice.order.domain.item.optiongroup.ItemOptionGroup;
import dev.practice.order.domain.partner.Partner;
import dev.practice.order.domain.partner.PartnerReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{
    private final PartnerReader partnerReader;
    private final ItemStore itemStore;
    private final ItemOptionSeriesFactory itemOptionSeriesFactory;
    private final ItemReader itemReader;


    @Override
    @Transactional
    public String registerItem(ItemCommand.RegisterItemRequest command, String partnerToken) {
        //1. get partner
        Partner partner = partnerReader.getPartner(partnerToken);
        //2. store item
        Item initItem = command.toEntity(partner.getId());
        Item item = itemStore.store(initItem);
        //3. store itemOption + itemOptionGroup
        itemOptionSeriesFactory.store(command, item);
        return item.getItemToken();
    }

    @Override
    @Transactional
    public void changeOnSale(String itemToken) {
        Item item = itemReader.getItemBy(itemToken);
        item.changeOnSale();
    }

    @Override
    @Transactional
    public void changeEndOfSale(String itemToken) {
        Item item = itemReader.getItemBy(itemToken);
        item.changeEndOfSale();
    }

    @Override
    @Transactional(readOnly = true)
    public ItemInfo.Main retrieveItemInfo(String itemToken) {
        Item item = itemReader.getItemBy(itemToken);
        List<ItemInfo.ItemOptionGroupInfo> itemOptionSeries = itemReader.getItemOptionSeries(item);
        return new ItemInfo.Main(item, itemOptionSeries);
    }
}
