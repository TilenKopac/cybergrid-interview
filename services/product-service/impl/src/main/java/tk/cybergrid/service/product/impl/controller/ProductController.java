package tk.cybergrid.service.product.impl.controller;

import org.springframework.web.bind.annotation.*;
import tk.cybergrid.service.product.impl.model.Product;

import java.math.BigDecimal;

@RestController
public class ProductController {

    @GetMapping("/")
    public Product getProductByName() {
        return new Product("id", "name", "description", new BigDecimal("1.23"));
    }

}
