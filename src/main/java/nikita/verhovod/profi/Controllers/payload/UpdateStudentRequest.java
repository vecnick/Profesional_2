package nikita.verhovod.profi.Controllers.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateStudentRequest(
    @NotBlank String name,
    @NotNull @JsonProperty("group_id") Integer groupId
) {}
