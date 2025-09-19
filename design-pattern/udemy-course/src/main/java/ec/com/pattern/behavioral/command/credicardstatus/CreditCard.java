package ec.com.pattern.behavioral.command.credicardstatus;

public class CreditCard {
    
 

    public void sendPingNumber() {
       System.out.println("Sending ping number to the credit card");
    }

    public void  sendSMSToCustomerActive() {
        System.out.println("Sending SMS to customer to active the credit card");
    }

    public void activate() {
        System.out.println("Activating the credit card");
    }

    public void desactive() {
        System.out.println("Desactivating the credit card");
    }
    public void sendSMSToCustomerDesactive() {
        System.out.println("Sending SMS to customer to desactive the credit card");
    }   
}
