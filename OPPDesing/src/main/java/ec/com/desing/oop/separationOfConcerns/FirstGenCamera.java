package ec.com.desing.oop.separationOfConcerns;

public class FirstGenCamera implements Camera {

    @Override
    public void takePhoto() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void savePhoto() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void cameraFlash() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
