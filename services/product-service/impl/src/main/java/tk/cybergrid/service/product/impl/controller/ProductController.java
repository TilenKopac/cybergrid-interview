package tk.cybergrid.service.product.impl.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tk.cybergrid.service.product.impl.model.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable(name = "id") final UUID id) {
        final Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            // product was not found, return 404
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }

        return productOptional.get();
    }

    @GetMapping("/")
    public List<Product> searchProducts(@RequestParam(name = "name", required = false) final String name,
                                        @RequestParam(name = "min_price", required = false) final BigDecimal minPrice,
                                        @RequestParam(name = "max_price", required = false) final BigDecimal maxPrice,
                                        @RequestParam(name = "description", required = false) final String description) {
        return productRepository.findByNamePriceAndDescription(name, minPrice, maxPrice, description);
    }

    @PostMapping("/")
    public Product createProduct(@RequestBody final Product product) {
        return productRepository.save(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable(name = "id") final UUID id, @RequestBody final Product product) {
        final Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            // product was not found, return 404
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }

        // existing product was found, ensure give product has id set and call repository
        product.setId(productOptional.get().getId());
        return productRepository.save(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable(name = "id") final UUID id) {
        productRepository.deleteById(id);
    }

}
