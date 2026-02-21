package app.invoice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String invoiceNumber;

    private Date invoiceDate;

    @OneToMany(mappedBy = "invoice")
    private List<InvoiceItem> invoiceItems;


}
