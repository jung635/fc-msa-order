package dev.practice.order.interfaces.order;

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
@RequestMapping("/api/v1/orders")
public class OrderApiController {
    private final OrderFacade orderFacade;
    private final OrderDtoMapper orderDtoMapper;

    @PostMapping("/init")
    public CommonResponse registerOrder(@RequestBody @Valid OrderDto.RegisterOrderRequest request) {
        OrderCommand.RegisterOrder orderCommand = orderDtoMapper.of(request);
        String orderToken = orderFacade.registerOrder(orderCommand);
        OrderDto.RegisterResponse response = orderDtoMapper.of(orderToken);
        return CommonResponse.success(response);
    }

    @GetMapping("{orderToken}")
    public CommonResponse retrieveOrder(@PathVariable String orderToken) {
        OrderInfo.Main orderResult = orderFacade.retrieveOrder(orderToken);
        //return CommonResponse.success(orderResult);
        return CommonResponse.success(orderDtoMapper.of(orderResult));
    }

    @PostMapping("/payment-order")
    public CommonResponse paymentOrder(@RequestBody @Valid OrderDto.PaymentRequest request) {
        log.info("결제방식::" + request.getPayMethod().name());
        OrderCommand.PaymentRequest paymentRequest = orderDtoMapper.of(request);
        orderFacade.paymentOrder(paymentRequest);
        return CommonResponse.success("OK");
    }
}
