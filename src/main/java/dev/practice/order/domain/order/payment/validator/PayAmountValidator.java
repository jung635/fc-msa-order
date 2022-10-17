package dev.practice.order.domain.order.payment.validator;


import dev.practice.order.common.exception.InvalidParamException;
import dev.practice.order.domain.order.Order;
import dev.practice.order.domain.order.OrderCommand;
import org.springframework.stereotype.Component;

@org.springframework.core.annotation.Order(value = 1)
@Component
public class PayAmountValidator implements PaymentValidator{
    @Override
    public void validate(Order order, OrderCommand.PaymentRequest request) {
        if (!order.getTotalPrice().equals(request.getAmount())) {
            throw new InvalidParamException("주문가격이 불일치합니다");
        }
    }
}
