/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.data_models;

import java.io.Serializable;
import java.util.List;
import javax.faces.model.SelectItem;

/**
 *
 * @author Karolis
 */
public class AdminUserFormProperty implements Serializable{
    private static final long serialVersionUID = 20120521L;  
  
    private String labelName;  
    private String selectionValues;
    private String selectedType;
    private boolean required;
    private List<SelectItem> type;  
    private int index;

    public AdminUserFormProperty(List<SelectItem> type, int index) {
        this.type = type;
        this.index = index;
    }   
    
    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getSelectionValues() {
        return selectionValues;
    }

    public void setSelectionValues(String selectionValues) {
        this.selectionValues = selectionValues;
    }

    public String getSelectedType() {
        return selectedType;
    }

    public void setSelectedType(String selectedType) {
        this.selectedType = selectedType;
    }
  
    public boolean isRequired() {  
        return required;  
    }  
  
    public void setRequired(boolean required) {  
        this.required = required;  
    }  

    public List<SelectItem> getType() {
        return type;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
    
}
