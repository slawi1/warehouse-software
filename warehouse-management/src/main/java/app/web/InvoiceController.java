package app.web;

import app.invoice.service.InvoiceService;
import app.web.dto.InvoiceResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }


    @GetMapping("/{id}")
    public InvoiceResult getHome(@PathVariable String id) {


        return invoiceService.getInvoiceByNumber(id);
    }

}
