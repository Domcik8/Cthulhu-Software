/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Dominik Lisovski
 */
@Entity
@Table(name = "PAYMENT")
@NamedQueries({
    @NamedQuery(name = "Payment.findAll", query = "SELECT p FROM Payment p"),
    @NamedQuery(name = "Payment.findById", query = "SELECT p FROM Payment p WHERE p.id = :id"),
    @NamedQuery(name = "Payment.findByPaymentreg", query = "SELECT p FROM Payment p WHERE p.paymentreg = :paymentreg"),
    @NamedQuery(name = "Payment.findByPaymentprice", query = "SELECT p FROM Payment p WHERE p.paymentprice = :paymentprice"),
    @NamedQuery(name = "Payment.findByPaymentdate", query = "SELECT p FROM Payment p WHERE p.paymentdate = :paymentdate"),
    @NamedQuery(name = "Payment.findByPaidwithmoney", query = "SELECT p FROM Payment p WHERE p.paidwithmoney = :paidwithmoney"),
    @NamedQuery(name = "Payment.findByOptLockVersion", query = "SELECT p FROM Payment p WHERE p.optLockVersion = :optLockVersion")})
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "PAYMENTREG")
    private String paymentreg;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PAYMENTPRICE")
    private int paymentprice;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PAYMENTDATE")
    @Temporal(TemporalType.DATE)
    private Date paymentdate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PAIDWITHMONEY")
    private int paidwithmoney;
    @Column(name = "OPT_LOCK_VERSION")
    private Integer optLockVersion;
    @JoinColumn(name = "PERSONID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Objecttable personid;
    @JoinColumn(name = "OBJECTID", referencedColumnName = "ID")
    @OneToOne(optional = false)
    private Objecttable objectid;
    @JoinColumn(name = "PERSONVERSIONID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Person personversionid;

    public Payment() {
    }

    public Payment(Long id) {
        this.id = id;
    }

    public Payment(Long id, String paymentreg, int paymentprice, Date paymentdate, int paidwithmoney) {
        this.id = id;
        this.paymentreg = paymentreg;
        this.paymentprice = paymentprice;
        this.paymentdate = paymentdate;
        this.paidwithmoney = paidwithmoney;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymentreg() {
        return paymentreg;
    }

    public void setPaymentreg(String paymentreg) {
        this.paymentreg = paymentreg;
    }

    public int getPaymentprice() {
        return paymentprice;
    }

    public void setPaymentprice(int paymentprice) {
        this.paymentprice = paymentprice;
    }

    public Date getPaymentdate() {
        return paymentdate;
    }

    public void setPaymentdate(Date paymentdate) {
        this.paymentdate = paymentdate;
    }

    public int getPaidwithmoney() {
        return paidwithmoney;
    }

    public void setPaidwithmoney(int paidwithmoney) {
        this.paidwithmoney = paidwithmoney;
    }

    public Integer getOptLockVersion() {
        return optLockVersion;
    }

    public void setOptLockVersion(Integer optLockVersion) {
        this.optLockVersion = optLockVersion;
    }

    public Objecttable getPersonid() {
        return personid;
    }

    public void setPersonid(Objecttable personid) {
        this.personid = personid;
    }

    public Objecttable getObjectid() {
        return objectid;
    }

    public void setObjectid(Objecttable objectid) {
        this.objectid = objectid;
    }

    public Person getPersonversionid() {
        return personversionid;
    }

    public void setPersonversionid(Person personversionid) {
        this.personversionid = personversionid;
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
        return "lt.vu.entities.Payment[ id=" + id + " ]";
    }
    
}
