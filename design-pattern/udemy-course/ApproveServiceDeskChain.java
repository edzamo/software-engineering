package ec.com.pattern.behavioral.chainofresponsability.servicedesk;

/**
 * Handler interface declares a method for building the chain of handlers. It
 * also declares a method for executing a request.
 */
public interface ApproveServiceDeskChain {

    void setNext(ApproveServiceDeskChain next);

    ApproveServiceDeskChain getNext();

    void serviceDeskRequest(String request);
}