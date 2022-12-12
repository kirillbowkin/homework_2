package com.company.homeworkloans.screen.requestloan;

import com.company.homeworkloans.app.service.LoanService;
import com.company.homeworkloans.entity.Client;
import com.google.common.base.Preconditions;
import io.jmix.core.DataManager;
import io.jmix.ui.Notifications;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.*;
import io.jmix.ui.component.validation.DecimalMinValidator;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.model.ScreenData;
import io.jmix.ui.screen.*;
import liquibase.pro.packaged.B;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@UiController("RequestLoan")
@UiDescriptor("request-loan.xml")
public class RequestLoan extends Screen {
    private static final Logger log = LoggerFactory.getLogger(RequestLoan.class);

    private Client client;
    @Autowired
    private EntityComboBox<Client> clientField;
    @Autowired
    private TextField amountField;
    @Autowired
    private ScreenValidation screenValidation;
    @Autowired
    private LoanService loanService;
    @Autowired
    private Notifications notifications;
    @Autowired
    private DataManager dataManager;
    @Autowired
    private CollectionLoader<Client> clientsDl;

    @Subscribe("windowClose")
    public void onWindowClose(Action.ActionPerformedEvent event) {
        close(StandardOutcome.CLOSE);
    }

    @Subscribe("windowCommitAndClose")
    public void onWindowCommitAndClose(Action.ActionPerformedEvent event) {
        ValidationErrors errors = screenValidation.validateUiComponents(Arrays.asList(clientField, amountField));
        if (!errors.isEmpty()) {
            screenValidation.showValidationErrors(this, errors);
            return;
        }

        loanService.requestLoan(clientField.getValue(), (BigDecimal) amountField.getValue());
        close(WINDOW_CLOSE_ACTION);
    }

    public void setClient(Client client) {
        this.client = client;

        clientField.setValue(client);
    }
}