package imd.ufrn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

import imd.ufrn.presentation.BankTerminalPresentation;

@SpringBootApplication
public class App implements CommandLineRunner {
    @Autowired
    BankTerminalPresentation bankTerminalPresentation;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public RestClient restTemplate() {
        return RestClient.create();
    }

    @Override
    public void run(String... args) throws Exception {
        bankTerminalPresentation.Initialize();
    }
}