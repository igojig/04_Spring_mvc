package com.bellintegrator.spring_mvc_homework.controllers;

import com.bellintegrator.spring_mvc_homework.dtos.BillDto;
import com.bellintegrator.spring_mvc_homework.entities.Bill;
import com.bellintegrator.spring_mvc_homework.entities.User;
import com.bellintegrator.spring_mvc_homework.mappers.BillMapper;
import com.bellintegrator.spring_mvc_homework.model.BillFormModel;
import com.bellintegrator.spring_mvc_homework.services.BillService;
import com.bellintegrator.spring_mvc_homework.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/bills")
@RequiredArgsConstructor
@Slf4j
public class BillController {

    private final UserService userService;
    private final BillService billService;
    private final BillMapper billMapper;

    @GetMapping
    public String showAll(Model model) {
        log.info("Выводим список всех счетов");
        User authUser = userService.getAuthUser();
        Map<Bill.Type, List<BillDto>> mapBillByType = billService.getAllByUserId(authUser.getId()).stream()
                .map(billMapper::toDto)
                .collect(Collectors.groupingBy(BillDto::getType));

        model.addAttribute("bills", mapBillByType);
        return "bills/listAllBills";
    }

    @GetMapping("/openDeposit")
    public String openDeposit(Model model) {
        log.info("Передаем View для открытия депозитного счета");
        BillDto billDto = new BillDto();
        model.addAttribute("bill", billDto);
        return "bills/openDepositBill";
    }

    @PostMapping("/deposit")
    public String openDeposit(@ModelAttribute("bill") @Valid BillDto billDto, BindingResult bindingResult) {
        log.info("Получили данные из View для открытия депозитнго счета");
        if (bindingResult.hasErrors()) {
            log.info("Ошибки в модели при создании депозитного счета");
            return "bills/openDepositBill";
        }

        Bill bill=billMapper.toEntityWithDepositTypeAndAuthUser(billDto);
        bill = billService.save(bill);
        log.info("Сохранили депозитный счет id: {}", bill.getId());
        return "redirect:/bills";
    }

    @PostMapping("/deposit/put")
    public String putToDeposit(@RequestParam("billId") Long billId, Model model) {
        log.info("Передаем View для внесения суммы на депозит");
        BillFormModel billFormModel = new BillFormModel();
        billFormModel.setId(billId);
        model.addAttribute("model", billFormModel);
        return "bills/putToDeposit";
    }

    @PostMapping("/putToDeposit")
    public String putToDeposit(@Valid @ModelAttribute("model") BillFormModel model, BindingResult bindingResult) {
        log.info("Получили данные из View для пополения депозитного счета");
        if (bindingResult.hasErrors()) {
            log.info("Ошибки в модели при пополнении депозитного счета");
            return "bills/putToDeposit";
        }

        Bill bill=billService.addToBalance(model.getId(), model.getAmount());
        return "redirect:/bills";
    }

    @PostMapping("/deposit/get")
    public String getFromDeposit(@RequestParam("billId") Long billId, Model model) {
        log.info("Передаем View для снятия суммы с депозита");
        BillFormModel billFormModel = new BillFormModel();
        billFormModel.setId(billId);
        model.addAttribute("model", billFormModel);
        return "bills/getFromDeposit";
    }

    @PostMapping("/getFromDeposit")
    public String getFromDeposit(@Valid @ModelAttribute("model") BillFormModel model, BindingResult bindingResult) {
        log.info("Получили данные из View для снятия с депозитного счета");
        if (bindingResult.hasErrors()) {
            log.info("Ошибки в модели при снятии с депозитного счета");
            return "bills/getFromDeposit";
        }

        billService.subtractFromBalance(model.getId(), model.getAmount());
        log.info("Сняли со счета  сумму: {}", model.getAmount());
        return "redirect:/bills";
    }
}
