package by.bseu.pp.lab03.model;

/**
 * Represents a product sold by the enterprise.
 */
public class Product {
    private final String code;
    private String name;
    private double price;

    public Product(String code, String name, double price) {
        if (code==null || code.isBlank()){
            throw new IllegalArgumentException("code  не может быть null или пустым");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name не может быть null или пустым");
        }
        if (price < 0) {
            throw new IllegalArgumentException("price не может быть отрицательной");
        }
        this.code = code;
        this.name = name;
        this.price = price;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void rename(String newName) {
        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("newName не может быть null или пустым");
        }
        this.name = newName;
    }

    public void changePrice(double newPrice) {
        if (newPrice < 0) {
            throw new IllegalArgumentException("newPrice не может быть отрицательной");
        }
        this.price = newPrice;
    }

    public double calculateAmount(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity должно быть строго больше нуля");
        }
        return price * quantity;
    }
}
