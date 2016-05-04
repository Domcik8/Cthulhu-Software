/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.data_models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.faces.model.SelectItem;

/**
 *
 * @author Karolis
 */
public class UserFormProperty implements Serializable{
    private static final long serialVersionUID = 20120521L;  
  
    private String name;  
    private Object value = null;  
    private boolean required;
    private List<SelectItem> selectItems;  
  
    public UserFormProperty(String name, boolean required) {  
        this.name = name;  
        this.required = required;  
    }  
    
    public UserFormProperty(String name, boolean required, List<SelectItem> selectItems) {  
        this.name = name;  
        this.value = selectItems==null|| selectItems.isEmpty()? null : selectItems.get(0);  
        this.required = required;
        this.selectItems = selectItems;
    } 
  
    public String getName() {  
        return name;  
    }  
  
    public void setName(String name) {  
        this.name = name;  
    }  
  
    public Object getValue() {  
        return value;  
    }  
  
    public Object getFormattedValue() {  
        if (value instanceof Date) {  
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");  
  
            return simpleDateFormat.format(value);  
        }  
  
        return value;  
    }  
  
    public void setValue(Object value) {  
        this.value = value;  
    }  
  
    public boolean isRequired() {  
        return required;  
    }  
  
    public void setRequired(boolean required) {  
        this.required = required;  
    }  

    public List<SelectItem> getSelectItems() {
        return selectItems;
    }
    
}
