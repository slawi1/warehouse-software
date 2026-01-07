package app.batch.model;

import app.invoice.model.InvoiceItem;
import app.product.model.Product;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "invoice_item_id")
    private InvoiceItem invoiceItem;

    private String batchNumber;

    private Date expiryDate;

    private int quantity;


}
