package app.stock.service;

import app.history.model.MovementHistory;
import app.history.service.MovementHistoryService;
import app.locations.model.Locations;
import app.locations.service.LocationService;
import app.product.model.Product;
import app.product.service.ProductService;
import app.stock.model.Stock;
import app.stock.repository.StockRepository;
import app.web.dto.AllProductQuantityResult;
import app.web.dto.LocationItemResult;
import app.web.dto.LocationItemsResult;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private final LocationService locationService;
    private final ProductService productService;
    private final MovementHistoryService movementHistoryService;


    public StockService(StockRepository stockRepository, LocationService locationService, ProductService productService, MovementHistoryService movementHistoryService) {
        this.stockRepository = stockRepository;
        this.locationService = locationService;
        this.productService = productService;
        this.movementHistoryService = movementHistoryService;
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
        Locations oldLocation = stock.getLocation();
        if (oldLocation.getCode().equals(locationCode)) {
            throw new RuntimeException("New location address must be different from the current!");
        }

        if (stock.getQuantity().compareTo(quantity) < 0) {
            throw new RuntimeException("Quantity you want to move is more than the stock quantity!\n Available quantity is: " + stock.getQuantity());

        } else if (stock.getQuantity().compareTo(quantity) >= 0) {
            stock.setQuantity(stock.getQuantity().subtract(quantity));
            stockRepository.save(stock);
        }

        Locations location = locationService.findLocationByCode(locationCode);
        Optional<Stock> optionalStockToMove = stockRepository.findStockByLocation_CodeAndBatch_BatchNumber(locationCode, stock.getBatch().getBatchNumber());

        if (optionalStockToMove.isPresent()) {
            Stock stockToMove = optionalStockToMove.get();
            stockToMove.setQuantity(stockToMove.getQuantity().add(quantity));
            stockRepository.save(stockToMove);
        } else {
            Stock moovedStock = Stock.builder()
                    .batch(stock.getBatch())
                    .productName(stock.getProductName())
                    .quantity(quantity)
                    .build();
            moovedStock.setLocation(location);
            stockRepository.save(moovedStock);

        }
        if (stock.getQuantity().compareTo(BigDecimal.ZERO) == 0) {
            stockRepository.delete(stock);
        }
        MovementHistory movementHistory = MovementHistory.builder()
                .product(stock.getBatch().getProduct())
                .batch(stock.getBatch())
                .fromLocation(oldLocation)
                .toLocation(location)
                .quantity(quantity)
                .movedAt(LocalDateTime.now())
                .build();
        movementHistoryService.saveMovement(movementHistory);
    }

    public LocationItemsResult findItemsByLocation(String locationCode) {

        Optional<List<Stock>> optionalStocks = stockRepository.findStocksByLocation_Code(locationCode);
        List<Stock> stocks = optionalStocks.get();

        List<LocationItemResult> locationItems = new ArrayList<>();
        for (Stock stock : stocks) {
            LocationItemResult item = LocationItemResult.builder()
                    .productName(stock.getProductName())
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

    public AllProductQuantityResult getAllQuantityByProductName(String productName) {

        Optional<Product> product = productService.findProductByName(productName);
        if (product.isEmpty()) {
            throw new RuntimeException("Product " + productName + " was not found.");
        }

        List<Stock> allProductStocks = stockRepository.findAllByProductName(product.get().getName());

        BigDecimal allQuantity = BigDecimal.ZERO;

        for (Stock stock : allProductStocks) {
            allQuantity = allQuantity.add(stock.getQuantity());
        }

        AllProductQuantityResult result = AllProductQuantityResult.builder()
                .productName(productName)
                .totalQuantity(allQuantity)
                .build();

        if (allQuantity.compareTo(BigDecimal.ZERO) == 0) {
            throw new RuntimeException("Product " + productName + " is out of quantity.");
        }
        return result;
    }
}
