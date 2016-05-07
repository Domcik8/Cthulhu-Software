package lt.vu.mif.labanoro_draugai.Atsiskaitymui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
@Stateful
public class Shop implements Serializable {

    private List<Product> products = new ArrayList<Product>();
    private List<Product> selectedProducts = new ArrayList<Product>();

    @PostConstruct
    public void init() {
        products.add(new Product("Laptopas", "Geras laptopas", 4000));
        products.add(new Product("Desktopas", "Šiaip sau desktopas", 3000));
        products.add(new Product("Serveris", "Kažkoks serveris", 10000));
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Product> getSelectedProducts() {
        return selectedProducts;
    }

    public String selectProducts() {
        selectedProducts.clear();

        for (Product product : products) {
            if (product.getKiekPirksim() > 0) {
                selectedProducts.add(product);
            }
        }

        return "cart";
    }

    public String deleteProduct(Product product) {
        selectedProducts.remove(product);
        return "cart";
    }

}