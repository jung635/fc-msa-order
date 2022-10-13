package dev.practice.order.application.item;

import dev.practice.order.domain.item.ItemCommand;
import dev.practice.order.domain.item.ItemInfo;
import dev.practice.order.domain.item.ItemService;
import dev.practice.order.domain.notification.NotificationService;
import dev.practice.order.domain.partner.PartnerInfo;
import dev.practice.order.domain.partner.PartnerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemFacade {
    private final ItemService itemService;
    private final NotificationService notificationService;
    private final PartnerService partnerService;

    public String registerItem(ItemCommand.RegisterItemRequest request, String partnerToken) {
        String itemToken = itemService.registerItem(request, partnerToken);
        PartnerInfo partnerInfo = partnerService.getPartnerInfo(partnerToken);
        notificationService.sendEmail(partnerInfo.getEmail(), "title", "description");
        return itemToken;
    }

    public void changeOnSaleItem(String itemToken) {
        itemService.changeOnSale(itemToken);
    }

    public void changeEndOfSaleItem(String itemToken) {
        itemService.changeEndOfSale(itemToken);
    }

    public ItemInfo.Main retrieveItemInfo(String itemToken) {
        return itemService.retrieveItemInfo(itemToken);
    }


}
