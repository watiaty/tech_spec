package by.waitaty.webrise.service.impl;

import by.waitaty.webrise.common.ErrorMessages;
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
import by.waitaty.webrise.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SubscriptionService subscriptionService;
    private final UserMapper userMapper;
    private final SubscriptionMapper subscriptionMapper;

    @Override
    @Transactional
    public CreateUserResponseDto createUser(CreateUserRequestDto requestDto) {
        String email = requestDto.email();

        log.info("Проверка существования пользователя с email: {}", email);

        userRepository.findByEmail(email).ifPresent(user -> {
            throw new EmailAlreadyExistsException(ErrorMessages.EMAIL_ALREADY_EXISTS);
        });

        log.info("Создание нового пользователя с email: {}", email);

        User user = userRepository.save(userMapper.toEntity(requestDto));
        return userMapper.toCreateResponseDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getUserInfo(Long id) {
        log.info("Получение информации о пользователе с ID: {}", id);

        User user = findUserWithSubscriptionsByIdOrThrow(id);
        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public UserResponseDto updateUser(Long id, UpdateUserRequestDto requestDto) {
        log.info("Обновление пользователя с ID: {}", id);

        User user = findUserByIdOrThrow(id);

        user.setName(requestDto.name());
        user.setEmail(requestDto.email());

        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        log.info("Удаление пользователя с ID: {}", id);

        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void addSubscription(Long id, AddSubscriptionRequestDto requestDto) {
        User user = findUserByIdOrThrow(id);
        String subscriptionName = requestDto.name();

        log.info("Добавление подписки '{}' для пользователя с ID: {}", subscriptionName, id);

        if (user.getSubscriptions().stream().anyMatch(s -> s.getName().equals(subscriptionName))) {
            throw new AlreadySubscribedException(ErrorMessages.USER_ALREADY_SUBSCRIBED);
        }

        Subscription subscription = subscriptionService.getSubscription(subscriptionName);
        user.getSubscriptions().add(subscription);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<SubscriptionResponseDto> getSubscriptions(Long id) {
        log.info("Получение подписок пользователя с ID: {}", id);

        User user = findUserWithSubscriptionsByIdOrThrow(id);

        return subscriptionMapper.toSetDto(user.getSubscriptions());
    }

    @Override
    @Transactional
    public void deleteSubscription(Long id, Long subscriptionId) {
        log.info("Удаление подписки с ID: {} у пользователя с ID: {}", subscriptionId, id);

        userRepository.deleteSubscriptionByIdAndSubscriptionId(id, subscriptionId);
    }

    private User findUserByIdOrThrow(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.USER_NOT_FOUND));
    }

    private User findUserWithSubscriptionsByIdOrThrow(Long id) {
        return userRepository.findWithSubscriptionsById(id)
            .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.USER_NOT_FOUND));
    }
}
