package app.batch.service;

import app.batch.model.Batch;
import app.batch.repository.BatchRepository;
import org.springframework.stereotype.Service;

@Service
public class BatchService {

    private final BatchRepository batchRepository;

    public BatchService(BatchRepository batchRepository) {
        this.batchRepository = batchRepository;
    }

    public void saveBatch(Batch batch) {
        batchRepository.save(batch);
    }

}
