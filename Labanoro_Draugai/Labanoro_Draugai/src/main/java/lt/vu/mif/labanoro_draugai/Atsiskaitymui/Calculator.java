package lt.vu.mif.labanoro_draugai.Atsiskaitymui;

import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
//@RequestScoped
@Stateful
//@Stateless
public class Calculator implements Serializable {
    // Read/write property "number":
    private int number = 5;

    public int getNumber() { return number; }

    public void setNumber(int number) { this.number = number; }

    // Read-only property "result":
    private Integer result = null;

    public Integer getResult() { return result; }

    // Method to square a number
    public void square() { result = number * number; }

    // Method to navigate to the second page
    public String bye() { return "enough"; }

    @PostConstruct
    public void init() {
      System.out.println(toString() + " constructed.");
    }
}