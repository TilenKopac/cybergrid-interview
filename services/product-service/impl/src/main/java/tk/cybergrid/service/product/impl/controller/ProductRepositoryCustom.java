package tk.cybergrid.service.product.impl.controller;

import tk.cybergrid.service.product.impl.model.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepositoryCustom {

    List<Product> findByNamePriceAndDescription(String name, BigDecimal minPrice, BigDecimal maxPrice, String description);

}
