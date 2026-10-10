package by.bseu.pp.lab03.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an order and protects the allowed order state transitions.
 */
public class Order {
    private final Customer customer;
    private final List<OrderItem> items;
    private OrderStatus status;
    private Payment payment;

    public Order(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("customer не может быть null");
        }
        this.customer = customer;
        this.items = new ArrayList<>();
        this.status = OrderStatus.CREATED;
    }

    public Customer getCustomer() {
        return customer;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public int getItemsCount() {
        return items.size();
    }

    public Payment getPayment() {
        return payment;
    }

    public void addItem(Product product, int quantity) {
        if (status != OrderStatus.CREATED) {
            throw new IllegalStateException("Нельзя добавить позицию: статус заказа — " + status);
        }
        // Создаём OrderItem — он сам проверит product и quantity
        OrderItem item = new OrderItem(product, quantity);
        items.add(item);
    }

    public double total() {
        double sum = 0.0;
        for (OrderItem item : items) {
            sum += item.getAmount();
        }
        return sum;
    }

    public void confirm() {
        if (status != OrderStatus.CREATED) {
            throw new IllegalStateException("Подтвердить можно только CREATED-заказ");
        }
        if (items.isEmpty()) {
            throw new IllegalStateException("Нельзя подтвердить пустой заказ");
        }
        if (!customer.isActive()) {
            throw new IllegalStateException("customer неактивен");
        }
        if (total() > customer.getCreditLimit()) {
            throw new IllegalStateException("total  превышает кредитный лимит");
        }
        this.status = OrderStatus.CONFIRMED;
    }

    public void cancel() {
        if (status == OrderStatus.PAID) {
            throw new IllegalStateException("Оплаченный заказ отменить нельзя");
        }
        if (status == OrderStatus.CANCELLED) {
            throw new IllegalStateException("Заказ уже отменён");
        }
        this.status = OrderStatus.CANCELLED;
    }

    public void pay(Payment payment) {
        if (payment == null) {
            throw new IllegalArgumentException("Платёж не может быть null");
        }
        if (status != OrderStatus.CONFIRMED) {
            throw new IllegalStateException("Оплатить можно только CONFIRMED-заказ");
        }
        if (!payment.isSuccessful()) {
            throw new IllegalStateException("Платёж не является успешным");
        }
        if (payment.getAmount() != total()) {
            throw new IllegalArgumentException("Сумма платежа не совпадает с суммой заказа");
        }
        this.payment = payment;
        this.status = OrderStatus.PAID;
    }

    public boolean isPaid() {
        return status == OrderStatus.PAID;
    }
}
