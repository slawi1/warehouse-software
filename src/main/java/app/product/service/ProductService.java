package app.product.service;

import app.product.model.Product;
import app.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Optional<Product> findProductByName(String name) {

        return productRepository.findProductByName(name);
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }
}
