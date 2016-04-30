/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.administration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import lt.vu.mif.labanoro_draugai.data_models.AdminUserFormProperty;
import lt.vu.mif.labanoro_draugai.data_models.UserFormProperty;
import org.primefaces.context.RequestContext;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;



/**
 *
 * @author Karolis
 */
@ManagedBean(name = "userFormCreationManager")
@ViewScoped
public class UserFormCreationManager implements Serializable{

  
    private static final long serialVersionUID = 20120423L;  
  
    private DynaFormModel model;
    private DynaFormModel displayModel;
    
    private List<AdminUserFormProperty> adminFormProperties;
    private List<SelectItem> fieldTypes;
    
    @PostConstruct  
    protected void initialize() {  
        model = new DynaFormModel();
        adminFormProperties = new ArrayList<>();
        updateDisplayDynaForm();
        
        //get types from db
        fieldTypes = new ArrayList<>();
        fieldTypes.add(new SelectItem("input","Teksto laukas"));
        fieldTypes.add(new SelectItem("select","Pasirinkti vieną"));
        fieldTypes.add(new SelectItem("textarea","Didelis teksto laukas"));
        fieldTypes.add(new SelectItem("calendar","Datos pasirinkimas"));
        fieldTypes.add(new SelectItem("number","Skaičius"));
        //load current form

        
        for(int i =0;i<1;i++){
            DynaFormRow row = model.createRegularRow();
            
            AdminUserFormProperty adminProp = new AdminUserFormProperty(fieldTypes,i);
            addAdminPropertyToRowAndList(row, adminProp);
        }
        System.out.println("eilucius skaicius administravimo formoje: " + adminFormProperties.size());
    }  
  
    public void removeAdminFormPropery(AdminUserFormProperty property) {  
        model.removeRegularRow(property.getIndex());          
        adminFormProperties.remove(property);  
          
        // re-index conditions  
        int idx = 0;  
        for (AdminUserFormProperty prop : adminFormProperties) {  
            prop.setIndex(idx);  
            idx++;  
        }  
    }
    public void addAdminFormPropery() {  
        DynaFormRow row = model.createRegularRow();
        AdminUserFormProperty adminProp;
        if(adminFormProperties!=null){
            adminProp = new AdminUserFormProperty(fieldTypes,adminFormProperties.get(adminFormProperties.size()-1).getIndex()+1); 
        }else{
            adminFormProperties = new ArrayList<>();
            adminProp = new AdminUserFormProperty(fieldTypes,0); 
        }
        
        addAdminPropertyToRowAndList(row, adminProp);

    } 
    
    private void addAdminPropertyToRowAndList(DynaFormRow row, AdminUserFormProperty adminProp){
        adminFormProperties.add(adminProp);
            
        DynaFormLabel label = row.addLabel("Pavadinimas:");  
        DynaFormControl control = row.addControl(adminProp, "inputName");  
        label.setForControl(control);

        label = row.addLabel("tipas:");  
        control = row.addControl(adminProp, "select");  
        label.setForControl(control);

        label = row.addLabel("privalomas:");  
        control = row.addControl(adminProp, "booleanchoice");  
        label.setForControl(control);

        label = row.addLabel("reikšmės:");  
        control = row.addControl(adminProp, "input");  
        label.setForControl(control);

        row.addControl(adminProp, "remove");
    }
    
    
    public DynaFormModel getModel() {  
        return model;  
    }  
  
    public List<AdminUserFormProperty> getAdminUserProperties() {  
        return adminFormProperties;  
    }  
  
    public String submitForm() {  
        FacesMessage.Severity sev = FacesContext.getCurrentInstance().getMaximumSeverity();  
        boolean hasErrors = (sev != null && (FacesMessage.SEVERITY_ERROR.compareTo(sev) >= 0));  
  
        RequestContext requestContext = RequestContext.getCurrentInstance();  
        requestContext.addCallbackParam("isValid", !hasErrors);  
  
        updateDisplayDynaForm();
        
        return null;  
    }
    
    private void updateDisplayDynaForm(){
        displayModel = new DynaFormModel();  
  
        for(AdminUserFormProperty adminProp:adminFormProperties){
            DynaFormRow row = displayModel.createRegularRow();
            
            DynaFormLabel label = row.addLabel(adminProp.getLabelName()); 
            DynaFormControl control = adminProp.getSelectedType().equals("select")?
                row.addControl(new UserFormProperty(adminProp.getLabelName(),adminProp.isRequired(),parseSelectValues(adminProp.getSelectionValues())), adminProp.getSelectedType()):
                row.addControl(new UserFormProperty(adminProp.getLabelName(),adminProp.isRequired()), adminProp.getSelectedType());  
             label.setForControl(control);
        }
    }
    
    private List<SelectItem> parseSelectValues(String selectionValues){
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

    public void setDisplayModel(DynaFormModel displayModel) {
        this.displayModel = displayModel;
    }
    
}