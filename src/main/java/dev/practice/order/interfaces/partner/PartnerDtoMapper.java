package dev.practice.order.interfaces.partner;

import dev.practice.order.domain.partner.PartnerCommand;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface PartnerDtoMapper {

    PartnerCommand of(PartnerDto.RegisterReqeust request);
    PartnerCommand of(PartnerDto.RegisterResponse response);
}
