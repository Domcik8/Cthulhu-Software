/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Dominik Lisovski
 */
@Entity
@Table(name = "HOUSE")
@NamedQueries({
    @NamedQuery(name = "House.findAll", query = "SELECT h FROM House h"),
    @NamedQuery(name = "House.findById", query = "SELECT h FROM House h WHERE h.id = :id"),
    @NamedQuery(name = "House.findByTitle", query = "SELECT h FROM House h WHERE h.title = :title"),
    @NamedQuery(name = "House.findByDescription", query = "SELECT h FROM House h WHERE h.description = :description"),
    @NamedQuery(name = "House.findByHousereg", query = "SELECT h FROM House h WHERE h.housereg = :housereg"),
    @NamedQuery(name = "House.findByAddress", query = "SELECT h FROM House h WHERE h.address = :address"),
    @NamedQuery(name = "House.findByIsactive", query = "SELECT h FROM House h WHERE h.isactive = :isactive"),
    @NamedQuery(name = "House.findBySeasonstartdate", query = "SELECT h FROM House h WHERE h.seasonstartdate = :seasonstartdate"),
    @NamedQuery(name = "House.findBySeasonenddate", query = "SELECT h FROM House h WHERE h.seasonenddate = :seasonenddate"),
    @NamedQuery(name = "House.findByWeekprice", query = "SELECT h FROM House h WHERE h.weekprice = :weekprice"),
    @NamedQuery(name = "House.findByNumberofplaces", query = "SELECT h FROM House h WHERE h.numberofplaces = :numberofplaces"),
    @NamedQuery(name = "House.findByIsdeleted", query = "SELECT h FROM House h WHERE h.isdeleted = :isdeleted"),
    @NamedQuery(name = "House.findByOptLockVersion", query = "SELECT h FROM House h WHERE h.optLockVersion = :optLockVersion")})
public class House implements Serializable {

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
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "HOUSEREG")
    private String housereg;
    @Size(max = 255)
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "ISACTIVE")
    private Boolean isactive;
    @Column(name = "SEASONSTARTDATE")
    @Temporal(TemporalType.DATE)
    private Date seasonstartdate;
    @Column(name = "SEASONENDDATE")
    @Temporal(TemporalType.DATE)
    private Date seasonenddate;
    @Column(name = "WEEKPRICE")
    private Integer weekprice;
    @Column(name = "NUMBEROFPLACES")
    private Integer numberofplaces;
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @Column(name = "OPT_LOCK_VERSION")
    private Integer optLockVersion;
    @JoinTable(name = "MULTISELECTHOUSETOSERVICE", joinColumns = {
        @JoinColumn(name = "PARENTID", referencedColumnName = "ID")}, inverseJoinColumns = {
        @JoinColumn(name = "CHILDID", referencedColumnName = "ID")})
    @ManyToMany
    private List<Service> serviceList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "houseid")
    private List<Reservation> reservationList;
    @JoinColumn(name = "TYPEID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Type typeid;
    @OneToMany(mappedBy = "houseid")
    private List<Housepictures> housepicturesList;

    public House() {
    }

    public House(Integer id) {
        this.id = id;
    }

    public House(Integer id, String housereg) {
        this.id = id;
        this.housereg = housereg;
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

    public String getHousereg() {
        return housereg;
    }

    public void setHousereg(String housereg) {
        this.housereg = housereg;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getIsactive() {
        return isactive;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
    }

    public Date getSeasonstartdate() {
        return seasonstartdate;
    }

    public void setSeasonstartdate(Date seasonstartdate) {
        this.seasonstartdate = seasonstartdate;
    }

    public Date getSeasonenddate() {
        return seasonenddate;
    }

    public void setSeasonenddate(Date seasonenddate) {
        this.seasonenddate = seasonenddate;
    }

    public Integer getWeekprice() {
        return weekprice;
    }

    public void setWeekprice(Integer weekprice) {
        this.weekprice = weekprice;
    }

    public Integer getNumberofplaces() {
        return numberofplaces;
    }

    public void setNumberofplaces(Integer numberofplaces) {
        this.numberofplaces = numberofplaces;
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

    public List<Service> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<Service> serviceList) {
        this.serviceList = serviceList;
    }

    public List<Reservation> getReservationList() {
        return reservationList;
    }

    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }

    public Type getTypeid() {
        return typeid;
    }

    public void setTypeid(Type typeid) {
        this.typeid = typeid;
    }

    public List<Housepictures> getHousepicturesList() {
        return housepicturesList;
    }

    public void setHousepicturesList(List<Housepictures> housepicturesList) {
        this.housepicturesList = housepicturesList;
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
        if (!(object instanceof House)) {
            return false;
        }
        House other = (House) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lt.vu.mif.labanoro_draugai.entities.House[ id=" + id + " ]";
    }
    
}
