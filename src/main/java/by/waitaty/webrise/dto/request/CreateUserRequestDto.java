package by.waitaty.webrise.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequestDto (

    @Size(max = 50, message = "Email не должен превышать 50 символов")
    @NotBlank(message = "Email не должен быть пустым")
    @Email(message = "Некорректный формат email")
    String email,

    @Size(min = 3, max = 50, message = "Имя должно быть больше 3 символов и не превышать 50 символов")
    String name

) { }
