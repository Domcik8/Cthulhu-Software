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
    @NamedQuery(name = "Recommendation.findByRecommendationdate", query = "SELECT r FROM Recommendation r WHERE r.recommendationdate = :recommendationdate"),
    @NamedQuery(name = "Recommendation.findByOptlockversion", query = "SELECT r FROM Recommendation r WHERE r.optlockversion = :optlockversion")})
public class Recommendation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "RECOMMENDATIONDATE")
    @Temporal(TemporalType.DATE)
    private Date recommendationdate;
    @Column(name = "OPTLOCKVERSION")
    private Integer optlockversion;
    @JoinColumn(name = "RECOMMENDERID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Person recommenderid;
    @JoinColumn(name = "RECOMMENDEDID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Person recommendedid;
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

    public Date getRecommendationdate() {
        return recommendationdate;
    }

    public void setRecommendationdate(Date recommendationdate) {
        this.recommendationdate = recommendationdate;
    }

    public Integer getOptlockversion() {
        return optlockversion;
    }

    public void setOptlockversion(Integer optlockversion) {
        this.optlockversion = optlockversion;
    }

    public Person getRecommenderid() {
        return recommenderid;
    }

    public void setRecommenderid(Person recommenderid) {
        this.recommenderid = recommenderid;
    }

    public Person getRecommendedid() {
        return recommendedid;
    }

    public void setRecommendedid(Person recommendedid) {
        this.recommendedid = recommendedid;
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
