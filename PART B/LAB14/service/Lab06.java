package service;
import model.*;
import static util.Validator.isValidMobile;
public class Lab06 {
    public static void main(String[] args) {
        Account a = new SavingsAccount("Tej",5000,1000);
        WithdrawRule first = new WithdrawRule() {
            public boolean allow(Account a, long amount) { return a.canWithdraw(amount); }
        };
        WithdrawRule second = (account, amount) -> account.canWithdraw(amount);
        System.out.println(first.allow(a,500));
        System.out.println(second.allow(a,500));
        System.out.println("Yearly interest: " + a.yearlyInterest());
        System.out.println("Premium: " + (a instanceof Premium));
        System.out.println(isValidMobile("9876543210"));
    }
}
