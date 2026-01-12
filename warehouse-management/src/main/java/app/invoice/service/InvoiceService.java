package app.invoice.service;

import app.batch.model.Batch;
import app.batch.service.BatchService;
import app.invoice.model.Invoice;
import app.invoice.model.InvoiceItem;
import app.invoice.repository.InvoiceItemRepository;
import app.invoice.repository.InvoiceRepository;
import app.product.model.Product;
import app.product.service.ProductService;
import app.web.dto.CreateInvoiceItemsRequest;
import app.web.dto.CreateInvoiceRequest;
import app.web.dto.InvoiceItemsResult;
import app.web.dto.InvoiceResult;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceItemRepository invoiceItemRepository;
    private final ProductService productService;
    private final BatchService batchService;

    public InvoiceService(InvoiceRepository invoiceRepository, InvoiceItemRepository invoiceItemRepository, ProductService productService, BatchService batchService) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceItemRepository = invoiceItemRepository;
        this.productService = productService;
        this.batchService = batchService;
    }

    public InvoiceResult getInvoiceByNumber(String invoiceNumber) {
        Invoice invoice = invoiceRepository.findInvoiceByInvoiceNumber(invoiceNumber).orElseThrow(() -> new RuntimeException("No invoice found"));

        List<InvoiceItemsResult> itemsResults = new ArrayList<>();

        for (InvoiceItem item : invoice.getInvoiceItems()) {
            InvoiceItemsResult result = InvoiceItemsResult.builder()
                    .itemName(item.getProduct().getName())
                    .price(item.getPrice())
                    .quantity(item.getQuantity())
                    .batchNumber(item.getBatchNumber())
                    .expiryDate(item.getExpiryDate())
                    .build();
            itemsResults.add(result);
        }

        InvoiceResult result = InvoiceResult.builder()
                .invoiceNumber(invoice.getInvoiceNumber())
                .invoiceDate(invoice.getInvoiceDate())
                .invoiceItems(itemsResults)
                .build();

        return result;
    }

    @Transactional
    public void createNewInvoice(CreateInvoiceRequest invoiceRequest) {

        Optional<Invoice> optionalInvoice = invoiceRepository.findInvoiceByInvoiceNumber(invoiceRequest.getInvoiceNumber());
        if (optionalInvoice.isPresent()) {
            throw new RuntimeException("Invoice with this number already exists!");
        }
        List<InvoiceItem> items = new ArrayList<>();

        Invoice invoice = Invoice.builder()
                .invoiceNumber(invoiceRequest.getInvoiceNumber())
                .invoiceDate(invoiceRequest.getInvoiceDate())
                .invoiceItems(items)
                .build();
        invoiceRepository.save(invoice);

        for (CreateInvoiceItemsRequest item : invoiceRequest.getInvoiceItems()) {

            InvoiceItem invoiceItem = InvoiceItem.builder()
                    .price(item.getPrice())
                    .quantity(item.getQuantity())
                    .batchNumber(item.getBatchNumber())
                    .expiryDate(item.getExpiryDate())
                    .build();
            invoiceItem.setInvoice(invoice);
            items.add(invoiceItem);

            Optional<Product> optionalProduct = productService.findProductByName(item.getName());

            Product product;
            if (optionalProduct.isEmpty()) {
                product = Product.builder()
                        .name(item.getName())
                        .build();
                productService.saveProduct(product);
            } else {
                product = optionalProduct.get();
            }
            invoiceItem.setProduct(product);
            invoiceItemRepository.save(invoiceItem);

            Batch batch = Batch.builder()
                    .quantity(item.getQuantity())
                    .product(product)
                    .invoiceItem(invoiceItem)
                    .batchNumber(item.getBatchNumber())
                    .expiryDate(item.getExpiryDate())
                    .build();

            batchService.saveBatch(batch);
        }

    }
}
