package ec.com.pattern.behavioral.chainofresponsability.servicedesk;

public class ExpertTechnician implements ApproveServiceDeskChain    {

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
        if (request.equalsIgnoreCase("Expert Technician")) {
            System.out.println("Your request for Expert Technician has been approved");
        } else {
            if (next != null) {
                next.serviceDeskRequest(request);
            } else {
                System.out.println("Your request could not be handled. Please contact our main support line.");
            }
        }
    }
    
}
