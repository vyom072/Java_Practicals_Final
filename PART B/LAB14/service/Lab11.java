package service;
import model.*;
import util.*;
import java.nio.file.*;
public class Lab11 {
    public static void main(String[] args) throws Exception {
        Account[] accounts = {new SavingsAccount("Tej",5000,0),
            new CurrentAccount("Riya",2000,1000),new FixedDepositAccount("Aman",9000)};
        accounts[0].deposit(500); accounts[1].withdraw(200);
        Path data = Path.of("data/accounts.dat");
        StatePersister.save(accounts,data);
        Account[] loaded = StatePersister.load(data);
        for (Account a : loaded) { System.out.println(a); }
        Path logs = Path.of("data/logs");
        TransactionLog.append(logs.resolve("daily.log"),"DEPOSIT "
            + accounts[0].getAccountNumber() + " 500");
        TransactionLog.append(logs.resolve("daily.log"),"WITHDRAW "
            + accounts[1].getAccountNumber() + " 200");
        System.out.println(ReportGenerator.generate(logs,Path.of("data/report.txt")));
    }
}
