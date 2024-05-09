package imd.ufrn.backend;

import java.util.Map;
import java.util.Optional;

public class BankService {
    private BankRepository bankRepository;

    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public boolean createAccount(int accountNumber, int accountType) {
        Account account;

        if (accountType == 1) {
            account = new Account(accountNumber, 0.0);
        } else if (accountType == 2) {
            account = new SavingsAccount(accountNumber, 0.0);
        } else {
            return false;
        }

        bankRepository.saveAccount(account);
        return true;
    }

    public Optional<Double> realizeDebit(int accountNumber, double value) {
        Account selectedAccount = bankRepository.getAccountByAccountNumber(accountNumber);
        double accountBalance = selectedAccount.getBalance();

        if (accountBalance < value) {
            return Optional.empty();
        }

        selectedAccount.setBalance(accountBalance - value);
        bankRepository.saveAccount(selectedAccount);
        return Optional.of(selectedAccount.getBalance());
    }

    public double realizeCredit(int accountNumber, double value) {
        Account selectedAccount = bankRepository.getAccountByAccountNumber(accountNumber);
        selectedAccount.setBalance(selectedAccount.getBalance() + value);
        bankRepository.saveAccount(selectedAccount);
        return selectedAccount.getBalance();
    }

    public Boolean realizeTransfer(int originAccountNumber, int destinationAccountNumber, double value) {
        Optional<Double> debit = realizeDebit(originAccountNumber, value);
        if (debit.isEmpty()) {
            return false;
        }

        realizeCredit(destinationAccountNumber, value);

        return true;
    }

    public double checkBalance(int accountNumber) {
        Account account = bankRepository.getAccountByAccountNumber(accountNumber);
        return account.getBalance();
    }

    public void payFees(double fee) {
        Map<Integer, Account> allAccounts = bankRepository.findAll();
        for (Map.Entry<Integer, Account> entry : allAccounts.entrySet()) {
            Account account = entry.getValue();
            if (account instanceof SavingsAccount) {
                double accountBalance = account.getBalance();
                account.setBalance(accountBalance + (accountBalance * fee / 100));
                bankRepository.saveAccount(account);
            }
        }
    }
}
