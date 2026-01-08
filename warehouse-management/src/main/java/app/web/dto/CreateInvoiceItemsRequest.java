package app.web.dto;

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

    private String name;

    private BigDecimal quantity;

    private BigDecimal price;

    private String batchNumber;

    private Date expiryDate;
}
