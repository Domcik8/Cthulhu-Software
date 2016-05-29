/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.entities;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Dominik Lisovski
 */
@Entity
@Table(name = "PERSON")
@NamedQueries({
    @NamedQuery(name = "Person.findAll", query = "SELECT p FROM Person p WHERE (p.isdeleted IS NULL OR p.isdeleted = FALSE)"),
    @NamedQuery(name = "Person.findById", query = "SELECT p FROM Person p WHERE (p.isdeleted IS NULL OR p.isdeleted = FALSE) AND p.id = :id"),
    @NamedQuery(name = "Person.findByEmail", query = "SELECT p FROM Person p WHERE (p.isdeleted IS NULL OR p.isdeleted = FALSE) AND p.email = :email"),
    @NamedQuery(name = "Person.findByPassword", query = "SELECT p FROM Person p WHERE (p.isdeleted IS NULL OR p.isdeleted = FALSE) AND p.password = :password"),
    @NamedQuery(name = "Person.findByPriority", query = "SELECT p FROM Person p WHERE (p.isdeleted IS NULL OR p.isdeleted = FALSE) AND p.priority = :priority"),
    @NamedQuery(name = "Person.findByPoints", query = "SELECT p FROM Person p WHERE (p.isdeleted IS NULL OR p.isdeleted = FALSE) AND p.points = :points"),
    @NamedQuery(name = "Person.findByFacebookid", query = "SELECT p FROM Person p WHERE (p.isdeleted IS NULL OR p.isdeleted = FALSE) AND p.facebookid = :facebookid"),
    @NamedQuery(name = "Person.findByFacebookpassword", query = "SELECT p FROM Person p WHERE (p.isdeleted IS NULL OR p.isdeleted = FALSE) AND p.facebookpassword = :facebookpassword"),
    @NamedQuery(name = "Person.findByFirstname", query = "SELECT p FROM Person p WHERE (p.isdeleted IS NULL OR p.isdeleted = FALSE) AND p.firstname = :firstname"),
    @NamedQuery(name = "Person.findByMiddlename", query = "SELECT p FROM Person p WHERE (p.isdeleted IS NULL OR p.isdeleted = FALSE) AND p.middlename = :middlename"),
    @NamedQuery(name = "Person.findByLastname", query = "SELECT p FROM Person p WHERE (p.isdeleted IS NULL OR p.isdeleted = FALSE) AND p.lastname = :lastname"),
    @NamedQuery(name = "Person.findByAddress", query = "SELECT p FROM Person p WHERE (p.isdeleted IS NULL OR p.isdeleted = FALSE) AND p.address = :address"),
    @NamedQuery(name = "Person.findByPersonalid", query = "SELECT p FROM Person p WHERE (p.isdeleted IS NULL OR p.isdeleted = FALSE) AND p.personalid = :personalid"),
    @NamedQuery(name = "Person.findByMembershipdue", query = "SELECT p FROM Person p WHERE (p.isdeleted IS NULL OR p.isdeleted = FALSE) AND p.membershipdue = :membershipdue"),
    @NamedQuery(name = "Person.findByRecommendationsreceived", query = "SELECT p FROM Person p WHERE (p.isdeleted IS NULL OR p.isdeleted = FALSE) AND p.recommendationsreceived = :recommendationsreceived"),
    @NamedQuery(name = "Person.findByRecommendationstosend", query = "SELECT p FROM Person p WHERE (p.isdeleted IS NULL OR p.isdeleted = FALSE) AND p.recommendationstosend = :recommendationstosend"),
    @NamedQuery(name = "Person.findByEmailconfirmation", query = "SELECT p FROM Person p WHERE (p.isdeleted IS NULL OR p.isdeleted = FALSE) AND p.emailconfirmation = :emailconfirmation"),
    @NamedQuery(name = "Person.findByUniquekey", query = "SELECT p FROM Person p WHERE (p.isdeleted IS NULL OR p.isdeleted = FALSE) AND p.uniquekey = :uniquekey"),
    @NamedQuery(name = "Person.findByIsdeleted", query = "SELECT p FROM Person p WHERE p.isdeleted = :isdeleted")})
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 255)
    @Column(name = "EMAIL")
    private String email;
    @Size(max = 255)
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "PRIORITY")
    private Integer priority;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "POINTS")
    private BigDecimal points;
    @Size(max = 255)
    @Column(name = "FACEBOOKID")
    private String facebookid;
    @Size(max = 255)
    @Column(name = "FACEBOOKPASSWORD")
    private String facebookpassword;
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
    @Temporal(TemporalType.TIMESTAMP)
    private Date membershipdue;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RECOMMENDATIONSRECEIVED")
    private int recommendationsreceived;
    @Column(name = "RECOMMENDATIONSTOSEND")
    private Integer recommendationstosend;
    @Size(max = 255)
    @Column(name = "EMAILCONFIRMATION")
    private String emailconfirmation;
    @Size(max = 255)
    @Column(name = "UNIQUEKEY")
    private String uniquekey;
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @Column(name = "OPTLOCKVERSION")
    @Version
    private Integer optlockversion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personid")
    private List<Payment> paymentList;
    @JoinColumn(name = "TYPEID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Type typeid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personid")
    private List<Reservation> reservationList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "personid")
    private Personregistrationform personregistrationform;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recommenderid")
    private List<Recommendation> recommendationList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recommendedid")
    private List<Recommendation> recommendationList1;

    public Person() {
    }

    public Person(Integer id) {
        this.id = id;
    }

    public Person(Integer id, int recommendationsreceived) {
        this.id = id;
        this.recommendationsreceived = recommendationsreceived;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public BigDecimal getPoints() {
        return points;
    }

    public void setPoints(BigDecimal points) {
        this.points = points;
    }

    public String getFacebookid() {
        return facebookid;
    }

    public void setFacebookid(String facebookid) {
        this.facebookid = facebookid;
    }

    public String getFacebookpassword() {
        return facebookpassword;
    }

    public void setFacebookpassword(String facebookpassword) {
        this.facebookpassword = facebookpassword;
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

    public int getRecommendationsreceived() {
        return recommendationsreceived;
    }

    public void setRecommendationsreceived(int recommendationsreceived) {
        this.recommendationsreceived = recommendationsreceived;
    }

    public Integer getRecommendationstosend() {
        return recommendationstosend;
    }

    public void setRecommendationstosend(Integer recommendationstosend) {
        this.recommendationstosend = recommendationstosend;
    }

    public String getEmailconfirmation() {
        return emailconfirmation;
    }

    public void setEmailconfirmation(String emailconfirmation) {
        this.emailconfirmation = emailconfirmation;
    }

    public String getUniquekey() {
        return uniquekey;
    }

    public void setUniquekey(String uniquekey) {
        this.uniquekey = uniquekey;
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

    public Type getTypeid() {
        return typeid;
    }

    public void setTypeid(Type typeid) {
        this.typeid = typeid;
    }

    public List<Reservation> getReservationList() {
        return reservationList;
    }

    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }

    public Personregistrationform getPersonregistrationform() {
        return personregistrationform;
    }

    public void setPersonregistrationform(Personregistrationform personregistrationform) {
        this.personregistrationform = personregistrationform;
    }

    public List<Recommendation> getRecommendationList() {
        return recommendationList;
    }

    public void setRecommendationList(List<Recommendation> recommendationList) {
        this.recommendationList = recommendationList;
    }

    public List<Recommendation> getRecommendationList1() {
        return recommendationList1;
    }

    public void setRecommendationList1(List<Recommendation> recommendationList1) {
        this.recommendationList1 = recommendationList1;
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
        if (!(object instanceof Person)) {
            return false;
        }
        Person other = (Person) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lt.vu.mif.labanoro_draugai.entities.Person[ id=" + id + " ]";
    }
    
}
