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
@Table(name = "PAYMENT")
@NamedQueries({
    @NamedQuery(name = "Payment.findAll", query = "SELECT p FROM Payment p WHERE (p.isdeleted IS NULL OR p.isdeleted = FALSE)"),
    @NamedQuery(name = "Payment.findById", query = "SELECT p FROM Payment p WHERE (p.isdeleted IS NULL OR p.isdeleted = FALSE) AND p.id = :id"),
    @NamedQuery(name = "Payment.findByPaymentreg", query = "SELECT p FROM Payment p WHERE (p.isdeleted IS NULL OR p.isdeleted = FALSE) AND p.paymentreg = :paymentreg"),
    @NamedQuery(name = "Payment.findByPaymentprice", query = "SELECT p FROM Payment p WHERE (p.isdeleted IS NULL OR p.isdeleted = FALSE) AND p.paymentprice = :paymentprice"),
    @NamedQuery(name = "Payment.findByPaymentdate", query = "SELECT p FROM Payment p WHERE (p.isdeleted IS NULL OR p.isdeleted = FALSE) AND p.paymentdate = :paymentdate"),
    @NamedQuery(name = "Payment.findByApproveddate", query = "SELECT p FROM Payment p WHERE (p.isdeleted IS NULL OR p.isdeleted = FALSE) AND p.approveddate = :approveddate"),
    @NamedQuery(name = "Payment.findByIsdeleted", query = "SELECT p FROM Payment p WHERE p.isdeleted = :isdeleted")})
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "PAYMENTREG")
    private String paymentreg;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PAYMENTPRICE")
    private BigDecimal paymentprice;
    @Column(name = "PAYMENTDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentdate;
    @Column(name = "APPROVEDDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date approveddate;
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @Column(name = "OPTLOCKVERSION")
    @Version
    private Integer optlockversion;
    @JoinColumn(name = "PERSONID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Person personid;
    @JoinColumn(name = "RESERVATIONID", referencedColumnName = "ID")
    @ManyToOne
    private Reservation reservationid;
    @JoinColumn(name = "CURRENCYTYPEID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Type currencytypeid;
    @JoinColumn(name = "TYPEID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Type typeid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paymentid")
    private List<Reservation> reservationList;

    public Payment() {
    }

    public Payment(Integer id) {
        this.id = id;
    }

    public Payment(Integer id, String paymentreg) {
        this.id = id;
        this.paymentreg = paymentreg;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPaymentreg() {
        return paymentreg;
    }

    public void setPaymentreg(String paymentreg) {
        this.paymentreg = paymentreg;
    }

    public BigDecimal getPaymentprice() {
        return paymentprice;
    }

    public void setPaymentprice(BigDecimal paymentprice) {
        this.paymentprice = paymentprice;
    }

    public Date getPaymentdate() {
        return paymentdate;
    }

    public void setPaymentdate(Date paymentdate) {
        this.paymentdate = paymentdate;
    }

    public Date getApproveddate() {
        return approveddate;
    }

    public void setApproveddate(Date approveddate) {
        this.approveddate = approveddate;
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

    public Person getPersonid() {
        return personid;
    }

    public void setPersonid(Person personid) {
        this.personid = personid;
    }

    public Reservation getReservationid() {
        return reservationid;
    }

    public void setReservationid(Reservation reservationid) {
        this.reservationid = reservationid;
    }

    public Type getCurrencytypeid() {
        return currencytypeid;
    }

    public void setCurrencytypeid(Type currencytypeid) {
        this.currencytypeid = currencytypeid;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Payment)) {
            return false;
        }
        Payment other = (Payment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lt.vu.mif.labanoro_draugai.entities.Payment[ id=" + id + " ]";
    }
    
}
