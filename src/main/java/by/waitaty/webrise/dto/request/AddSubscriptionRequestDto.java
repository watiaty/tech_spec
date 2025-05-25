package by.waitaty.webrise.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AddSubscriptionRequestDto(

    @NotNull
    @Size(max = 50, message = "Название подписки не должно превышать 50 символов")
    String name

) { }
