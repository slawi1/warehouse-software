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
public class LocationItemResult {

    private String productName;

    private BigDecimal quantity;

    private String batchNumber;

    private Date expiryDate;
}
