package imd.ufrn.backend;

public class BankController {
     private BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    public void createAccount(int accountNumber) {
        bankService.createAccount(accountNumber);
    }
}
