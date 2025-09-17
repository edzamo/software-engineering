package ec.com.pattern.behavioral.chainofresponsability.creditcard;

public interface ApproveLoanChain {
    
    void setNext(ApproveLoanChain loan);
    ApproveLoanChain getNext();
    void crediCardRequest(int totalLoan);
}
