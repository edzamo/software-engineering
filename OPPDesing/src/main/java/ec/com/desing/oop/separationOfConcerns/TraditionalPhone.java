package ec.com.desing.oop.separationOfConcerns;

public class TraditionalPhone implements Phone {

    @Override
    public void makePhoneCall() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void encryptOutgoingSound() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void decryptIncomingSound() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
