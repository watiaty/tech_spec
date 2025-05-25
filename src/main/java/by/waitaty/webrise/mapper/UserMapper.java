package by.waitaty.webrise.mapper;

import by.waitaty.webrise.dto.request.CreateUserRequestDto;
import by.waitaty.webrise.dto.response.CreateUserResponseDto;
import by.waitaty.webrise.dto.response.UserResponseDto;
import by.waitaty.webrise.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = SubscriptionMapper.class)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "subscriptions", ignore = true)
    User toEntity(CreateUserRequestDto requestDto);

    CreateUserResponseDto toCreateResponseDto(User user);

    UserResponseDto toDto(User user);

}