package imd.ufrn.backend;

import java.util.Optional;

public class BankController {
    private BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    public void createAccount(int accountNumber, double balance) {
        bankService.createAccount(accountNumber, balance);
    }

    public Optional<Double> debit(int accountNumber, double value) {
        return bankService.realizeDebit(accountNumber, value);
    }

    public double credit(int accountNumber, double value) {
        return bankService.realizeCredit(accountNumber, value);
    }

    public boolean transfer(int originAccountNumber, int destinationAccountNumber, double value) {
        return bankService.realizeTransfer(originAccountNumber, destinationAccountNumber, value);
    }

    public double checkBalance(int accountNumber) {
        return bankService.checkBalance(accountNumber);
    }
}
