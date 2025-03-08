package com.bellintegrator.spring_mvc_homework.services;

import com.bellintegrator.spring_mvc_homework.entities.Bill;
import com.bellintegrator.spring_mvc_homework.entities.User;
import com.bellintegrator.spring_mvc_homework.exceptions.BillLimitException;
import com.bellintegrator.spring_mvc_homework.exceptions.BillNotFoundException;
import com.bellintegrator.spring_mvc_homework.repositories.BillRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BillServiceImpl implements BillService {
    private final BillRepository billRepository;

    @Override
    @Transactional
    public Bill save(Bill bill) {
        log.info("Создаем/обновляем счет типа {} для пользователя {}", bill.getBillType(), bill.getUser().getId());
        return billRepository.save(bill);
    }

    @Override
    public List<Bill> getAllByUserId(Long id) {
        log.info("Возвращаем все счтета пользователя с id:{} ", id);
        return billRepository.getAllByUserId(id);
    }

    @Override
    public Optional<Bill> getBillByUserIdAndType(Long id, Bill.BillType billType) {
        log.info("Получаем счета типа {} у пользователя с id {}", billType, id);
        return billRepository.getBillByUserIdAndType(id, billType);
    }

    @Override
    public Bill findById(Long billId) {
        log.info("Возвращаем счет с id: {}", billId);
        return billRepository.findById(billId).orElseThrow(() -> new BillNotFoundException("Bill not found " + billId));
    }

    @Override
    @Transactional
    public Bill createBill(Bill.BillType billType, Long balance, User user) {
        log.info("Создаем новый счет типа {} для пользователя {}", billType, user.getId());
        Bill bill = new Bill();
        bill.setBillType(billType);
        bill.setBalance(balance);
        bill.setUser(user);
        return billRepository.save(bill);
    }

    @Override
    @Transactional
    public Bill addToBalance(Long id, Long amount) {
        log.info("Увеличиваем балансу счета с id {} сумму {}", id, amount);
        Bill bill = findById(id);
        bill.setBalance(bill.getBalance() + amount);
        return bill;
    }

    @Override
    @Transactional
    public Bill subtractFromBalance(Long id, Long amount) {
        log.info("Уменьшаем баланс счета с id {} на сумму {}", id, amount);
        Bill bill = findById(id);
        if (amount > bill.getBalance()) {
            log.warn("Сумма снятия превышает баланс счета");
            throw new BillLimitException("Сумма снятия превышает баланс счета");
        }
        bill.setBalance(bill.getBalance() - amount);
        return bill;
    }
}
