package dev.practice.order.infrastructure.payment;

import dev.practice.order.common.exception.InvalidParamException;
import dev.practice.order.domain.order.Order;
import dev.practice.order.domain.order.OrderCommand;
import dev.practice.order.domain.order.payment.PaymentProcessor;
import dev.practice.order.domain.order.payment.validator.PaymentValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentProcessorImpl implements PaymentProcessor {
    private final List<PaymentValidator> paymentValidatorList;
    private final List<PaymentApiCaller> paymentApiCallerList;
    @Override
    public void pay(Order order, OrderCommand.PaymentRequest request) {
        paymentValidatorList.forEach(paymentValidator -> paymentValidator.validate(order, request));
        PaymentApiCaller paymentApiCaller = routingApiCaller(request);
        paymentApiCaller.pay(request);
    }

    private PaymentApiCaller routingApiCaller(OrderCommand.PaymentRequest request) {
        log.info("결제방식::" + request.getPayMethod().name());
        return paymentApiCallerList.stream()
                .filter(paymentApiCaller -> paymentApiCaller.support(request.getPayMethod()))
                .findFirst()
                .orElseThrow(InvalidParamException::new);
    }
}
