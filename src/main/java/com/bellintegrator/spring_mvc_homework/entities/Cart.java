package com.bellintegrator.spring_mvc_homework.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

@Entity
@Table(name = "carts")
@Data
public class Cart {

    @Getter
    public enum CartStatus {
        ACTIVE("активная"),
        BLOCKED("заблокирована"),
        CLOSED("закрыта");
        private final String name;

        CartStatus(String name) {
            this.name = name;
        }
    }

    public enum PaymentSystem {
        MIR,
        UNION_PAY,
        VISA,
        MASTERCARD;
    }

    @Getter
    public enum CartType {
        DEBIT("дебитовая"),
        CREDIT("кредитная");
        private final String name;

        CartType(String name) {
            this.name = name;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "balance", nullable = false)
    private Long balance;

    @Column(name = "cart_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private CartStatus cartStatus;

    @Column(name = "cart_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CartType cartType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_id", nullable = false)
    private Bill bill;

    @Column(name = "payment_system", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentSystem paymentSystem;
}
