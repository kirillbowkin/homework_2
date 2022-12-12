package com.company.homeworkloans.screen.client;

import com.company.homeworkloans.screen.requestloan.RequestLoan;
import io.jmix.core.common.util.Preconditions;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.Screens;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.screen.*;
import com.company.homeworkloans.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("Client.browse")
@UiDescriptor("client-browse.xml")
@LookupComponent("clientsTable")
public class ClientBrowse extends StandardLookup<Client> {
    @Autowired
    private ScreenBuilders screenBuilders;
    @Autowired
    private GroupTable<Client> clientsTable;

    @Subscribe("clientsTable.requestLoan")
    public void onClientsTableRequestLoan(Action.ActionPerformedEvent event) {
        Client selectedClient = clientsTable.getSingleSelected();

        RequestLoan requestLoanScreen = screenBuilders.screen(this)
                .withScreenClass(RequestLoan.class)
                .build();

        requestLoanScreen.setClient(selectedClient);
        requestLoanScreen.show();
    }
}