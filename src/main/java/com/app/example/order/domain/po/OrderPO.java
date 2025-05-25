package com.app.example.order.domain.po;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Table("orders")
@Getter
@Setter
public class OrderPO {
    @Id
    private UUID id;
    @Column("client_id")
    private UUID clientId;
    @Column("total_amount")
    private BigDecimal totalAmount;
    private String status;
    @Column("created_at")
    private LocalDateTime createdAt;
}
