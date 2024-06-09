package tk.cybergrid.service.product.impl.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_NAME_PRICE = "price";
    public static final String COLUMN_NAME_DESCRIPTION = "description";

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = COLUMN_NAME_NAME, length = 50, nullable = false)
    private String name;

    @Column(name = COLUMN_NAME_PRICE, nullable = false)
    private BigDecimal price;

    @Column(name = COLUMN_NAME_DESCRIPTION)
    private String description;

}
