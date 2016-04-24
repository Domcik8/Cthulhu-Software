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
@Table(name = "GROUPVIEW")
@NamedQueries({
    @NamedQuery(name = "Groupview.findAll", query = "SELECT g FROM Groupview g"),
    @NamedQuery(name = "Groupview.findById", query = "SELECT g FROM Groupview g WHERE g.id = :id"),
    @NamedQuery(name = "Groupview.findByInternalname", query = "SELECT g FROM Groupview g WHERE g.internalname = :internalname"),
    @NamedQuery(name = "Groupview.findByTitle", query = "SELECT g FROM Groupview g WHERE g.title = :title"),
    @NamedQuery(name = "Groupview.findByDescription", query = "SELECT g FROM Groupview g WHERE g.description = :description"),
    @NamedQuery(name = "Groupview.findByIsdeleted", query = "SELECT g FROM Groupview g WHERE g.isdeleted = :isdeleted"),
    @NamedQuery(name = "Groupview.findByOptLockVersion", query = "SELECT g FROM Groupview g WHERE g.optLockVersion = :optLockVersion")})
public class Groupview implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private int id;
    @Size(max = 255)
    @Column(name = "INTERNALNAME")
    private String internalname;
    @Size(max = 255)
    @Column(name = "TITLE")
    private String title;
    @Size(max = 255)
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @Column(name = "OPT_LOCK_VERSION")
    private Integer optLockVersion;

    public Groupview() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
    
}
