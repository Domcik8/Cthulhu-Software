package lt.vu.mif.labanoro_draugai.Atsiskaitymui;

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
//@RequestScoped 
@SessionScoped
@Stateful
//@Stateless
public class PirmasKomponentas {
    
    @Inject
    private AntrasKomponentas Antras1;
    
    private AntrasKomponentas Antras2 = new AntrasKomponentas();

    public String sakykLabas() {
      return "Labas " + new Date() + " " + toString();
    }
    
    public String TestMethods(){
        return "Antras Injest: " + Antras1.getClass().getName() + ", antras new: " + Antras2.getClass().getName();
    }
    
    @PostConstruct
    public void init() {
      System.out.println(toString() + " constructed.");
    }
    
    @PreDestroy
    public void aboutToDie() {
      System.out.println(toString() + " ready to die.");
    }  
    
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
}