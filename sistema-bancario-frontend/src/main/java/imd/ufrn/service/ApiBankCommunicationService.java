package imd.ufrn.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import imd.ufrn.model.api.AccountRequest;
import imd.ufrn.model.api.AccountResponse;
import imd.ufrn.model.api.TransferRequest;

@Component
public class ApiBankCommunicationService {

    @Autowired
    @Lazy
    private RestClient restClient;
    private final String baseUrl = "http://localhost:8080/banco";

    public boolean createAccount(int accountNumber, int accountType, double balance) {
        AccountRequest accountRequest = new AccountRequest(accountNumber, accountType, balance);

        var response = restClient.post()
                .uri(baseUrl + "/conta")
                .contentType(MediaType.APPLICATION_JSON)
                .body(accountRequest)
                .retrieve()
                .body(Boolean.class);

        return response;
    }

    public AccountResponse getAccountByNumber(int accountNumber) {
        var response = restClient.get()
                .uri(baseUrl + "/conta/{accountNumber}", accountNumber)
                .retrieve()
                .body(AccountResponse.class);

        return response;
    }

    public double checkBalance(int accountNumber) {
        var response = restClient.get()
                .uri(baseUrl + "/conta/saldo/{accountNumber}", accountNumber)
                .retrieve()
                .body(Double.class);

        return response;
    }

    public double credit(int accountNumber, double value) {
        var response = restClient.put()
                .uri(baseUrl + "/conta/credito/{accountNumber}", accountNumber)
                .contentType(MediaType.APPLICATION_JSON)
                .body(value)
                .retrieve()
                .body(Double.class);

        return response;
    }

    public Optional<Double> debit(int accountNumber, double value) {
        var response = restClient.put()
                .uri(baseUrl + "/conta/debito/{accountNumber}", accountNumber)
                .contentType(MediaType.APPLICATION_JSON)
                .body(value)
                .retrieve()
                .body(Double.class);

        return response == null ? Optional.empty() : Optional.of(response);
    }

    public boolean transfer(int originAccountNumber, int destinationAccountNumber, double value) {
        TransferRequest transferRequest = new TransferRequest(originAccountNumber, destinationAccountNumber, value);

        var response = restClient.put()
                .uri(baseUrl + "/conta/transferencia")
                .contentType(MediaType.APPLICATION_JSON)
                .body(transferRequest)
                .retrieve()
                .body(Boolean.class);

        return response;
    }

    public void payFees(double fee) {
        restClient.put()
                .uri(baseUrl + "/conta/rendimento")
                .contentType(MediaType.APPLICATION_JSON)
                .body(fee)
                .retrieve();
    }

}
