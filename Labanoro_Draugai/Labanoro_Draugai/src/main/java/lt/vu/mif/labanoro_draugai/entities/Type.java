/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.entities;

import java.io.Serializable;
import java.util.List;
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
    @NamedQuery(name = "Type.findAll", query = "SELECT t FROM Type t WHERE (t.isdeleted IS NULL OR t.isdeleted = FALSE)"),
    @NamedQuery(name = "Type.findById", query = "SELECT t FROM Type t WHERE (t.isdeleted IS NULL OR t.isdeleted = FALSE) AND t.id = :id"),
    @NamedQuery(name = "Type.findByInternalname", query = "SELECT t FROM Type t WHERE (t.isdeleted IS NULL OR t.isdeleted = FALSE) AND t.internalname = :internalname"),
    @NamedQuery(name = "Type.findByTitle", query = "SELECT t FROM Type t WHERE (t.isdeleted IS NULL OR t.isdeleted = FALSE) AND t.title = :title"),
    @NamedQuery(name = "Type.findByDescription", query = "SELECT t FROM Type t WHERE (t.isdeleted IS NULL OR t.isdeleted = FALSE) AND t.description = :description"),
    @NamedQuery(name = "Type.findByIsdeleted", query = "SELECT t FROM Type t WHERE t.isdeleted = :isdeleted")})
public class Type implements Serializable {

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
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @Column(name = "OPTLOCKVERSION")
    @Version
    private Integer optlockversion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "currencytypeid")
    private List<Payment> paymentList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "typeid")
    private List<Payment> paymentList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "typeid")
    private List<Person> personList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "typeid")
    private List<Reservation> reservationList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "typeid")
    private List<Formattribute> formattributeList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "typeid")
    private List<Personregistrationform> personregistrationformList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "typeid")
    private List<Service> serviceList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "typeid")
    private List<House> houseList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "typeid")
    private List<Recommendation> recommendationList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "typeid")
    private List<Houseimage> houseimageList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "typeid")
    private List<Systemparameter> systemparameterList;

    public Type() {
    }

    public Type(Integer id) {
        this.id = id;
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

    public Boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    public Integer getOptlockversion() {
        return optlockversion;
    }

    public void setOptlockversion(Integer optlockversion) {
        this.optlockversion = optlockversion;
    }

    public List<Payment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<Payment> paymentList) {
        this.paymentList = paymentList;
    }

    public List<Payment> getPaymentList1() {
        return paymentList1;
    }

    public void setPaymentList1(List<Payment> paymentList1) {
        this.paymentList1 = paymentList1;
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

    public List<Formattribute> getFormattributeList() {
        return formattributeList;
    }

    public void setFormattributeList(List<Formattribute> formattributeList) {
        this.formattributeList = formattributeList;
    }

    public List<Personregistrationform> getPersonregistrationformList() {
        return personregistrationformList;
    }

    public void setPersonregistrationformList(List<Personregistrationform> personregistrationformList) {
        this.personregistrationformList = personregistrationformList;
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

    public List<Recommendation> getRecommendationList() {
        return recommendationList;
    }

    public void setRecommendationList(List<Recommendation> recommendationList) {
        this.recommendationList = recommendationList;
    }

    public List<Houseimage> getHouseimageList() {
        return houseimageList;
    }

    public void setHouseimageList(List<Houseimage> houseimageList) {
        this.houseimageList = houseimageList;
    }

    public List<Systemparameter> getSystemparameterList() {
        return systemparameterList;
    }

    public void setSystemparameterList(List<Systemparameter> systemparameterList) {
        this.systemparameterList = systemparameterList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Type)) {
            return false;
        }
        Type other = (Type) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lt.vu.mif.labanoro_draugai.entities.Type[ id=" + id + " ]";
    }
    
}
