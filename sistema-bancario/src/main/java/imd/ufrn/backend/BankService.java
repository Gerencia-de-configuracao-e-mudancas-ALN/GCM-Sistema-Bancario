package imd.ufrn.backend;

import java.util.Optional;

public class BankService {
    private BankRepository bankRepository;

    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public void createAccount(int accountNumber) {
        Account account = new Account(accountNumber, 0.0);
        bankRepository.saveAccount(account);
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
}
