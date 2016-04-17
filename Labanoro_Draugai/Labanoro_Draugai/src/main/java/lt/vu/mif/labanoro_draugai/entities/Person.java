/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
@Table(name = "PERSON")
@NamedQueries({
    @NamedQuery(name = "Person.findAll", query = "SELECT p FROM Person p"),
    @NamedQuery(name = "Person.findById", query = "SELECT p FROM Person p WHERE p.id = :id"),
    @NamedQuery(name = "Person.findByUsername", query = "SELECT p FROM Person p WHERE p.username = :username"),
    @NamedQuery(name = "Person.findByPassword", query = "SELECT p FROM Person p WHERE p.password = :password"),
    @NamedQuery(name = "Person.findByPriority", query = "SELECT p FROM Person p WHERE p.priority = :priority"),
    @NamedQuery(name = "Person.findByPoints", query = "SELECT p FROM Person p WHERE p.points = :points"),
    @NamedQuery(name = "Person.findByFacebookid", query = "SELECT p FROM Person p WHERE p.facebookid = :facebookid"),
    @NamedQuery(name = "Person.findByFirstname", query = "SELECT p FROM Person p WHERE p.firstname = :firstname"),
    @NamedQuery(name = "Person.findByMiddlename", query = "SELECT p FROM Person p WHERE p.middlename = :middlename"),
    @NamedQuery(name = "Person.findByLastname", query = "SELECT p FROM Person p WHERE p.lastname = :lastname"),
    @NamedQuery(name = "Person.findByAddress", query = "SELECT p FROM Person p WHERE p.address = :address"),
    @NamedQuery(name = "Person.findByPersonalid", query = "SELECT p FROM Person p WHERE p.personalid = :personalid"),
    @NamedQuery(name = "Person.findByMembershipdue", query = "SELECT p FROM Person p WHERE p.membershipdue = :membershipdue"),
    @NamedQuery(name = "Person.findByIsdeleted", query = "SELECT p FROM Person p WHERE p.isdeleted = :isdeleted"),
    @NamedQuery(name = "Person.findByOptLockVersion", query = "SELECT p FROM Person p WHERE p.optLockVersion = :optLockVersion")})
public class Person implements Serializable {

    @Size(max = 255)
    @Column(name = "FACEBOOKID")
    private String facebookid;
    @Size(max = 255)
    @Column(name = "FACEBOOKACCESSTOKEN")
    private String facebookaccesstoken;
    @Column(name = "ISDELETED")
    private Boolean isdeleted;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personid")
    private List<Payment> paymentList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personid")
    private List<Reservation> reservationList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 255)
    @Column(name = "USERNAME")
    private String username;
    @Size(max = 255)
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "PRIORITY")
    private Integer priority;
    @Column(name = "POINTS")
    private Integer points;
    @Size(max = 255)
    @Column(name = "FIRSTNAME")
    private String firstname;
    @Size(max = 255)
    @Column(name = "MIDDLENAME")
    private String middlename;
    @Size(max = 255)
    @Column(name = "LASTNAME")
    private String lastname;
    @Size(max = 255)
    @Column(name = "ADDRESS")
    private String address;
    @Size(max = 255)
    @Column(name = "PERSONALID")
    private String personalid;
    @Column(name = "MEMBERSHIPDUE")
    @Temporal(TemporalType.DATE)
    private Date membershipdue;
    @Version
    @Column(name = "OPT_LOCK_VERSION")
    private Integer optLockVersion;
    @JoinColumn(name = "TYPEID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Type typeid;

    public Person() {
    }

    public Person(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }


    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPersonalid() {
        return personalid;
    }

    public void setPersonalid(String personalid) {
        this.personalid = personalid;
    }

    public Date getMembershipdue() {
        return membershipdue;
    }

    public void setMembershipdue(Date membershipdue) {
        this.membershipdue = membershipdue;
    }


    public Integer getOptLockVersion() {
        return optLockVersion;
    }

    public void setOptLockVersion(Integer optLockVersion) {
        this.optLockVersion = optLockVersion;
    }

    public Type getTypeid() {
        return typeid;
    }

    public void setTypeid(Type typeid) {
        this.typeid = typeid;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.personalid);
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
        final Person other = (Person) obj;
        if (!Objects.equals(this.personalid, other.personalid)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "lt.vu.mif.entities.Person[ id=" + id + " ]";
    }

    public List<Payment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<Payment> paymentList) {
        this.paymentList = paymentList;
    }

    public List<Reservation> getReservationList() {
        return reservationList;
    }

    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }

    public String getFacebookid() {
        return facebookid;
    }

    public void setFacebookid(String facebookid) {
        this.facebookid = facebookid;
    }

    public String getFacebookaccesstoken() {
        return facebookaccesstoken;
    }

    public void setFacebookaccesstoken(String facebookaccesstoken) {
        this.facebookaccesstoken = facebookaccesstoken;
    }

    public Boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
    }
    
}
