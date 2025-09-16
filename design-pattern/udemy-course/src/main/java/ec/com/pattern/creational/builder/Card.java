package ec.com.pattern.creational.builder;

public class Card {
     
    private final  String cardNumber;
    private final String cardHolder;
    private final String expirationDate;
    private final String cvv;

private Card(Builder builder) {
        this.cardNumber = builder.cardNumber;
        this.cardHolder = builder.cardHolder;
        this.expirationDate = builder.expirationDate;
        this.cvv = builder.cvv;
    }   


    public static class Builder {
        private  String cardNumber;
        private String cardHolder;
        private String expirationDate;
        private String cvv;

        public Builder setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
            return this;
        }

        public Builder setCardHolder(String cardHolder) {
            this.cardHolder = cardHolder;
            return this;
        }

        public Builder setExpirationDate(String expirationDate) {
            this.expirationDate = expirationDate;
            return this;
        }

        public Builder setCvv(String cvv) {
            this.cvv = cvv;
            return this;
        }

        public Card build() {
            return new Card(this);
        }
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardNumber='" + cardNumber + '\'' +
                ", cardHolder='" + cardHolder + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                ", cvv='" + cvv + '\'' +
                '}';
    }
}
