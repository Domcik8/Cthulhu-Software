/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lt.vu.mif.labanoro_draugai.business.DatabaseManager;
import lt.vu.mif.labanoro_draugai.data_models.UserFormProperty;
import lt.vu.mif.labanoro_draugai.entities.Formattribute;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

/**
 *
 * @author Karolis
 */
@Named
@ViewScoped
public class RegistrationManager implements Serializable{
    
    private DynaFormModel displayModel;

    @Inject
    DatabaseManager dbm;
    
    @PostConstruct
    public void init(){
        displayModel = new DynaFormModel();
        List<Formattribute> attributes = (List<Formattribute>)dbm.getAllEntities("Formattribute");
        if(attributes == null) return;
        
        for(Formattribute attribute:attributes){
            DynaFormRow row = displayModel.createRegularRow();
            DynaFormLabel label = row.addLabel(attribute.getName()); 
            DynaFormControl control = row.addControl(new UserFormProperty(attribute.getName(),attribute.getIsrequired(),parseSelectValues(attribute.getListitems())),
                    attribute.getTypeid().getInternalname());
             label.setForControl(control);
        }
        
    } 
      
    private List<SelectItem> parseSelectValues(String selectionValues){
        if(selectionValues == null || selectionValues.isEmpty()) return null;
        List<SelectItem> result = new ArrayList<>();
        String[] values = selectionValues.split(",");
        for(String str:values){
            System.out.println(str.trim());
            result.add(new SelectItem(str.trim()));
        }
        return result;
    }
    
    public DynaFormModel getDisplayModel() {
        return displayModel;
    }
}
