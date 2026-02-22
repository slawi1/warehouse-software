package app.web;

import app.invoice.service.InvoiceService;
import app.web.dto.CreateInvoiceRequest;
import app.web.dto.InvoiceResult;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/create")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }


    @GetMapping("/{id}")
    public InvoiceResult getInvoice(@PathVariable String id) {

        return invoiceService.getInvoiceByNumber(id);
    }

    @PostMapping("/create")
    public void createNewInvoice(@Valid @RequestBody CreateInvoiceRequest invoiceRequest) {

        invoiceService.createNewInvoice(invoiceRequest);

    }
}
