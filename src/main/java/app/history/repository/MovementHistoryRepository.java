package app.history.repository;

import app.history.model.MovementHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovementHistoryRepository extends JpaRepository<MovementHistory, Long> {
}
