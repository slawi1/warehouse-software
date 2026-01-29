package app.stock.service;

import app.locations.model.Locations;
import app.locations.service.LocationService;
import app.stock.model.Stock;
import app.stock.repository.StockRepository;
import app.web.dto.LocationItemResult;
import app.web.dto.LocationItemsResult;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private final LocationService locationService;


    public StockService(StockRepository stockRepository, LocationService locationService) {
        this.stockRepository = stockRepository;
        this.locationService = locationService;
    }

    public void saveStock(Stock stock) {
        stockRepository.save(stock);
    }

    @Transactional
    public void changeStockLocations(Long stockId, BigDecimal quantity, String locationCode) {

        Optional<Stock> optionalStock = stockRepository.findStockById(stockId);

        if (optionalStock.isEmpty()) {
            throw new RuntimeException("Stock does not exists!");
        }

        Stock stock = optionalStock.get();

        if (stock.getLocation().getCode().equals(locationCode)) {
            throw new RuntimeException("New location address must be different from the current!");
        }

        if (stock.getQuantity().compareTo(quantity) < 0) {
            throw new RuntimeException("Quantity you want to move is more than the stock quantity!\n Available quantity is: " + stock.getQuantity());

        } else if (stock.getQuantity().compareTo(quantity) >= 0) {
            stock.setQuantity(stock.getQuantity().subtract(quantity));
        }

        stockRepository.save(stock);
        Locations location = locationService.findLocationByCode(locationCode);
        Optional<Stock> optionalStockToMove = stockRepository.findStockByLocation_CodeAndBatch_BatchNumber(locationCode, stock.getBatch().getBatchNumber());

        if (optionalStockToMove.isPresent()) {
            Stock stockToMove = optionalStockToMove.get();
            stockToMove.setQuantity(stockToMove.getQuantity().add(quantity));
            stockRepository.save(stockToMove);
        } else {
            Stock moovedStock = Stock.builder()
                    .batch(stock.getBatch())
                    .prodName(stock.getProdName())
                    .quantity(quantity)
                    .build();
            moovedStock.setLocation(location);
            stockRepository.save(moovedStock);

        }
        if (stock.getQuantity().compareTo(BigDecimal.ZERO) == 0) {
            stockRepository.delete(stock);
        }
    }

    public LocationItemsResult findItemsByLocation(String locationCode) {

        Optional<List<Stock>> optionalStocks = stockRepository.findStocksByLocation_Code(locationCode);
        List<Stock> stocks = optionalStocks.get();

        List<LocationItemResult> locationItems = new ArrayList<>();
        for (Stock stock : stocks) {
            LocationItemResult item = LocationItemResult.builder()
                    .productName(stock.getProdName())
                    .quantity(stock.getQuantity())
                    .batchNumber(stock.getBatch().getBatchNumber())
                    .expiryDate(stock.getBatch().getExpiryDate())
                    .build();

            locationItems.add(item);
        }

        return LocationItemsResult.builder()
                .items(locationItems)
                .code(locationCode)
                .build();

    }
}
