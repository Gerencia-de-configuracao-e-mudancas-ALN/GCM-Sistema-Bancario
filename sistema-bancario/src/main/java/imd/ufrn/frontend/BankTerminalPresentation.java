package imd.ufrn.frontend;

import java.util.Scanner;

import imd.ufrn.backend.BankController;

public class BankTerminalPresentation {
    Scanner scanner = new Scanner(System.in);
    BankController bankController;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String GREEN_BACKGROUND = "\u001B[32m";
    public static final String RED_BACKGROUND = "\u001B[31m";

    public BankTerminalPresentation(BankController bankController){
        this.bankController = bankController;
    }

    public void Initialize(){
        mainLoop();  
    }
    
    private void mainLoop(){
        while(true){

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

    public int showOptionsAndReturnOption(){
        System.out.println("Escolha uma opção: ");
        System.out.println("    0- Sair");
        System.out.println("    1- Criar conta");

        return scanner.nextInt();
    }

    public void createAccountChosen(){
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
}
