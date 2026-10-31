package service;
import model.Account;
import java.util.*;
public class AccountStore {
    private HashMap<String,Account> accounts = new HashMap<>();
    private ArrayList<String> history = new ArrayList<>();
    public void add(Account account) { accounts.put(account.getAccountNumber(),account); }
    public Account get(String number) { return accounts.get(number); }
    public ArrayList<Account> getAll() { return new ArrayList<>(accounts.values()); }
    public void record(String line) { history.add(line); }
    public ArrayList<String> getHistory() { return new ArrayList<>(history); }
    public ArrayList<Account> sortedByBalance() {
        ArrayList<Account> list = getAll();
        list.sort(Comparator.comparingLong(Account::getBalance).reversed()); return list;
    }
}
