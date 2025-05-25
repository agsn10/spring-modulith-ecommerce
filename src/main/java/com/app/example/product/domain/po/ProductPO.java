package com.app.example.product.domain.po;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Table("products")
@Getter
@Setter
public class ProductPO {

    @Id
    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    @Column("stock_quantity")
    private Integer stockQuantity;
    private String category;
}
