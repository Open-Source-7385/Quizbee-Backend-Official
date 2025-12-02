package pe.edu.upc.quizbee.subscriptions.domain.model.valueobjects;

public record PaymentMethod(
    String type,
    String lastFourDigits,
    String expiryDate
) {
    public PaymentMethod {
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("Payment type cannot be null or empty");
        }
        if (lastFourDigits == null || !lastFourDigits.matches("\\d{4}")) {
            throw new IllegalArgumentException("Last four digits must be exactly 4 digits");
        }
        if (expiryDate == null || !expiryDate.matches("\\d{2}/\\d{2}")) {
            throw new IllegalArgumentException("Expiry date must be in format MM/YY");
        }
    }

    public String getMaskedNumber() {
        return "**** **** **** " + lastFourDigits;
    }
}