public class Lab05 {
    public static void main(String[] args) {
        Account[] accounts = {new SavingsAccount("Riya",5000,1000),
            new CurrentAccount("Tej",2000,1000),new FixedDepositAccount("Aman",9000)};
        for (Account a : accounts) {
            System.out.println(a + " Rate: " + a.interestRate());
            if (a instanceof SavingsAccount s) {
                System.out.println("Savings withdrawal allowed: " + s.canWithdraw(4500));
            }
        }
    }
}
