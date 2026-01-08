package app.web.dto;


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

    private String invoiceNumber;

    private Date invoiceDate;

    private List<CreateInvoiceItemsRequest> invoiceItems;
}
