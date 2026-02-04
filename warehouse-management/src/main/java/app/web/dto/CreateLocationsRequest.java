package app.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateLocationsRequest {

    @Size(min = 4, message = "Location code must be at least 4 characters long!")
    @NotBlank(message = "Location code can not be blank!")
    private String code;

    private String description;
}
