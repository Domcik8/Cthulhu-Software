
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped // @SessionScoped
@Stateful
public class PirmasKomponentas {
    
    @Inject
    private AntrasKomponentas Antras1;
    
    private AntrasKomponentas Antras2 = new AntrasKomponentas();

    public String sakykLabas() {
      return "Labas " + new Date() + " " + toString();
    }
    
    @PostConstruct
    public void init() {
      System.out.println(toString() + " constructed.");
    }
    
    @PreDestroy
    public void aboutToDie() {
      System.out.println(toString() + " ready to die.");
    }   
    
    public String TestMethods(){
        return "Pirmas: " + Antras1.getClass().getName() + ", antras: " + Antras2.getClass().getName();
    }
}