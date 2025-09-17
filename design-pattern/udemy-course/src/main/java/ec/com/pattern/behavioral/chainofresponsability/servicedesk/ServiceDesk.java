package ec.com.pattern.behavioral.chainofresponsability.servicedesk;

public class ServiceDesk  implements ApproveServiceDeskChain {
    
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
       BasicSupport basicSupport = new BasicSupport();
       this.setNext(basicSupport);
       
       ExpertTechnician expertTechnician = new ExpertTechnician();
       basicSupport.setNext(expertTechnician);
       
       SpecializedTechnical specializedTechnical = new SpecializedTechnical();
       expertTechnician.setNext(specializedTechnical);

       next.serviceDeskRequest(request);
       
    }   
    

    
}