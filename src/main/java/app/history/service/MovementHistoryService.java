package app.history.service;

import app.history.model.MovementHistory;
import app.history.repository.MovementHistoryRepository;
import org.springframework.stereotype.Service;

@Service
public class MovementHistoryService {

    private final MovementHistoryRepository movementHistoryRepository;

    public MovementHistoryService(MovementHistoryRepository movementHistoryRepository) {
        this.movementHistoryRepository = movementHistoryRepository;
    }

    public void saveMovement(MovementHistory movementHistory){
        movementHistoryRepository.save(movementHistory);
    }
}
