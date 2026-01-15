package app.stock.model;

import app.batch.model.Batch;
import app.locations.model.Locations;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "batch_id")
    private Batch batch;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Locations location;

    private BigDecimal quantity;

}
