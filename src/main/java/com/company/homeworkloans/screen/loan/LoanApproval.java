package com.company.homeworkloans.screen.loan;

import com.company.homeworkloans.entity.Loan;
import io.jmix.core.common.util.Preconditions;
import io.jmix.ui.component.Component;
import io.jmix.ui.component.Table;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.Period;

@UiController("Loan.approval")
@UiDescriptor("loan-approval.xml")
@LookupComponent("loansTable")
public class LoanApproval extends StandardLookup<Loan> {
    @Autowired
    private CollectionLoader<Loan> previousLoansDl;

    @Install(to = "loansTable.age", subject = "columnGenerator")
    private Component loansTableAgeColumnGenerator(Loan loan) {
        return new Table.PlainTextCell(
                String.valueOf(Period.between(loan.getClient().getBirthDate(), LocalDate.now())
                        .getYears())
        );
    }

    @Subscribe(id = "loansDc", target = Target.DATA_CONTAINER)
    public void onLoansDcItemChange(InstanceContainer.ItemChangeEvent<Loan> event) {
        Preconditions.checkNotNullArgument(event.getItem().getClient());

        previousLoansDl.setParameter("client", event.getItem().getClient());
        previousLoansDl.setParameter("id", event.getItem().getId());
        previousLoansDl.load();
    }

}