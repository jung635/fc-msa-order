package dev.practice.order.interfaces.item;

import dev.practice.order.domain.item.ItemCommand;
import dev.practice.order.domain.item.ItemInfo;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring", // impl은 스프링의 싱글톤 빈으로 관리한다. 즉 빌드 시 구현체를 만들고 빈으로 등록한다. ( @Component를 붙임 )
        injectionStrategy = InjectionStrategy.CONSTRUCTOR, // 생성자 주입 전략
        unmappedTargetPolicy = ReportingPolicy.ERROR //일치하지 않는 필드가 있으면 빌드 시 에러
)
public interface ItemDtoMapper {

    /*주의점
     * 변환 선언한 클래스 내에 컬렉션이 있을 경우 한번 더 맵핑 해주어야한다.
     * itemDto -> itemCommand 기본 매퍼
     * itemDto 내의 collection 요소, 즉 itemOptionGroupDto -> ItemOptionGroupCommand
     * 하기 세개 항목
     * */
    
    //조건이 여러개인 경우 @Mappings 사용
    @Mappings({
            @Mapping(source = "itemOptionGroupList", target = "itemOptionGroupRequestList")
    })
    ItemCommand.RegisterItemRequest of(ItemDto.RegisterItemRequest request);
    @Mappings({
            @Mapping(source = "itemOptionList", target = "itemOptionRequestList")
    })
    ItemCommand.RegisterItemOptionGroupRequest of(ItemDto.RegisterItemOptionGroupRequest request);
    ItemCommand.RegisterItemOptionRequest of(ItemDto.RegisterItemOptionRequest request);

    ItemDto.RegisterResponse of(String itemToken);

    ItemDto.Main of(ItemInfo.Main main);

    ItemDto.ItemOptionGroupInfo of(ItemInfo.ItemOptionGroupInfo itemOptionGroupInfo);

    ItemDto.ItemOptionInfo of(ItemInfo.ItemOptionInfo itemOptionInfo);
}
