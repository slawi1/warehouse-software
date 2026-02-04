package app.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ChangeStockLocationRequest {

    private Long stockId;

    @Positive(message = "Quantity must be positive number!")
    @NotNull(message = "Quantity can not be null!")
    private BigDecimal quantity;

    @Size(min = 4, message = "Location code must be at least 4 characters long!")
    @NotBlank(message = "Location code can not be blank!")
    private String locationCode;
}
