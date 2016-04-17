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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Dominik Lisovski
 */
@Entity
@Table(name = "HOUSEPICTURES")
@NamedQueries({
    @NamedQuery(name = "Housepictures.findAll", query = "SELECT h FROM Housepictures h"),
    @NamedQuery(name = "Housepictures.findById", query = "SELECT h FROM Housepictures h WHERE h.id = :id"),
    @NamedQuery(name = "Housepictures.findByPath", query = "SELECT h FROM Housepictures h WHERE h.path = :path"),
    @NamedQuery(name = "Housepictures.findByIsdeleted", query = "SELECT h FROM Housepictures h WHERE h.isdeleted = :isdeleted"),
    @NamedQuery(name = "Housepictures.findByOptLockVersion", query = "SELECT h FROM Housepictures h WHERE h.optLockVersion = :optLockVersion")})
public class Housepictures implements Serializable {

    @Column(name = "SEQUENCE")
    private Integer sequence;
    @Column(name = "ISDELETED")
    private Boolean isdeleted;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "PATH")
    private String path;
    @Column(name = "OPT_LOCK_VERSION")
    @Version
    private Integer optLockVersion;
    @JoinColumn(name = "HOUSEID", referencedColumnName = "ID")
    @ManyToOne
    private House houseid;
    @JoinColumn(name = "TYPEID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Type typeid;

    public Housepictures() {
    }

    public Housepictures(Integer id) {
        this.id = id;
    }

    public Housepictures(Integer id, String path) {
        this.id = id;
        this.path = path;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public Integer getOptLockVersion() {
        return optLockVersion;
    }

    public void setOptLockVersion(Integer optLockVersion) {
        this.optLockVersion = optLockVersion;
    }

    public House getHouseid() {
        return houseid;
    }

    public void setHouseid(House houseid) {
        this.houseid = houseid;
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
        hash = 41 * hash + Objects.hashCode(this.path);
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
        final Housepictures other = (Housepictures) obj;
        if (!Objects.equals(this.path, other.path)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lt.vu.mif.entities.Housepictures[ id=" + id + " ]";
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
    }
    
}
