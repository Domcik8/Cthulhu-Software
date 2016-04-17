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
@Table(name = "SERVICEPICTURES")
@NamedQueries({
    @NamedQuery(name = "Servicepictures.findAll", query = "SELECT s FROM Servicepictures s"),
    @NamedQuery(name = "Servicepictures.findById", query = "SELECT s FROM Servicepictures s WHERE s.id = :id"),
    @NamedQuery(name = "Servicepictures.findBySequence", query = "SELECT s FROM Servicepictures s WHERE s.sequence = :sequence"),
    @NamedQuery(name = "Servicepictures.findByPath", query = "SELECT s FROM Servicepictures s WHERE s.path = :path"),
    @NamedQuery(name = "Servicepictures.findByIsdeleted", query = "SELECT s FROM Servicepictures s WHERE s.isdeleted = :isdeleted"),
    @NamedQuery(name = "Servicepictures.findByOptLockVersion", query = "SELECT s FROM Servicepictures s WHERE s.optLockVersion = :optLockVersion")})
public class Servicepictures implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "SEQUENCE")
    private Integer sequence;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "PATH")
    private String path;
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @Column(name = "OPT_LOCK_VERSION")
    private Integer optLockVersion;
    @JoinColumn(name = "SERVICEID", referencedColumnName = "ID")
    @ManyToOne
    private Service serviceid;
    @JoinColumn(name = "TYPEID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Type typeid;

    public Servicepictures() {
    }

    public Servicepictures(Integer id) {
        this.id = id;
    }

    public Servicepictures(Integer id, String path) {
        this.id = id;
        this.path = path;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    public Integer getOptLockVersion() {
        return optLockVersion;
    }

    public void setOptLockVersion(Integer optLockVersion) {
        this.optLockVersion = optLockVersion;
    }

    public Service getServiceid() {
        return serviceid;
    }

    public void setServiceid(Service serviceid) {
        this.serviceid = serviceid;
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
        if (!(object instanceof Servicepictures)) {
            return false;
        }
        Servicepictures other = (Servicepictures) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lt.vu.mif.labanoro_draugai.entities.Servicepictures[ id=" + id + " ]";
    }
    
}
