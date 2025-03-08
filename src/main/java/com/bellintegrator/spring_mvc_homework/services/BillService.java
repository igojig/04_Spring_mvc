package com.bellintegrator.spring_mvc_homework.services;

import com.bellintegrator.spring_mvc_homework.entities.Bill;
import com.bellintegrator.spring_mvc_homework.entities.User;

import java.util.List;
import java.util.Optional;

public interface BillService {

    public Bill save(Bill bill);

    public List<Bill> getAllByUserId(Long id);

    public Optional<Bill> getBillByUserIdAndType(Long id, Bill.BillType billType);

    public Bill findById(Long billId);

    public Bill createBill(Bill.BillType billType, Long balance, User user);

    Bill addToBalance(Long id, Long amount);

    Bill subtractFromBalance(Long id, Long amount);
}
