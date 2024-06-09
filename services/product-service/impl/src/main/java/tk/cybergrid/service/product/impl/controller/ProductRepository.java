package tk.cybergrid.service.product.impl.controller;

import org.springframework.data.jpa.repository.JpaRepository;
import tk.cybergrid.service.product.impl.model.Product;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID>, ProductRepositoryCustom {

    Product findByName(String name);

}
