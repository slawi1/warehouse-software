package app.history.model;

import app.batch.model.Batch;
import app.locations.model.Locations;
import app.product.model.Product;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class MovementHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "batch_id")
    private Batch batch;

    @ManyToOne
    @JoinColumn(name = "from_location_id")
    private Locations fromLocation;

    @ManyToOne
    @JoinColumn(name = "to_location_id")
    private Locations toLocation;

    private BigDecimal quantity;

    private LocalDateTime movedAt;
}
