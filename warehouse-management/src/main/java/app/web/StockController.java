package app.web;

import app.stock.service.StockService;
import app.web.dto.AllProductQuantityResult;
import app.web.dto.LocationItemsResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/get-items/{id}")
    public LocationItemsResult getAllItemsOnLocation(@PathVariable String id) {
        return stockService.findItemsByLocation(id);
    }


    @GetMapping("/get-item-quantity")
    public AllProductQuantityResult getAllProductQuantity(@RequestParam String name) {
        return stockService.getAllQuantityByProductName(name);
    }
}
