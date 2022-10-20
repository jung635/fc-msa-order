package dev.practice.order.interfaces.order.gift;

import dev.practice.order.application.order.OrderFacade;
import dev.practice.order.application.order.gift.GiftFacade;
import dev.practice.order.common.response.CommonResponse;
import dev.practice.order.domain.order.OrderCommand;
import dev.practice.order.domain.order.OrderInfo;
import dev.practice.order.interfaces.order.OrderDto;
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
    private final GiftFacade giftFacade;
    private final GiftOrderDtoMapper giftOrderDtoMapper;

    @PostMapping("/init")
    public CommonResponse registerOrder(@RequestBody @Valid GiftOrderDto.RegisterOrderRequest request) {
        OrderCommand.RegisterOrder orderCommand = giftOrderDtoMapper.of(request);
        String orderToken = orderFacade.registerOrder(orderCommand);
        GiftOrderDto.RegisterResponse response = giftOrderDtoMapper.of(orderToken);
        return CommonResponse.success(response);
    }

    @PostMapping("/{orderToken}/update-receiver-info")
    public CommonResponse updateReceiverInfo(@PathVariable String orderToken,
                                             @RequestBody @Valid GiftOrderDto.UpdateReceiverInfoReq request) {
        orderFacade.updateReceiverInfo(orderToken, request.toCommand());
        return CommonResponse.success("OK");
    }

    @PostMapping("/payment-order")
    public CommonResponse paymentOrder(@RequestBody @Valid GiftOrderDto.PaymentRequest request) {
        OrderCommand.PaymentRequest paymentRequest = giftOrderDtoMapper.of(request);
        giftFacade.paymentOrder(paymentRequest);
        return CommonResponse.success("OK");
    }
}
