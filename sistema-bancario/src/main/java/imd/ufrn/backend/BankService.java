package imd.ufrn.backend;

import java.util.Map;
import java.util.Optional;

public class BankService {
    private BankRepository bankRepository;

    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public boolean createAccount(int accountNumber, int accountType, double balance) {
        Account account;

        if (accountType == 1) {
            account = new Account(accountNumber, balance);
        } else if (accountType == 2) {
            account = new SavingsAccount(accountNumber, balance);
        } else if (accountType == 3) {
            account = new BonusAccount(accountNumber, balance, 10);
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

    public double realizeCredit(int accountNumber, double value, boolean isTransfer) {
        Account selectedAccount = bankRepository.getAccountByAccountNumber(accountNumber);
        selectedAccount.setBalance(selectedAccount.getBalance() + value);
        if (selectedAccount instanceof BonusAccount & !isTransfer) {
            int actualPunctuation = ((BonusAccount) selectedAccount).getPunctuation();
            ((BonusAccount) selectedAccount).setPunctuation(actualPunctuation + (int) (value / 100));
        }
        bankRepository.saveAccount(selectedAccount);
        return selectedAccount.getBalance();
    }

    public boolean realizeTransfer(int originAccountNumber, int destinationAccountNumber, double value) {
        realizeDebit(originAccountNumber, value);
        realizeCredit(destinationAccountNumber, value, true);
        Account destinationAccount = bankRepository.getAccountByAccountNumber(destinationAccountNumber);

        if (destinationAccount instanceof BonusAccount) {
            int punctuation = ((BonusAccount) destinationAccount).getPunctuation();
            ((BonusAccount) destinationAccount).setPunctuation(punctuation + (int) (value / 150));
        }
        bankRepository.saveAccount(destinationAccount);
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
