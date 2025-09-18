package ec.com.pattern.behavioral.command.credicardstatus;

public class CreditActiveCommand implements Command {

    private CreditCard creditCard;

    public CreditActiveCommand(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    @Override
    public void execute() {
        creditCard.sendSMSToCustomerActive();
        creditCard.activate();
    }
    
}
