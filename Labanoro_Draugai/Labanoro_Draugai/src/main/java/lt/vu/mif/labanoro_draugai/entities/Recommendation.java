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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Dominik Lisovski
 */
@Entity
@Table(name = "RECOMMENDATION")
@NamedQueries({
    @NamedQuery(name = "Recommendation.findAll", query = "SELECT r FROM Recommendation r"),
    @NamedQuery(name = "Recommendation.findById", query = "SELECT r FROM Recommendation r WHERE r.id = :id"),
    @NamedQuery(name = "Recommendation.findByRecomendationdate", query = "SELECT r FROM Recommendation r WHERE r.recomendationdate = :recomendationdate"),
    @NamedQuery(name = "Recommendation.findByIsdeleted", query = "SELECT r FROM Recommendation r WHERE r.isdeleted = :isdeleted"),
    @NamedQuery(name = "Recommendation.findByOptlockversion", query = "SELECT r FROM Recommendation r WHERE r.optlockversion = :optlockversion")})
public class Recommendation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "RECOMENDATIONDATE")
    @Temporal(TemporalType.DATE)
    private Date recomendationdate;
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @Column(name = "OPTLOCKVERSION")
    private Integer optlockversion;
    @JoinColumn(name = "RECOMENDERID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Person recomenderid;
    @JoinColumn(name = "RECOMENDEDID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Person recomendedid;
    @JoinColumn(name = "TYPEID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Type typeid;

    public Recommendation() {
    }

    public Recommendation(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getRecomendationdate() {
        return recomendationdate;
    }

    public void setRecomendationdate(Date recomendationdate) {
        this.recomendationdate = recomendationdate;
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

    public Person getRecomenderid() {
        return recomenderid;
    }

    public void setRecomenderid(Person recomenderid) {
        this.recomenderid = recomenderid;
    }

    public Person getRecomendedid() {
        return recomendedid;
    }

    public void setRecomendedid(Person recomendedid) {
        this.recomendedid = recomendedid;
    }

    public Type getTypeid() {
        return typeid;
    }

    public void setTypeid(Type typeid) {
        this.typeid = typeid;
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
        if (!(object instanceof Recommendation)) {
            return false;
        }
        Recommendation other = (Recommendation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lt.vu.mif.labanoro_draugai.entities.Recommendation[ id=" + id + " ]";
    }
    
}
