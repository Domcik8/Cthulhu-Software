/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.entities;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Dominik Lisovski
 */
@Entity
@Table(name = "SYSTEMPARAMETER")
@NamedQueries({
    @NamedQuery(name = "Systemparameter.findAll", query = "SELECT s FROM Systemparameter s WHERE (s.isdeleted IS NULL OR s.isdeleted = FALSE)"),
    @NamedQuery(name = "Systemparameter.findById", query = "SELECT s FROM Systemparameter s WHERE (s.isdeleted IS NULL OR s.isdeleted = FALSE) AND s.id = :id"),
    @NamedQuery(name = "Systemparameter.findByInternalname", query = "SELECT s FROM Systemparameter s WHERE (s.isdeleted IS NULL OR s.isdeleted = FALSE) AND s.internalname = :internalname"),
    @NamedQuery(name = "Systemparameter.findByTitle", query = "SELECT s FROM Systemparameter s WHERE (s.isdeleted IS NULL OR s.isdeleted = FALSE) AND s.title = :title"),
    @NamedQuery(name = "Systemparameter.findByValue", query = "SELECT s FROM Systemparameter s WHERE (s.isdeleted IS NULL OR s.isdeleted = FALSE) AND s.value = :value"),
    @NamedQuery(name = "Systemparameter.findByDescription", query = "SELECT s FROM Systemparameter s WHERE (s.isdeleted IS NULL OR s.isdeleted = FALSE) AND s.description = :description"),
    @NamedQuery(name = "Systemparameter.findByIsdeleted", query = "SELECT s FROM Systemparameter s WHERE s.isdeleted = :isdeleted")})
public class Systemparameter implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "INTERNALNAME")
    private String internalname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "TITLE")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "VALUE")
    private String value;
    @Size(max = 255)
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @Column(name = "OPTLOCKVERSION")
    private Integer optlockversion;
    @JoinColumn(name = "TYPEID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Type typeid;

    public Systemparameter() {
    }

    public Systemparameter(Integer id) {
        this.id = id;
    }

    public Systemparameter(Integer id, String internalname, String title, String value) {
        this.id = id;
        this.internalname = internalname;
        this.title = title;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInternalname() {
        return internalname;
    }

    public void setInternalname(String internalname) {
        this.internalname = internalname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        if (!(object instanceof Systemparameter)) {
            return false;
        }
        Systemparameter other = (Systemparameter) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lt.vu.mif.labanoro_draugai.entities.Systemparameter[ id=" + id + " ]";
    }
    
}
