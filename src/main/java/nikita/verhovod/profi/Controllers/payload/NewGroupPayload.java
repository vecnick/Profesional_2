package nikita.verhovod.profi.Controllers.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewGroupPayload(
    @NotBlank String name,
    @Nullable Integer parentId
) {}
