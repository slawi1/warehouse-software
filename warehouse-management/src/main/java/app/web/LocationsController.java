package app.web;

import app.locations.service.LocationService;
import app.stock.service.StockService;
import app.web.dto.ChangeStockLocationRequest;
import app.web.dto.CreateLocationsRequest;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class LocationsController {

    private final LocationService locationService;
    private final StockService stockService;

    public LocationsController(LocationService locationService, StockService stockService) {
        this.locationService = locationService;
        this.stockService = stockService;
    }

    @PostMapping("/create-locations")
    public void createNewInvoice(@RequestBody List<CreateLocationsRequest> createLocationsRequest) {
        locationService.createLocations(createLocationsRequest);
    }

    @PutMapping("/change-stock-location")
    public void changeStockLocation(@RequestBody ChangeStockLocationRequest request) {
        stockService.changeStockLocations(request.getStockId(), request.getQuantity(), request.getLocationCode());
    }

}
