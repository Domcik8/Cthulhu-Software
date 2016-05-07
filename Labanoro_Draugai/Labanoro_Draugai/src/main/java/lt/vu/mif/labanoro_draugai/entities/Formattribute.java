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
@Table(name = "FORMATTRIBUTE")
@NamedQueries({
    @NamedQuery(name = "Formattribute.findAll", query = "SELECT f FROM Formattribute f"),
    @NamedQuery(name = "Formattribute.findById", query = "SELECT f FROM Formattribute f WHERE f.id = :id"),
    @NamedQuery(name = "Formattribute.findByInternalname", query = "SELECT f FROM Formattribute f WHERE f.internalname = :internalname"),
    @NamedQuery(name = "Formattribute.findByName", query = "SELECT f FROM Formattribute f WHERE f.name = :name"),
    @NamedQuery(name = "Formattribute.findByListitems", query = "SELECT f FROM Formattribute f WHERE f.listitems = :listitems"),
    @NamedQuery(name = "Formattribute.findByIsrequired", query = "SELECT f FROM Formattribute f WHERE f.isrequired = :isrequired"),
    @NamedQuery(name = "Formattribute.findByDescription", query = "SELECT f FROM Formattribute f WHERE f.description = :description"),
    @NamedQuery(name = "Formattribute.findByIsdeleted", query = "SELECT f FROM Formattribute f WHERE f.isdeleted = :isdeleted"),
    @NamedQuery(name = "Formattribute.findByOptlockversion", query = "SELECT f FROM Formattribute f WHERE f.optlockversion = :optlockversion")})
public class Formattribute implements Serializable {

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
    @Column(name = "NAME")
    private String name;
    @Size(max = 255)
    @Column(name = "LISTITEMS")
    private String listitems;
    @Column(name = "ISREQUIRED")
    private Boolean isrequired;
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

    public Formattribute() {
    }

    public Formattribute(Integer id) {
        this.id = id;
    }

    public Formattribute(Integer id, String internalname, String name) {
        this.id = id;
        this.internalname = internalname;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getListitems() {
        return listitems;
    }

    public void setListitems(String listitems) {
        this.listitems = listitems;
    }

    public Boolean getIsrequired() {
        return isrequired;
    }

    public void setIsrequired(Boolean isrequired) {
        this.isrequired = isrequired;
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
        if (!(object instanceof Formattribute)) {
            return false;
        }
        Formattribute other = (Formattribute) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lt.vu.mif.labanoro_draugai.entities.Formattribute[ id=" + id + " ]";
    }
    
}
