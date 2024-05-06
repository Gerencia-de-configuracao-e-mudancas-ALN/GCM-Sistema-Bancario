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

    public double realizeDebit(int accountNumber, double value) {
        Account selectedAccount = bankRepository.getAccountByAccountNumber(accountNumber);
        selectedAccount.setBalance(selectedAccount.getBalance() - value);
        bankRepository.saveAccount(selectedAccount);
        return selectedAccount.getBalance();
    }

    public boolean transfer(int originAccountNumber, int destinationAccountNumber, double value){
        return bankRepository.realizeTransfer(originAccountNumber, destinationAccountNumber, value);
    }
}
