package by.waitaty.webrise.service;

import by.waitaty.webrise.dto.request.AddSubscriptionRequestDto;
import by.waitaty.webrise.dto.request.CreateUserRequestDto;
import by.waitaty.webrise.dto.request.UpdateUserRequestDto;
import by.waitaty.webrise.dto.response.CreateUserResponseDto;
import by.waitaty.webrise.dto.response.SubscriptionResponseDto;
import by.waitaty.webrise.dto.response.UserResponseDto;
import java.util.Set;

public interface UserService {

    CreateUserResponseDto createUser(CreateUserRequestDto requestDto);

    UserResponseDto getUserInfo(Long id);

    UserResponseDto updateUser(Long id, UpdateUserRequestDto requestDto);

    void deleteUser(Long id);

    void addSubscription(Long userId, AddSubscriptionRequestDto requestDto);

    Set<SubscriptionResponseDto> getSubscriptions(Long userId);

    void deleteSubscription(Long userId, Long subscriptionId);

}
