package by.waitaty.webrise.dto.response;

import java.util.Set;

public record UserResponseDto(
    Long id,
    String name,
    String email,
    Set<SubscriptionResponseDto> subscriptions
) {
}
