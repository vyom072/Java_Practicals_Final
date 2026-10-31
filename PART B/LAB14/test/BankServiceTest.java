package test;
import service.BankService;
import model.Account;
import exception.*;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;
public class BankServiceTest {
    @TempDir Path folder;
    Account open(BankService bank) throws BankException {
        return bank.openAccount("SAVINGS","Tej","tej@test.com","9876543210",
            "ABCDE1234F","SBIN0001234",1000);
    }
    @Test void depositIncreasesBalance() throws Exception {
        BankService bank = new BankService(folder);
        Account a = open(bank); bank.deposit(a.getAccountNumber(),500);
        assertEquals(1500,bank.balance(a.getAccountNumber()));
    }
    @Test void excessWithdrawalThrows() throws Exception {
        BankService bank = new BankService(folder);
        Account a = open(bank);
        assertThrows(InsufficientFundsException.class,
            () -> bank.withdraw(a.getAccountNumber(),5000));
        assertEquals(1000,bank.balance(a.getAccountNumber()));
    }
    @Test void concurrentDepositsRemainCorrect() throws Exception {
        BankService bank = new BankService(folder);
        Account a = open(bank); bank.batchDeposit(a.getAccountNumber(),100,10);
        assertEquals(2000,bank.balance(a.getAccountNumber()));
    }
    @Test void savedStateSurvivesRestart() throws Exception {
        BankService bank = new BankService(folder);
        Account a = open(bank); bank.deposit(a.getAccountNumber(),500); bank.save();
        BankService loaded = new BankService(folder);
        assertEquals(1500,loaded.balance(a.getAccountNumber()));
        Account next = open(loaded);
        assertNotEquals(a.getAccountNumber(),next.getAccountNumber());
    }
}
