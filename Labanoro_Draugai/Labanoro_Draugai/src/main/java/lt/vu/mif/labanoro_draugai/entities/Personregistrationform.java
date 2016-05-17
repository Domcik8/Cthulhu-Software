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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Dominik Lisovski
 */
@Entity
@Table(name = "PERSONREGISTRATIONFORM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Personregistrationform.findAll", query = "SELECT p FROM Personregistrationform p"),
    @NamedQuery(name = "Personregistrationform.findById", query = "SELECT p FROM Personregistrationform p WHERE p.id = :id"),
    @NamedQuery(name = "Personregistrationform.findByInternalname", query = "SELECT p FROM Personregistrationform p WHERE p.internalname = :internalname"),
    @NamedQuery(name = "Personregistrationform.findByIsdeleted", query = "SELECT p FROM Personregistrationform p WHERE p.isdeleted = :isdeleted"),
    @NamedQuery(name = "Personregistrationform.findByOptlockversion", query = "SELECT p FROM Personregistrationform p WHERE p.optlockversion = :optlockversion")})
public class Personregistrationform implements Serializable {

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
    @Lob
    @Size(max = 32700)
    @Column(name = "FORMVALUE")
    private String formvalue;
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @Column(name = "OPTLOCKVERSION")
    private Integer optlockversion;
    @JoinColumn(name = "PERSONID", referencedColumnName = "ID")
    @OneToOne(optional = false)
    private Person personid;
    @JoinColumn(name = "TYPEID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Type typeid;

    public Personregistrationform() {
    }

    public Personregistrationform(Integer id) {
        this.id = id;
    }

    public Personregistrationform(Integer id, String internalname) {
        this.id = id;
        this.internalname = internalname;
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

    public String getFormvalue() {
        return formvalue;
    }

    public void setFormvalue(String formvalue) {
        this.formvalue = formvalue;
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

    public Person getPersonid() {
        return personid;
    }

    public void setPersonid(Person personid) {
        this.personid = personid;
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
        if (!(object instanceof Personregistrationform)) {
            return false;
        }
        Personregistrationform other = (Personregistrationform) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lt.vu.mif.labanoro_draugai.entities.Personregistrationform[ id=" + id + " ]";
    }
    
}
