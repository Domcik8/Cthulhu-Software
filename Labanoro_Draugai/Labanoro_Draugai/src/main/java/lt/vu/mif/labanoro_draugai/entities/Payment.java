/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
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
@Table(name = "PAYMENT")
@NamedQueries({
    @NamedQuery(name = "Payment.findAll", query = "SELECT p FROM Payment p"),
    @NamedQuery(name = "Payment.findById", query = "SELECT p FROM Payment p WHERE p.id = :id"),
    @NamedQuery(name = "Payment.findByPaymentreg", query = "SELECT p FROM Payment p WHERE p.paymentreg = :paymentreg"),
    @NamedQuery(name = "Payment.findByPaymentprice", query = "SELECT p FROM Payment p WHERE p.paymentprice = :paymentprice"),
    @NamedQuery(name = "Payment.findByPaymentdate", query = "SELECT p FROM Payment p WHERE p.paymentdate = :paymentdate"),
    @NamedQuery(name = "Payment.findByPaidwithmoney", query = "SELECT p FROM Payment p WHERE p.paidwithmoney = :paidwithmoney"),
    @NamedQuery(name = "Payment.findByIsdeleted", query = "SELECT p FROM Payment p WHERE p.isdeleted = :isdeleted"),
    @NamedQuery(name = "Payment.findByOptLockVersion", query = "SELECT p FROM Payment p WHERE p.optLockVersion = :optLockVersion")})
public class Payment implements Serializable {

    @Column(name = "ISDELETED")
    private Boolean isdeleted;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 255)
    @Column(name = "PAYMENTREG")
    private String paymentreg;
    @Column(name = "PAYMENTPRICE")
    private Integer paymentprice;
    @Column(name = "PAYMENTDATE")
    @Temporal(TemporalType.DATE)
    private Date paymentdate;
    @Column(name = "PAIDWITHMONEY")
    private Integer paidwithmoney;
    @Column(name = "OPT_LOCK_VERSION")
    @Version
    private Integer optLockVersion;
    @JoinColumn(name = "PERSONID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Person personid;
    @JoinColumn(name = "TYPEID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Type typeid;

    public Payment() {
    }

    public Payment(Integer id) {
        this.id = id;
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

    public Integer getPaymentprice() {
        return paymentprice;
    }

    public void setPaymentprice(Integer paymentprice) {
        this.paymentprice = paymentprice;
    }

    public Date getPaymentdate() {
        return paymentdate;
    }

    public void setPaymentdate(Date paymentdate) {
        this.paymentdate = paymentdate;
    }

    public Integer getPaidwithmoney() {
        return paidwithmoney;
    }

    public void setPaidwithmoney(Integer paidwithmoney) {
        this.paidwithmoney = paidwithmoney;
    }


    public Integer getOptLockVersion() {
        return optLockVersion;
    }

    public void setOptLockVersion(Integer optLockVersion) {
        this.optLockVersion = optLockVersion;
    }

    public Person getPersonid() {
        return personid;
    }

    public void setPersonid(Person personid) {
        this.personid = personid;
    }

    public Type getTypeid() {
        return typeid;
    }

    public void setTypeid(Type typeid) {
        this.typeid = typeid;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.paymentreg);
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
        final Payment other = (Payment) obj;
        if (!Objects.equals(this.paymentreg, other.paymentreg)) {
            return false;
        }
        return true;
    }

   

    @Override
    public String toString() {
        return "lt.vu.mif.entities.Payment[ id=" + id + " ]";
    }

    public Boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
    }
    
}
