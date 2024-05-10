package imd.ufrn.frontend;

import java.util.Optional;
import java.util.Scanner;

import imd.ufrn.backend.BankController;

public class BankTerminalPresentation {
    Scanner scanner = new Scanner(System.in);
    BankController bankController;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String GREEN_BACKGROUND = "\u001B[32m";
    public static final String RED_BACKGROUND = "\u001B[31m";

    public BankTerminalPresentation(BankController bankController) {
        this.bankController = bankController;
    }

    public void Initialize() {
        mainLoop();
    }

    private void mainLoop() {
        while (true) {

            System.out.println("");
            System.out.println("-----------------------------------");
            System.out.println("");

            int opt = showOptionsAndReturnOption();

            System.out.println("");
            System.out.println("");

            switch (opt) {
                case 0:
                    exitChosen();
                case 1:
                    createAccountChosen();
                    break;
                case 2:
                    realizeDebit();
                    break;
                case 3:
                    realizeCredit();
                    break;
                case 4:
                    realizeTransfer();
                    break;
                case 5:
                    checkBalance();
                    break;
                default:
                    wrongOption();
                    break;
            }

            System.out.println("");
            System.out.println("");
            System.out.println("");
            System.out.println("Digite enter para prosseguir para a próxima operação...");
            scanner.nextLine();
            scanner.nextLine();
        }
    }

    private void checkBalance() {
        System.out.println("Digite o número da conta que deseja consultar o saldo: ");
        int accountNumber = scanner.nextInt();
        double balance = bankController.checkBalance(accountNumber);
        System.out.printf("saldo atual da conta número " + accountNumber + ": %.2f: \n", balance);
    }

    private void wrongOption() {
        System.out.println(RED_BACKGROUND + "Opção inválida. Tente novamente." + ANSI_RESET);
    }

    private void exitChosen() {
        System.out.println("Fechando o programa...");
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
        }
        System.out.println(GREEN_BACKGROUND + "Programa fechado com sucesso!" + ANSI_RESET);
        scanner.close();
        System.exit(0);
    }

    public int showOptionsAndReturnOption() {
        System.out.println("Escolha uma opção: ");
        System.out.println("    0- Sair");
        System.out.println("    1- Criar conta");
        System.out.println("    2- Realizar débito");
        System.out.println("    3- Realizar crédito");
        System.out.println("    4- Realizar transferência");
        System.out.println("    5- Consultar saldo");

        return scanner.nextInt();
    }

    public void createAccountChosen() {
        System.out.println("Para criar uma conta digite o número da conta: ");
        int accountNumber = scanner.nextInt();
        System.out.println("Criando conta de número: " + accountNumber);
        try {
            Thread.sleep(2000);
        } catch (Exception e) {

        }
        bankController.createAccount(accountNumber);
        System.out.println(GREEN_BACKGROUND + "Conta criada com sucesso!" + ANSI_RESET);
    }

    public void realizeDebit() {
        System.out.println("Digite o número da conta que deseja realizar o débito: ");
        int accountNumber = scanner.nextInt();
        System.out.printf("Digite quanto deseja debitar da conta %d: ", accountNumber);
        double valueToDebit = scanner.nextDouble();

        Optional<Double> newBalance = bankController.debit(accountNumber, valueToDebit);
        if (newBalance.isEmpty()) {
            System.out.printf("Não foi possível realizar a operação pois a conta não possui saldo suficiente");
        } else {
            System.out.printf("Valor debitado com sucesso, saldo atual: %.2f: ", newBalance.get());
        }
    }

    public void realizeCredit() {
        System.out.println("Digite o número da conta em que deseja realizar o crédito: ");
        int accountNumber = scanner.nextInt();
        System.out.printf("Digite quanto deseja creditar na conta %d: ", accountNumber);
        double valueToCredit = scanner.nextDouble();
        double newBalance = bankController.credit(accountNumber, valueToCredit);
        System.out.printf("Valor creditado com sucesso, saldo atual: %.2f: ", newBalance);
    }

    public void realizeTransfer() {
        System.out.println("Digite o número da conta de origem: ");
        int originAccountNumber = scanner.nextInt();
        System.out.println("Digite o número da conta de destino: ");
        int destinationAccountNumber = scanner.nextInt();
        System.out.println("Digite o valor a ser transferido: ");
        double valueToTransfer = scanner.nextDouble();
        boolean isSuccess = bankController.transfer(originAccountNumber, destinationAccountNumber, valueToTransfer);
        if (isSuccess) {
            System.out.println("Valor transferido com sucesso!");
        } else {
            System.out.println("Falha ao transferir. A conta de origem não possui saldo o suficiente.");
        }
    }
}
