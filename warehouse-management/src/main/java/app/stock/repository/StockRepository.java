package app.stock.repository;

import app.stock.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    Optional<Stock> findStockById(long id);

    Optional<Stock> findStockByLocation_CodeAndBatch_BatchNumber(String locationCode, String batchNumber);

    Optional<List<Stock>> findStocksByLocation_Code(String locationCode);

}
