package service;
import model.Account;
public interface WithdrawRule { boolean allow(Account account, long amount); }
