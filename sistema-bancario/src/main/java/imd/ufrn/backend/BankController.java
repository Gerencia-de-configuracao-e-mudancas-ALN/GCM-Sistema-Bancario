package imd.ufrn.backend;

public class BankController {
    private BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    public void createAccount(int accountNumber) {
        bankService.createAccount(accountNumber);
    }

    public double debit(int accountNumber, double value) {
        return bankService.realizeDebit(accountNumber, value);
    }

    public boolean transfer(int originAccountNumber, int destinationAccountNumber, double value) {
        return bankService.realizeTransfer(originAccountNumber, destinationAccountNumber, value);
    }

    public double checkBalance(int accountNumber) {
        return bankService.checkBalance(accountNumber);
    }
}
