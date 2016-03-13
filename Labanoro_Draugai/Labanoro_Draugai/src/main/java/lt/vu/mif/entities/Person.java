/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.entities;

import java.io.Serializable;
import java.math.BigInteger;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
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
    @NamedQuery(name = "Person.findByOptLockVersion", query = "SELECT p FROM Person p WHERE p.optLockVersion = :optLockVersion")})
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "USERNAME")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "PASSWORD")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRIORITY")
    private int priority;
    @Basic(optional = false)
    @NotNull
    @Column(name = "POINTS")
    private int points;
    @Column(name = "FACEBOOKID")
    private BigInteger facebookid;
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
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "PERSONALID")
    private String personalid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MEMBERSHIPDUE")
    @Temporal(TemporalType.DATE)
    private Date membershipdue;
    @Column(name = "OPT_LOCK_VERSION")
    @Version
    private Integer optLockVersion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personversionid")
    private List<Payment> paymentList;
    @JoinColumn(name = "ROLE", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Objecttable role;
    @JoinColumn(name = "OBJECTID", referencedColumnName = "ID")
    @OneToOne(optional = false)
    private Objecttable objectid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personversionid")
    private List<Reservation> reservationList;

    public Person() {
    }

    public Person(Long id) {
        this.id = id;
    }

    public Person(Long id, String username, String password, int priority, int points, String personalid, Date membershipdue) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.priority = priority;
        this.points = points;
        this.personalid = personalid;
        this.membershipdue = membershipdue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public BigInteger getFacebookid() {
        return facebookid;
    }

    public void setFacebookid(BigInteger facebookid) {
        this.facebookid = facebookid;
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

    public List<Payment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<Payment> paymentList) {
        this.paymentList = paymentList;
    }

    public Objecttable getRole() {
        return role;
    }

    public void setRole(Objecttable role) {
        this.role = role;
    }

    public Objecttable getObjectid() {
        return objectid;
    }

    public void setObjectid(Objecttable objectid) {
        this.objectid = objectid;
    }

    public List<Reservation> getReservationList() {
        return reservationList;
    }

    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.personalid);
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
    
}
