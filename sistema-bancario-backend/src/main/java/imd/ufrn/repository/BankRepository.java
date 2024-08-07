package imd.ufrn.repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import imd.ufrn.model.Account;

@Component
public class BankRepository {
    private Map<Integer, Account> accounts;

    public BankRepository() {
        this.accounts = new HashMap<>();
    }

    public void saveAccount(Account account) {
        accounts.put(account.getAccountNumber(), account);
    }

    public Account getAccountByAccountNumber(int accountNumber) {
        return accounts.get(accountNumber);
    }

    public Map<Integer, Account> findAll() {
        return Collections.unmodifiableMap(new HashMap<>(accounts));
    }

}
