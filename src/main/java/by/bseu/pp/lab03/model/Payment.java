package by.bseu.pp.lab03.model;

import java.time.LocalDate;

/**
 * Represents a payment connected with an order.
 */
public class Payment {
    private final String paymentId;
    private final double amount;
    private final LocalDate date;
    private PaymentStatus status;

    public Payment(String paymentId, double amount, LocalDate date) {
        if (paymentId == null || paymentId.isBlank()) {
            throw new IllegalArgumentException("paymentId платежа не может быть null или пустым");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("amount платежа должна быть строго больше нуля");
        }
        if (date == null) {
            throw new IllegalArgumentException("date платежа не может быть null");
        }
        this.paymentId = paymentId;
        this.amount = amount;
        this.date = date;
        this.status = PaymentStatus.CREATED;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void markSuccessful() {
        if (status != PaymentStatus.CREATED) {
            throw new IllegalStateException("Платёж уже обработан, текущий статус: " + status);
        }
        this.status = PaymentStatus.SUCCESSFUL;
    }

    public void markFailed() {
        if (status != PaymentStatus.CREATED) {
            throw new IllegalStateException("Платёж уже обработан, текущий статус: " + status);
        }
        this.status = PaymentStatus.FAILED;
    }

    public boolean isSuccessful() {
        return status == PaymentStatus.SUCCESSFUL;
    }
}
