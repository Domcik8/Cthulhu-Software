import java.util.Date;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped // @SessionScoped
@Stateful
public class AntrasKomponentas {

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
}