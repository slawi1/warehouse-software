package app.product.model;

import app.batch.model.Batch;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String unit;

    @OneToMany(mappedBy = "product")
    private List<Batch> batch;


}
