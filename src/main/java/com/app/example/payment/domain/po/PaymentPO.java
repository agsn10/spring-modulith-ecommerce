package com.app.example.payment.domain.po;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table("payments")
@Getter
@Setter
public class PaymentPO {
    @Id
    private UUID id;
    @Column("order_id")
    private UUID orderId;
    private String status;
    private String method;
    private BigDecimal amount;
    @Column("paid_at")
    private LocalDateTime paidAt;
}
