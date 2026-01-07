package app.invoice.service;

import app.invoice.model.Invoice;
import app.invoice.model.InvoiceItem;
import app.invoice.repository.InvoiceRepository;
import app.web.dto.InvoiceItemsResult;
import app.web.dto.InvoiceResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
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

}
