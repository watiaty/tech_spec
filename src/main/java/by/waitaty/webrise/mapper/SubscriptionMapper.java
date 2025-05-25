package by.waitaty.webrise.mapper;

import by.waitaty.webrise.dto.response.SubscriptionResponseDto;
import by.waitaty.webrise.model.entity.Subscription;
import java.util.Set;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    SubscriptionResponseDto toDto(Subscription subscription);

    Set<SubscriptionResponseDto> toSetDto(Set<Subscription> subscriptions);

}
