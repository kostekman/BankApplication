package com.luxoft.bankapp.networkcommands;

import com.luxoft.bankapp.appinterface.Command;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.service.BankInfo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static com.luxoft.bankapp.networkservice.MessageSender.sendMessage;


/**
 * Created by Adam on 22.04.2016.
 */
public class GetBankStatisticsNetworkCommand extends AbstractNetworkCommand{
    public GetBankStatisticsNetworkCommand(ObjectInputStream in, ObjectOutputStream out) {
        super(in, out);
    }

    @Override
    public void execute() {
        sendMessage("BANKINFO", out);
        try {
            BankInfo report = ((BankInfo) in.readObject());
            System.out.println(report.getBankReport());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Command gets bank info from server");
    }
}
