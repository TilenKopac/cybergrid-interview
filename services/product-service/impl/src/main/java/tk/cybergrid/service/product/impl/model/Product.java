package tk.cybergrid.service.product.impl.model;

import java.math.BigDecimal;

public record Product(String id, String name, String description, BigDecimal price) {}
