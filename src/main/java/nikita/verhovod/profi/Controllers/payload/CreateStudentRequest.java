package nikita.verhovod.profi.Controllers.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateStudentRequest(
    @NotBlank String name,
    @Email String email,
    @NotNull @JsonProperty("group_id") Integer groupId
) {}
