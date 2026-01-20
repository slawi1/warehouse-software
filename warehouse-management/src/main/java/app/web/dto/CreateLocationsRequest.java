package app.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateLocationsRequest {

    @NotNull
    private String code;

    private String description;
}
