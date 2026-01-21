package app.stock.service;

import app.locations.model.Locations;
import app.locations.service.LocationService;
import app.stock.model.Stock;
import app.stock.repository.StockRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
        if (optionalStock.isPresent()) {
            Stock stock = optionalStock.get();
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
        }
    }
}
