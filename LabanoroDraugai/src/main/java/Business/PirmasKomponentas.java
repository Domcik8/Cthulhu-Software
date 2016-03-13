import java.util.Date;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lt.vu.entities.House;

@Named
@RequestScoped // @SessionScoped
@Stateful
public class PirmasKomponentas {
    
    @Inject 
    private AntrasKomponentas antras;
    @Inject
    private House House1;
    @Inject
    private House House2;;

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
    
    public String TestComponents(){
        AntrasKomponentas trecias = new AntrasKomponentas();
        return "Antras: " + antras.getClass().getName() + " trecias: " + trecias.getClass().getName();
    }
    
    public String TestMethods(){
        if(House1 == House2)
            return "egual";
        return "different";
    }
}