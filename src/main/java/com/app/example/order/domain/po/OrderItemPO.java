package com.app.example.order.domain.po;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Table("order_items")
@Getter
@Setter
public class OrderItemPO {
    @Column("order_id")
    private UUID orderId;
    @Column("product_id")
    private UUID productId;
    @Column("product_name")
    private String productName;
    private Integer quantity;
    @Column("unit_price")
    private BigDecimal unitPrice;
    @Column("total_price")
    private BigDecimal totalPrice;
}
