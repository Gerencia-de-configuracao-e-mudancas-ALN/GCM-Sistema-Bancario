package imd.ufrn.controller;

import java.util.Optional;

// import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import imd.ufrn.model.Account;
import imd.ufrn.model.BonusAccount;
import imd.ufrn.model.SavingsAccount;
import imd.ufrn.model.api.AccountRequest;
import imd.ufrn.model.api.AccountResponse;
import imd.ufrn.model.api.TransferRequest;
import imd.ufrn.service.BankService;

@RestController
@RequestMapping("/banco")
@CrossOrigin("*")
public class BankController {
    @Autowired
    private BankService bankService;

    @PostMapping("/conta")
    public boolean createAccount(@RequestBody AccountRequest accountRequest) {
        return bankService.createAccount(accountRequest.getAccountNumber(), accountRequest.getAccountType(),
                accountRequest.getBalance());
    }

    @GetMapping("/conta/{accountNumber}")
    public AccountResponse getAccountByNumber(@PathVariable Integer accountNumber) {
        var accountReponse = new AccountResponse();
        var account = bankService.getAccountByNumber(accountNumber);

        accountReponse.setAccountNumber(account.getAccountNumber());
        accountReponse.setBalance(account.getBalance());
        if (account instanceof SavingsAccount) {
            accountReponse.setAccountType(2);
        } else if (account instanceof BonusAccount accountBonus) {
            accountReponse.setAccountType(3);
            accountReponse.setPunctuation(accountBonus.getPunctuation());
        } else if (account instanceof Account) {
            accountReponse.setAccountType(1);
        }

        return accountReponse;
    }

    @GetMapping("/conta/saldo/{accountNumber}")
    public Double checkBalance(@PathVariable Integer accountNumber) {
        return bankService.checkBalance(accountNumber);
    }

    @PutMapping("/conta/credito/{accountNumber}")
    public double credit(@PathVariable Integer accountNumber, @RequestBody Double value) {
        return bankService.realizeCredit(accountNumber, value, false);
    }

    @PutMapping("/conta/debito/{accountNumber}")
    public Optional<Double> debit(@PathVariable Integer accountNumber, @RequestBody Double value) {
        return bankService.realizeDebit(accountNumber, value);
    }

    @PutMapping("/conta/transferencia")
    public boolean transfer(@RequestBody TransferRequest transferRequest) {
        return bankService.realizeTransfer(transferRequest.getFrom(), transferRequest.getTo(),
                transferRequest.getAmount());
    }

    @PutMapping("/conta/rendimento")
    public void payFees(@RequestBody Double fee) {
        bankService.payFees(fee);
    }

}
