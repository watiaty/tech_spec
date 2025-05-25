package by.waitaty.webrise.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import by.waitaty.webrise.dto.request.AddSubscriptionRequestDto;
import by.waitaty.webrise.dto.request.CreateUserRequestDto;
import by.waitaty.webrise.dto.request.UpdateUserRequestDto;
import by.waitaty.webrise.dto.response.CreateUserResponseDto;
import by.waitaty.webrise.dto.response.SubscriptionResponseDto;
import by.waitaty.webrise.dto.response.UserResponseDto;
import by.waitaty.webrise.exception.AlreadySubscribedException;
import by.waitaty.webrise.exception.EmailAlreadyExistsException;
import by.waitaty.webrise.mapper.SubscriptionMapper;
import by.waitaty.webrise.mapper.UserMapper;
import by.waitaty.webrise.model.entity.Subscription;
import by.waitaty.webrise.model.entity.User;
import by.waitaty.webrise.repository.UserRepository;
import by.waitaty.webrise.service.SubscriptionService;
import jakarta.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private SubscriptionService subscriptionService;
    @Mock
    private UserMapper userMapper;
    @Mock
    private SubscriptionMapper subscriptionMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUser_success() {
        CreateUserRequestDto requestDto = new CreateUserRequestDto("test@mail.com", "Test User");

        User user = new User();
        user.setEmail("test@mail.com");
        user.setName("Test User");

        CreateUserResponseDto responseDto = new CreateUserResponseDto(1L, "test@mail.com");

        when(userRepository.findByEmail("test@mail.com")).thenReturn(Optional.empty());
        when(userMapper.toEntity(requestDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toCreateResponseDto(user)).thenReturn(responseDto);

        CreateUserResponseDto result = userService.createUser(requestDto);

        assertEquals(responseDto, result);
    }

    @Test
    void createUser_alreadyExists() {
        CreateUserRequestDto requestDto = new CreateUserRequestDto("test@mail.com", "Test User");

        when(userRepository.findByEmail("test@mail.com")).thenReturn(Optional.of(new User()));

        assertThrows(EmailAlreadyExistsException.class, () -> userService.createUser(requestDto));
    }

    @Test
    void getUserInfo_success() {
        User user = new User();
        user.setId(1L);

        UserResponseDto dto = new UserResponseDto(1L, "test@mail.com", "Test User", Set.of());

        when(userRepository.findWithSubscriptionsById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(dto);

        UserResponseDto result = userService.getUserInfo(1L);

        assertEquals(dto, result);
    }

    @Test
    void getUserInfo_notFound() {
        when(userRepository.findWithSubscriptionsById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.getUserInfo(1L));
    }

    @Test
    void updateUser_success() {
        UpdateUserRequestDto dto = new UpdateUserRequestDto("new@mail.com", "New Name");

        User user = new User();
        user.setId(1L);
        user.setEmail("old@mail.com");
        user.setName("Old Name");

        User updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setEmail("new@mail.com");
        updatedUser.setName("New Name");

        UserResponseDto responseDto = new UserResponseDto(1L, "New Name","new@mail.com", Set.of());

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);
        when(userMapper.toDto(any(User.class))).thenReturn(responseDto);

        UserResponseDto result = userService.updateUser(1L, dto);

        assertEquals("new@mail.com", result.email());
    }

    @Test
    void deleteUser_success() {
        doNothing().when(userRepository).deleteById(1L);

        assertDoesNotThrow(() -> userService.deleteUser(1L));
    }

    @Test
    void addSubscription_success() {
        AddSubscriptionRequestDto dto = new AddSubscriptionRequestDto("Netflix");
        User user = new User();
        user.setSubscriptions(new HashSet<>());

        Subscription subscription = new Subscription();
        subscription.setName("Netflix");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(subscriptionService.getSubscription("Netflix")).thenReturn(subscription);

        userService.addSubscription(1L, dto);

        assertTrue(user.getSubscriptions().contains(subscription));
    }

    @Test
    void addSubscription_alreadySubscribed() {
        Subscription existing = new Subscription();
        existing.setName("Netflix");

        AddSubscriptionRequestDto dto = new AddSubscriptionRequestDto("Netflix");

        User user = new User();
        user.setSubscriptions(Set.of(existing));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        assertThrows(AlreadySubscribedException.class, () -> userService.addSubscription(1L, dto));
    }

    @Test
    void getSubscriptions_success() {
        Subscription subscription = new Subscription();
        subscription.setName("Netflix");

        User user = new User();
        user.setSubscriptions(Set.of(subscription));

        Set<SubscriptionResponseDto> response = Set.of(new SubscriptionResponseDto(1L, "Netflix"));

        when(userRepository.findWithSubscriptionsById(1L)).thenReturn(Optional.of(user));
        when(subscriptionMapper.toSetDto(user.getSubscriptions())).thenReturn(response);

        Set<SubscriptionResponseDto> result = userService.getSubscriptions(1L);

        assertEquals(response, result);
    }

    @Test
    void deleteSubscription_success() {
        doNothing().when(userRepository).deleteSubscriptionByIdAndSubscriptionId(1L, 2L);

        assertDoesNotThrow(() -> userService.deleteSubscription(1L, 2L));
    }
}
