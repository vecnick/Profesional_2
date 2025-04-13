package nikita.verhovod.profi.Controllers.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateGroupPayload(
    @Nullable String name,
    @Nullable Integer parentId
) {}
