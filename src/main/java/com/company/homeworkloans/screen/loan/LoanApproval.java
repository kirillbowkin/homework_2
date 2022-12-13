package com.company.homeworkloans.screen.loan;

import com.company.homeworkloans.entity.Loan;
import io.jmix.ui.component.Component;
import io.jmix.ui.component.Table;
import io.jmix.ui.screen.*;

import java.time.LocalDate;
import java.time.Period;

@UiController("Loan.approval")
@UiDescriptor("loan-approval.xml")
@LookupComponent("loansTable")
public class LoanApproval extends StandardLookup<Loan> {
    @Install(to = "loansTable.age", subject = "columnGenerator")
    private Component loansTableAgeColumnGenerator(Loan loan) {
        return new Table.PlainTextCell(
                String.valueOf(Period.between(loan.getClient().getBirthDate(), LocalDate.now())
                        .getYears())
        );
    }
}