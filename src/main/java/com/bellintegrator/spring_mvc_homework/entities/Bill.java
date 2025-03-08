package com.bellintegrator.spring_mvc_homework.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "bills")
public class Bill {
    // есть ссылка в listAllBills.html -  com.bellintegrator.spring_mvc_homework.entities.Bill.BillType
    public enum BillType {
        DEPOSIT,
        DEBIT_CART,
        CREDIT_CART
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "bill_type", nullable = false)
    private BillType billType;

    @Column(name = "balance", nullable = false)
    private Long balance;

}
