package ec.com.pattern.behavioral.chainofresponsability.servicedesk;

public interface ApproveServiceDeskChain {
    
    void setNext(ApproveServiceDeskChain request);
    ApproveServiceDeskChain getNext();
    void serviceDeskRequest(String request);

}
