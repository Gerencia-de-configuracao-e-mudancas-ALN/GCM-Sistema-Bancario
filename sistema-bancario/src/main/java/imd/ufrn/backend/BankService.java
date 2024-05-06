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

    public double debit(int accountNumber, double value) {
        return bankRepository.realizeDebit(accountNumber, value);
    }

    public boolean transfer(int originAccountNumber, int destinationAccountNumber, double value){
        return bankRepository.realizeTransfer(originAccountNumber, destinationAccountNumber, value);
    }
}
