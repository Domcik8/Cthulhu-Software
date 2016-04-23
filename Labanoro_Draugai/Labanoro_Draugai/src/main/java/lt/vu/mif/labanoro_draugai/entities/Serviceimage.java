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
import javax.persistence.Lob;
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
@Table(name = "SERVICEIMAGE")
@NamedQueries({
    @NamedQuery(name = "Serviceimage.findAll", query = "SELECT s FROM Serviceimage s"),
    @NamedQuery(name = "Serviceimage.findById", query = "SELECT s FROM Serviceimage s WHERE s.id = :id"),
    @NamedQuery(name = "Serviceimage.findByInternalname", query = "SELECT s FROM Serviceimage s WHERE s.internalname = :internalname"),
    @NamedQuery(name = "Serviceimage.findBySequence", query = "SELECT s FROM Serviceimage s WHERE s.sequence = :sequence"),
    @NamedQuery(name = "Serviceimage.findByMimetype", query = "SELECT s FROM Serviceimage s WHERE s.mimetype = :mimetype"),
    @NamedQuery(name = "Serviceimage.findByIsdeleted", query = "SELECT s FROM Serviceimage s WHERE s.isdeleted = :isdeleted"),
    @NamedQuery(name = "Serviceimage.findByDescription", query = "SELECT s FROM Serviceimage s WHERE s.description = :description"),
    @NamedQuery(name = "Serviceimage.findByOptLockVersion", query = "SELECT s FROM Serviceimage s WHERE s.optLockVersion = :optLockVersion")})
public class Serviceimage implements Serializable {

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
    @Column(name = "SEQUENCE")
    private Integer sequence;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "IMAGE")
    private Serializable image;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "MIMETYPE")
    private String mimetype;
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @Size(max = 255)
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "OPT_LOCK_VERSION")
    private Integer optLockVersion;
    @JoinColumn(name = "SERVICEID", referencedColumnName = "ID")
    @ManyToOne
    private Service serviceid;
    @JoinColumn(name = "TYPEID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Type typeid;

    public Serviceimage() {
    }

    public Serviceimage(Integer id) {
        this.id = id;
    }

    public Serviceimage(Integer id, String internalname, Serializable image, String mimetype) {
        this.id = id;
        this.internalname = internalname;
        this.image = image;
        this.mimetype = mimetype;
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

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Serializable getImage() {
        return image;
    }

    public void setImage(Serializable image) {
        this.image = image;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public Boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        if (!(object instanceof Serviceimage)) {
            return false;
        }
        Serviceimage other = (Serviceimage) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lt.vu.mif.labanoro_draugai.entities.Serviceimage[ id=" + id + " ]";
    }
    
}
