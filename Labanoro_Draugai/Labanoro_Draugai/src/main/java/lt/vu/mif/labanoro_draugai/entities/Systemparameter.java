/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Dominik Lisovski
 */
@Entity
@Table(name = "SYSTEMPARAMETER")
@NamedQueries({
    @NamedQuery(name = "Systemparameter.findAll", query = "SELECT s FROM Systemparameter s"),
    @NamedQuery(name = "Systemparameter.findById", query = "SELECT s FROM Systemparameter s WHERE s.id = :id"),
    @NamedQuery(name = "Systemparameter.findByTitle", query = "SELECT s FROM Systemparameter s WHERE s.title = :title"),
    @NamedQuery(name = "Systemparameter.findByDescription", query = "SELECT s FROM Systemparameter s WHERE s.description = :description"),
    @NamedQuery(name = "Systemparameter.findByValue", query = "SELECT s FROM Systemparameter s WHERE s.value = :value"),
    @NamedQuery(name = "Systemparameter.findByIsdeleted", query = "SELECT s FROM Systemparameter s WHERE s.isdeleted = :isdeleted"),
    @NamedQuery(name = "Systemparameter.findByOptLockVersion", query = "SELECT s FROM Systemparameter s WHERE s.optLockVersion = :optLockVersion")})
public class Systemparameter implements Serializable {

    @Column(name = "ISDELETED")
    private Boolean isdeleted;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "INTERNALNAME")
    private String internalname;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 255)
    @Column(name = "TITLE")
    private String title;
    @Size(max = 255)
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "VALUE")
    private Integer value;
    @Version
    @Column(name = "OPT_LOCK_VERSION")
    private Integer optLockVersion;
    @JoinColumn(name = "TYPEID", referencedColumnName = "ID")
    @OneToOne(optional = false)
    private Type typeid;

    public Systemparameter() {
    }

    public Systemparameter(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }


    public Integer getOptLockVersion() {
        return optLockVersion;
    }

    public void setOptLockVersion(Integer optLockVersion) {
        this.optLockVersion = optLockVersion;
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
        hash = 53 * hash + Objects.hashCode(this.internalname);
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
        final Systemparameter other = (Systemparameter) obj;
        if (!Objects.equals(this.internalname, other.internalname)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lt.vu.mif.entities.Systemparameter[ id=" + id + " ]";
    }

    public String getInternalname() {
        return internalname;
    }

    public void setInternalname(String internalname) {
        this.internalname = internalname;
    }

    public Boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
    }
    
}
