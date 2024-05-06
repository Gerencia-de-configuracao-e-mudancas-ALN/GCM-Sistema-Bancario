package imd.ufrn.backend;

public class BankService {
    private BankRepository bankRepository;

    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public void createAccount(int accountNumber) {
        Account account = new Account(accountNumber, 0.0);
        bankRepository.saveAccount(account);
    }
}
