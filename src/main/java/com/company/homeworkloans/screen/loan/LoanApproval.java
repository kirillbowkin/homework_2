package com.company.homeworkloans.screen.loan;

import com.company.homeworkloans.app.service.LoanService;
import com.company.homeworkloans.entity.Client;
import com.company.homeworkloans.entity.Loan;
import io.jmix.core.common.util.Preconditions;
import io.jmix.ui.Notifications;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.Component;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.component.Table;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

@UiController("Loan.approval")
@UiDescriptor("loan-approval.xml")
@LookupComponent("loansTable")
public class LoanApproval extends StandardLookup<Loan> {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(LoanApproval.class);
    @Autowired
    private CollectionLoader<Loan> previousLoansDl;
    @Autowired
    private GroupTable<Loan> loansTable;
    @Autowired
    private LoanService loanService;
    @Autowired
    private Notifications notifications;
    @Autowired
    private CollectionContainer<Loan> loansDc;

    @Install(to = "loansTable.age", subject = "columnGenerator")
    private Component loansTableAgeColumnGenerator(Loan loan) {
        return new Table.PlainTextCell(
                String.valueOf(Period.between(loan.getClient().getBirthDate(), LocalDate.now())
                        .getYears())
        );
    }

    @Subscribe(id = "loansDc", target = Target.DATA_CONTAINER)
    public void onLoansDcItemChange(InstanceContainer.ItemChangeEvent<Loan> event) {
        if(event.getItem() == null) {
            return;
        }

        previousLoansDl.setParameter("client", event.getItem().getClient());
        previousLoansDl.setParameter("id", event.getItem().getId());
        previousLoansDl.load();


    }

    @Subscribe("loansTable.approve")
    public void onLoansTableApprove(Action.ActionPerformedEvent event) {
        Preconditions.checkNotNullArgument(loansTable.getSingleSelected());
        Loan selectedLoan = loansTable.getSingleSelected();
        loanService.acceptLoan(selectedLoan);
        loansDc.getMutableItems().remove(selectedLoan);
        notifications.create()
                .withCaption("Approved")
                .withType(Notifications.NotificationType.TRAY)
                .show();
    }

    @Subscribe("loansTable.reject")
    public void onLoansTableReject(Action.ActionPerformedEvent event) {
        Preconditions.checkNotNullArgument(loansTable.getSingleSelected());
        Loan selectedLoan = loansTable.getSingleSelected();
        loanService.rejectLoan(selectedLoan);
        loansDc.getMutableItems().remove(selectedLoan);
        notifications.create()
                .withCaption("Rejected")
                .withType(Notifications.NotificationType.TRAY)
                .show();
    }


}