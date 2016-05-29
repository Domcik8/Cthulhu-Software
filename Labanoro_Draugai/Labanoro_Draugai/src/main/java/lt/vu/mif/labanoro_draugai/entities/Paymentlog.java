/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "PAYMENTLOG")
@NamedQueries({
    @NamedQuery(name = "Paymentlog.findAll", query = "SELECT p FROM Paymentlog p"),
    @NamedQuery(name = "Paymentlog.findById", query = "SELECT p FROM Paymentlog p WHERE p.id = :id"),
    @NamedQuery(name = "Paymentlog.findByPersonemail", query = "SELECT p FROM Paymentlog p WHERE p.personemail = :personemail"),
    @NamedQuery(name = "Paymentlog.findByPersontype", query = "SELECT p FROM Paymentlog p WHERE p.persontype = :persontype"),
    @NamedQuery(name = "Paymentlog.findByDate", query = "SELECT p FROM Paymentlog p WHERE p.date = :date"),
    @NamedQuery(name = "Paymentlog.findByMethod", query = "SELECT p FROM Paymentlog p WHERE p.method = :method"),
    @NamedQuery(name = "Paymentlog.findByOptlockversion", query = "SELECT p FROM Paymentlog p WHERE p.optlockversion = :optlockversion")})
public class Paymentlog implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "PERSONEMAIL")
    private String personemail;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "PERSONTYPE")
    private String persontype;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "METHOD")
    private String method;
    @Version
    @Column(name = "OPTLOCKVERSION")
    private Integer optlockversion;

    public Paymentlog() {
    }

    public Paymentlog(Integer id) {
        this.id = id;
    }

    public Paymentlog(Integer id, String personemail, String persontype, Date date, String method) {
        this.id = id;
        this.personemail = personemail;
        this.persontype = persontype;
        this.date = date;
        this.method = method;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPersonemail() {
        return personemail;
    }

    public void setPersonemail(String personemail) {
        this.personemail = personemail;
    }

    public String getPersontype() {
        return persontype;
    }

    public void setPersontype(String persontype) {
        this.persontype = persontype;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Integer getOptlockversion() {
        return optlockversion;
    }

    public void setOptlockversion(Integer optlockversion) {
        this.optlockversion = optlockversion;
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
        if (!(object instanceof Paymentlog)) {
            return false;
        }
        Paymentlog other = (Paymentlog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lt.vu.mif.labanoro_draugai.entities.Paymentlog[ id=" + id + " ]";
    }
    
}
