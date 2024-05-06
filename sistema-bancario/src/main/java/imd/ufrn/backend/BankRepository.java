package imd.ufrn.backend;

import java.util.HashMap;
import java.util.Map;

public class BankRepository {
    private Map<Integer, Account> accounts;

    public BankRepository() {
        this.accounts = new HashMap<>();
    }

    public void saveAccount(Account account) {
        accounts.put(account.getAccountNumber(), account);
    }

    public double realizeDebit(int accountNumber, double value) {
        Account selectedAccount = accounts.get(accountNumber);
        selectedAccount.setBalance(selectedAccount.getBalance() - value);
        return selectedAccount.getBalance();
    }

    public boolean realizeTransfer(int originAccountNumber, int destinationAccountNumber, double value){
        Account destinationAccount = accounts.get(destinationAccountNumber);
        realizeDebit(originAccountNumber, value);
        destinationAccount.setBalance(destinationAccount.getBalance() + value);
        return true;
    }
}
