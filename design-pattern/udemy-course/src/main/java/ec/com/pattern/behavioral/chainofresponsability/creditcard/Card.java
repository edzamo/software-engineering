package ec.com.pattern.behavioral.chainofresponsability.creditcard;

public class Card implements ApproveLoanChain {

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
        Gold gold = new Gold();
        this.setNext(gold);

        Platinium platinium = new Platinium();
        gold.setNext(platinium);

        Black black = new Black();
        platinium.setNext(black);

        next.crediCardRequest(totalLoan);

    }

}
