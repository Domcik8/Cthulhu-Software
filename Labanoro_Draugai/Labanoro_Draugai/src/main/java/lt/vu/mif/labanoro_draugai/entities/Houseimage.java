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
@Table(name = "HOUSEIMAGE")
@NamedQueries({
    @NamedQuery(name = "Houseimage.findAll", query = "SELECT h FROM Houseimage h"),
    @NamedQuery(name = "Houseimage.findById", query = "SELECT h FROM Houseimage h WHERE h.id = :id"),
    @NamedQuery(name = "Houseimage.findByInternalname", query = "SELECT h FROM Houseimage h WHERE h.internalname = :internalname"),
    @NamedQuery(name = "Houseimage.findBySequence", query = "SELECT h FROM Houseimage h WHERE h.sequence = :sequence"),
    @NamedQuery(name = "Houseimage.findByMimetype", query = "SELECT h FROM Houseimage h WHERE h.mimetype = :mimetype"),
    @NamedQuery(name = "Houseimage.findByDescription", query = "SELECT h FROM Houseimage h WHERE h.description = :description"),
    @NamedQuery(name = "Houseimage.findByIsdeleted", query = "SELECT h FROM Houseimage h WHERE h.isdeleted = :isdeleted"),
    @NamedQuery(name = "Houseimage.findByOptLockVersion", query = "SELECT h FROM Houseimage h WHERE h.optLockVersion = :optLockVersion")})
public class Houseimage implements Serializable {

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
    @Size(max = 255)
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @Column(name = "OPT_LOCK_VERSION")
    private Integer optLockVersion;
    @JoinColumn(name = "HOUSEID", referencedColumnName = "ID")
    @ManyToOne
    private House houseid;
    @JoinColumn(name = "TYPEID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Type typeid;

    public Houseimage() {
    }

    public Houseimage(Integer id) {
        this.id = id;
    }

    public Houseimage(Integer id, String internalname, Serializable image, String mimetype) {
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
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Houseimage)) {
            return false;
        }
        Houseimage other = (Houseimage) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lt.vu.mif.labanoro_draugai.entities.Houseimage[ id=" + id + " ]";
    }
    
}
