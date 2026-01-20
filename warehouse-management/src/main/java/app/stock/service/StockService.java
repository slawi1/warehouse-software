package app.stock.service;

import app.locations.model.Locations;
import app.locations.service.LocationService;
import app.stock.model.Stock;
import app.stock.repository.StockRepository;
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


    public void changeStockLocations(Long stockId, BigDecimal quantity, String locationCode) {

        Optional<Stock> optionalStock = stockRepository.findStockById(stockId);
        Stock stock = optionalStock.get();
        stock.setQuantity(stock.getQuantity().subtract(quantity));
        stockRepository.save(stock);

        Locations location = locationService.findLocationByCode(locationCode);

        Optional<Stock> optionalStockToMoove = stockRepository.findStockByLocation_CodeAndBatch_BatchNumber(locationCode, stock.getBatch().getBatchNumber());

        if (optionalStockToMoove.isPresent()) {
            Stock stockToMoove = optionalStockToMoove.get();
            stockToMoove.setQuantity(quantity.add(stockToMoove.getQuantity()));
            stockRepository.save(stockToMoove);
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
