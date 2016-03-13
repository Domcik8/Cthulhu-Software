/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.Size;

/**
 *
 * @author Dominik Lisovski
 */
@Entity
@Table(name = "OBJECTTABLE")
@NamedQueries({
    @NamedQuery(name = "Objecttable.findAll", query = "SELECT o FROM Objecttable o"),
    @NamedQuery(name = "Objecttable.findById", query = "SELECT o FROM Objecttable o WHERE o.id = :id"),
    @NamedQuery(name = "Objecttable.findByInternalname", query = "SELECT o FROM Objecttable o WHERE o.internalname = :internalname"),
    @NamedQuery(name = "Objecttable.findByIsdeleted", query = "SELECT o FROM Objecttable o WHERE o.isdeleted = :isdeleted"),
    @NamedQuery(name = "Objecttable.findByCreateddate", query = "SELECT o FROM Objecttable o WHERE o.createddate = :createddate"),
    @NamedQuery(name = "Objecttable.findByDeleteddate", query = "SELECT o FROM Objecttable o WHERE o.deleteddate = :deleteddate"),
    @NamedQuery(name = "Objecttable.findByOptLockVersion", query = "SELECT o FROM Objecttable o WHERE o.optLockVersion = :optLockVersion")})
public class Objecttable implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Size(max = 255)
    @Column(name = "INTERNALNAME")
    private String internalname;
    @Column(name = "ISDELETED")
    private Integer isdeleted;
    @Column(name = "CREATEDDATE")
    @Temporal(TemporalType.DATE)
    private Date createddate;
    @Column(name = "DELETEDDATE")
    @Temporal(TemporalType.DATE)
    private Date deleteddate;
    @Column(name = "OPT_LOCK_VERSION")
    @Version
    private Integer optLockVersion;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "objectid")
    private Role role;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personid")
    private List<Payment> paymentList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "objectid")
    private Payment payment;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "role")
    private List<Person> personList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "objectid")
    private Person person;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personid")
    private List<Reservation> reservationList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "houseid")
    private List<Reservation> reservationList1;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "objectid")
    private Reservation reservation;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "objectid")
    private Service service;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "objectid")
    private House house;
    @OneToMany(mappedBy = "deletedby")
    private List<Objecttable> objecttableList;
    @JoinColumn(name = "DELETEDBY", referencedColumnName = "ID")
    @ManyToOne
    private Objecttable deletedby;
    @OneToMany(mappedBy = "createdby")
    private List<Objecttable> objecttableList1;
    @JoinColumn(name = "CREATEDBY", referencedColumnName = "ID")
    @ManyToOne
    private Objecttable createdby;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "typeid")
    private List<Objecttable> objecttableList2;
    @JoinColumn(name = "TYPEID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Objecttable typeid;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "objectid")
    private Type type;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "objectid")
    private Systemparameter systemparameter;

    public Objecttable() {
    }

    public Objecttable(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInternalname() {
        return internalname;
    }

    public void setInternalname(String internalname) {
        this.internalname = internalname;
    }

    public Integer getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Integer isdeleted) {
        this.isdeleted = isdeleted;
    }

    public Date getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
    }

    public Date getDeleteddate() {
        return deleteddate;
    }

    public void setDeleteddate(Date deleteddate) {
        this.deleteddate = deleteddate;
    }

    public Integer getOptLockVersion() {
        return optLockVersion;
    }

    public void setOptLockVersion(Integer optLockVersion) {
        this.optLockVersion = optLockVersion;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Payment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<Payment> paymentList) {
        this.paymentList = paymentList;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public List<Reservation> getReservationList() {
        return reservationList;
    }

    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }

    public List<Reservation> getReservationList1() {
        return reservationList1;
    }

    public void setReservationList1(List<Reservation> reservationList1) {
        this.reservationList1 = reservationList1;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public List<Objecttable> getObjecttableList() {
        return objecttableList;
    }

    public void setObjecttableList(List<Objecttable> objecttableList) {
        this.objecttableList = objecttableList;
    }

    public Objecttable getDeletedby() {
        return deletedby;
    }

    public void setDeletedby(Objecttable deletedby) {
        this.deletedby = deletedby;
    }

    public List<Objecttable> getObjecttableList1() {
        return objecttableList1;
    }

    public void setObjecttableList1(List<Objecttable> objecttableList1) {
        this.objecttableList1 = objecttableList1;
    }

    public Objecttable getCreatedby() {
        return createdby;
    }

    public void setCreatedby(Objecttable createdby) {
        this.createdby = createdby;
    }

    public List<Objecttable> getObjecttableList2() {
        return objecttableList2;
    }

    public void setObjecttableList2(List<Objecttable> objecttableList2) {
        this.objecttableList2 = objecttableList2;
    }

    public Objecttable getTypeid() {
        return typeid;
    }

    public void setTypeid(Objecttable typeid) {
        this.typeid = typeid;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Systemparameter getSystemparameter() {
        return systemparameter;
    }

    public void setSystemparameter(Systemparameter systemparameter) {
        this.systemparameter = systemparameter;
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
        if (!(object instanceof Objecttable)) {
            return false;
        }
        Objecttable other = (Objecttable) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lt.vu.mif.entities.Objecttable[ id=" + id + " ]";
    }
    
}
