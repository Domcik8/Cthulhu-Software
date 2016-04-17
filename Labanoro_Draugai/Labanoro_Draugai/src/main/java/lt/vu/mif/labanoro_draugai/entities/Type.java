/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.Size;

/**
 *
 * @author Dominik Lisovski
 */
@Entity
@Table(name = "TYPE")
@NamedQueries({
    @NamedQuery(name = "Type.findAll", query = "SELECT t FROM Type t"),
    @NamedQuery(name = "Type.findById", query = "SELECT t FROM Type t WHERE t.id = :id"),
    @NamedQuery(name = "Type.findByInternalname", query = "SELECT t FROM Type t WHERE t.internalname = :internalname"),
    @NamedQuery(name = "Type.findByTitle", query = "SELECT t FROM Type t WHERE t.title = :title"),
    @NamedQuery(name = "Type.findByDescription", query = "SELECT t FROM Type t WHERE t.description = :description"),
    @NamedQuery(name = "Type.findByIsdeleted", query = "SELECT t FROM Type t WHERE t.isdeleted = :isdeleted"),
    @NamedQuery(name = "Type.findByOptLockVersion", query = "SELECT t FROM Type t WHERE t.optLockVersion = :optLockVersion")})
public class Type implements Serializable {

    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "typeid")
    private List<Servicepictures> servicepicturesList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "typeid")
    private List<Housepictures> housepicturesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "typeid")
    private List<Systemparameter> systemparameterList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 255)
    @Column(name = "INTERNALNAME")
    private String internalname;
    @Size(max = 255)
    @Column(name = "TITLE")
    private String title;
    @Size(max = 255)
    @Column(name = "DESCRIPTION")
    private String description;
    @Version
    @Column(name = "OPT_LOCK_VERSION")
    private Integer optLockVersion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "typeid")
    private List<Payment> paymentList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "typeid")
    private List<Person> personList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "typeid")
    private List<Reservation> reservationList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "typeid")
    private List<Service> serviceList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "typeid")
    private List<House> houseList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "typeid")
    private Systemparameter systemparameter;

    public Type() {
    }

    public Type(String internalName, String title, String description, Boolean isDeleted) {
        this.internalname = internalName;
        this.title = title;
        this.description = description;
        this.isdeleted = isDeleted;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInternalname() {
        return internalname;
    }

    public void setInternalname(String internalname) {
        this.internalname = internalname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Integer getOptLockVersion() {
        return optLockVersion;
    }

    public void setOptLockVersion(Integer optLockVersion) {
        this.optLockVersion = optLockVersion;
    }

    public List<Payment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<Payment> paymentList) {
        this.paymentList = paymentList;
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    public List<Reservation> getReservationList() {
        return reservationList;
    }

    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }

    public List<Service> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<Service> serviceList) {
        this.serviceList = serviceList;
    }

    public List<House> getHouseList() {
        return houseList;
    }

    public void setHouseList(List<House> houseList) {
        this.houseList = houseList;
    }

    public Systemparameter getSystemparameter() {
        return systemparameter;
    }

    public void setSystemparameter(Systemparameter systemparameter) {
        this.systemparameter = systemparameter;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.internalname);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Type other = (Type) obj;
        if (!Objects.equals(this.internalname, other.internalname)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lt.vu.mif.entities.Type[ id=" + internalname + " ]";
    }

    public List<Housepictures> getHousepicturesList() {
        return housepicturesList;
    }

    public void setHousepicturesList(List<Housepictures> housepicturesList) {
        this.housepicturesList = housepicturesList;
    }

    public List<Systemparameter> getSystemparameterList() {
        return systemparameterList;
    }

    public void setSystemparameterList(List<Systemparameter> systemparameterList) {
        this.systemparameterList = systemparameterList;
    }

    public Boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    public List<Servicepictures> getServicepicturesList() {
        return servicepicturesList;
    }

    public void setServicepicturesList(List<Servicepictures> servicepicturesList) {
        this.servicepicturesList = servicepicturesList;
    }
    
}
