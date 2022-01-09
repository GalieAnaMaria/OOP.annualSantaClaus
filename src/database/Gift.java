package database;

/**
 * Class for keeping information about the
 * gifts from santa's list or owned by the children
 */
public class Gift {
    private String productName;
    private Double price;
    private String category;

    public Gift() {

    }

    public final String getProductName() {
        return productName;
    }

    public final void setProductName(final String productName) {
        this.productName = productName;
    }

    public final Double getPrice() {
        return price;
    }

    public final void setPrice(final Double price) {
        this.price = price;
    }

    public final String getCategory() {
        return category;
    }

    public final void setCategory(final String category) {
        this.category = category;
    }
}
