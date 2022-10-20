package dev.practice.order.domain.order.payment.validator;


import dev.practice.order.common.exception.InvalidParamException;
import dev.practice.order.domain.order.Order;
import dev.practice.order.domain.order.OrderCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@org.springframework.core.annotation.Order(value = 1)
@Component
@Slf4j
public class PayAmountValidator implements PaymentValidator{
    @Override
    public void validate(Order order, OrderCommand.PaymentRequest request) {
        if (!order.getTotalPrice().equals(request.getAmount())) {
            log.info("Req Price = {}", request.getAmount());
            log.info("Orginal Price = {}", order.getTotalPrice());
            throw new InvalidParamException("주문가격이 불일치합니다");
        }
    }
}
