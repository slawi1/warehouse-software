package app.web.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateInvoiceItemsRequest {

    @NotBlank(message = "Item name can not be blank!")
    private String name;

    @Positive(message = "Quantity must be positive number!")
    @NotNull(message = "Quantity can not be null!")
    private BigDecimal quantity;

    @Positive(message = "Price must be positive number!")
    @NotNull(message = "Price can not be null!")
    private BigDecimal price;

    @NotBlank(message = "Batch number can not be blank!")
    private String batchNumber;

    @NotNull(message = "Expiry date can not be null!")
    @Future(message = "Expiry date must be in the future!")
    private Date expiryDate;
}
