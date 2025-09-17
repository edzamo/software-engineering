package ec.com.pattern.behavioral.chainofresponsability.creditcard;

public class Gold implements ApproveLoanChain {

    private ApproveLoanChain next;

    @Override
    public void setNext(ApproveLoanChain loan) {
        next = loan;
    }

    @Override
    public ApproveLoanChain getNext() {
        return next;
    }

    @Override
    public void crediCardRequest(int totalLoan) {
        if (totalLoan <= 10000) {
            System.out.println("Your loan request for Gold Card has been approved");
        } else {
            next.crediCardRequest(totalLoan);
            System.out.println("Your loan request for Gold Card has been declined, please contact with our support");
        }
    }   
    
}
