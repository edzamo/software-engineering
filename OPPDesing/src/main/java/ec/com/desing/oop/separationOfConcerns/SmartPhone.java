package ec.com.desing.oop.separationOfConcerns;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SmartPhone {
    private Camera myCamera;
    private Phone myPhone;

    public void useCamera() {
        this.myCamera.takePhoto();
    }

    public void usePhone() {
        this.myPhone.makePhoneCall();
    }
}
