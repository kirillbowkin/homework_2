package com.company.homeworkloans.app.service;

import com.company.homeworkloans.entity.Client;
import com.company.homeworkloans.entity.Loan;
import com.company.homeworkloans.entity.LoanStatus;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class LoanService {

    @Autowired
    private DataManager dataManager;

    public void requestLoan(Client client, BigDecimal amount) {
        Loan loan = dataManager.create(Loan.class);
        loan.setClient(client);
        loan.setAmount(amount);
        loan.setRequestDate(LocalDate.now());
        loan.setStatus(LoanStatus.REQUESTED);
        dataManager.save(loan);
    }
}