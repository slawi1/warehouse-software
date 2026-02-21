package app.web.dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateInvoiceRequest {

    @NotBlank(message = "Invoice number can not be blank!")
    private String invoiceNumber;

    @NotNull(message = "Invoice date can not be null!")
    private Date invoiceDate;

    @Valid
    private List<CreateInvoiceItemsRequest> invoiceItems;
}
