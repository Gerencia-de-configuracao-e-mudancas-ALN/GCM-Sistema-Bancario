package imd.ufrn.backend;

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
        } else if (accountType == 3) {
            account = new BonusAccount(accountNumber, 0.0, 10);
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
            ((BonusAccount) destinationAccount).setPunctuation(punctuation + (int) (value / 200));
        }
        bankRepository.saveAccount(destinationAccount);
        return true;
    }

    public double checkBalance(int accountNumber) {
        Account account = bankRepository.getAccountByAccountNumber(accountNumber);
        return account.getBalance();
    }
}
