package dev.practice.order.domain.partner;

import org.springframework.context.annotation.Primary;

public interface PartnerReader {
    Partner getPartner(String partnerToken);
}
