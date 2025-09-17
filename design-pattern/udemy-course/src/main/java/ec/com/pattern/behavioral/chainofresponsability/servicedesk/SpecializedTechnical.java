package ec.com.pattern.behavioral.chainofresponsability.servicedesk;

public class SpecializedTechnical implements ApproveServiceDeskChain {
    
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
        if (request.equalsIgnoreCase("Specialized Technical")) {
            System.out.println("Your request for Specialized Technical has been approved");
        } else {
            this.next.serviceDeskRequest(request);
        }
    }
    
}
