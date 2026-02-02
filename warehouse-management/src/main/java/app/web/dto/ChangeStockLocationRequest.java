package app.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ChangeStockLocationRequest {

    private Long stockId;

    private BigDecimal quantity;

    private String locationCode;
}
