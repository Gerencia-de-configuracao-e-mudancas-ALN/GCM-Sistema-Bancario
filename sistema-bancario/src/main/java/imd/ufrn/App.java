package imd.ufrn;

import imd.ufrn.backend.BankController;
import imd.ufrn.backend.BankRepository;
import imd.ufrn.backend.BankService;
import imd.ufrn.frontend.BankTerminalPresentation;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        BankRepository bankRepository = new BankRepository();
        BankService bankService = new BankService(bankRepository);
        BankController bankController = new BankController(bankService);

        BankTerminalPresentation bankTerminalPresentation = new BankTerminalPresentation(bankController);
        bankTerminalPresentation.Initialize();
    }
}
