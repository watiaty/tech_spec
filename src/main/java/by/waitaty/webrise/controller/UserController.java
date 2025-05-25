package by.waitaty.webrise.controller;

import by.waitaty.webrise.dto.request.AddSubscriptionRequestDto;
import by.waitaty.webrise.dto.request.CreateUserRequestDto;
import by.waitaty.webrise.dto.request.UpdateUserRequestDto;
import by.waitaty.webrise.dto.response.CreateUserResponseDto;
import by.waitaty.webrise.dto.response.SubscriptionResponseDto;
import by.waitaty.webrise.dto.response.UserResponseDto;
import by.waitaty.webrise.service.UserService;
import jakarta.validation.Valid;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {

    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CreateUserResponseDto createUser(@RequestBody @Valid CreateUserRequestDto requestDto) {
        return userService.createUser(requestDto);
    }

    @GetMapping("/{id}")
    public UserResponseDto getUserInfo(@PathVariable Long id) {
        return userService.getUserInfo(id);
    }

    @PutMapping("/{id}")
    public UserResponseDto updateUser(
            @RequestBody @Valid UpdateUserRequestDto requestDto,
            @PathVariable Long id) {
        return userService.updateUser(id, requestDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PostMapping("/{id}/subscriptions")
    public void getSubscriptions(
            @PathVariable(name = "id") Long userId,
            @RequestBody @Valid AddSubscriptionRequestDto requestDto) {
        userService.addSubscription(userId, requestDto);
    }

    @GetMapping("/{id}/subscriptions")
    public Set<SubscriptionResponseDto> getSubscriptions(@PathVariable(name = "id") Long userId) {
        return userService.getSubscriptions(userId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}/subscriptions/{sub_id}")
    public void deleteSubscription(
            @PathVariable(name = "id") Long userId,
            @PathVariable(name = "sub_id") Long subscriptionId) {
        userService.deleteSubscription(userId, subscriptionId);
    }

}
