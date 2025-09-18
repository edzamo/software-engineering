package ec.com.pattern.behavioral.command.credicardstatus;

public class CreditDesactivateCommand implements Command {

    private CreditCard creditCard;

    public CreditDesactivateCommand(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    @Override
    public void execute() {
        creditCard.sendSMSToCustomerDesactive();
        creditCard.desactive();
    }
    
}
