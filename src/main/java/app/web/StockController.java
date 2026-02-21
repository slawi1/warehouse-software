package app.web;

import app.stock.service.StockService;
import app.web.dto.AllProductQuantityResult;
import app.web.dto.LocationItemsResult;
import app.web.dto.ProductNameRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/get-items/{code}")
    public LocationItemsResult getAllItemsOnLocation(@PathVariable String code) {
        return stockService.findItemsByLocation(code);
    }


    @GetMapping("/get-item-quantity")
    public AllProductQuantityResult getAllProductQuantity(@Valid @RequestBody ProductNameRequest request) {
        return stockService.getAllQuantityByProductName(request.getProductName());
    }
}
