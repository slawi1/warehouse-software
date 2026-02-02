package app.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductNameRequest {

    @NotBlank
    private String productName;
}
