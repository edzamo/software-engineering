package ec.com.pattern.behavioral.chainofresponsability.servicedesk;

public class BasicSupport implements ApproveServiceDeskChain {
    
    private ApproveServiceDeskChain next;
    
    @Override
    public void setNext(ApproveServiceDeskChain request) {
        next = request;
    }
    
    @Override
    public ApproveServiceDeskChain getNext() {
        return next;
    }
    
    @Override
    public void serviceDeskRequest(String request) {
        if (request.equalsIgnoreCase("Basic Support")) {
            System.out.println("Your request for Basic Support has been approved");
        } else {
            this.next.serviceDeskRequest(request);
        }
    }
    
}
