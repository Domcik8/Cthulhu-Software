package lt.vu.mif.labanoro_draugai.Atsiskaitymui;

public class Product {

    private String name;
    private int price;
    private String description;
    private int kiekPirksim;

    public Product(String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public int getKiekPirksim() {
        return kiekPirksim;
    }

    public void setKiekPirksim(int kiekPirksim) {
        this.kiekPirksim = kiekPirksim;
    }

}