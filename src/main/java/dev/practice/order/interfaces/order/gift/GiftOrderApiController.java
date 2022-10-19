package dev.practice.order.interfaces.order.gift;

import dev.practice.order.application.order.OrderFacade;
import dev.practice.order.common.response.CommonResponse;
import dev.practice.order.domain.order.OrderCommand;
import dev.practice.order.domain.order.OrderInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/gift-orders")
public class GiftOrderApiController {
    private final OrderFacade orderFacade;
    private final GiftOrderDtoMapper giftOrderDtoMapper;

    @PostMapping("/init")
    public CommonResponse registerOrder(@RequestBody @Valid GiftOrderDto.RegisterOrderRequest request) {
        OrderCommand.RegisterOrder orderCommand = giftOrderDtoMapper.of(request);
        String orderToken = orderFacade.registerOrder(orderCommand);
        GiftOrderDto.RegisterResponse response = giftOrderDtoMapper.of(orderToken);
        return CommonResponse.success(response);
    }
}
