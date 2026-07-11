package behavioral.iterator.spring;

public class SpringProduct {
    private final String name;
    private final double price;
    private final String category;
    private final boolean discounted;

    public SpringProduct(String name, double price, String category, boolean discounted) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.discounted = discounted;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public boolean isDiscounted() {
        return discounted;
    }
}
