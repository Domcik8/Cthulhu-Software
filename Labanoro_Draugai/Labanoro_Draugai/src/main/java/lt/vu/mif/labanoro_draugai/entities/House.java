/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.entities;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Dominik Lisovski
 */
@Entity
@Table(name = "HOUSE")
@NamedQueries({
    @NamedQuery(name = "House.findAll", query = "SELECT h FROM House h WHERE (h.isdeleted IS NULL OR h.isdeleted = FALSE)"),
    @NamedQuery(name = "House.findById", query = "SELECT h FROM House h WHERE (h.isdeleted IS NULL OR h.isdeleted = FALSE) AND h.id = :id"),
    @NamedQuery(name = "House.findByTitle", query = "SELECT h FROM House h WHERE (h.isdeleted IS NULL OR h.isdeleted = FALSE) AND h.title = :title"),
    @NamedQuery(name = "House.findByDescription", query = "SELECT h FROM House h WHERE (h.isdeleted IS NULL OR h.isdeleted = FALSE) AND h.description = :description"),
    @NamedQuery(name = "House.findByHousereg", query = "SELECT h FROM House h WHERE (h.isdeleted IS NULL OR h.isdeleted = FALSE) AND h.housereg = :housereg"),
    @NamedQuery(name = "House.findByAddress", query = "SELECT h FROM House h WHERE (h.isdeleted IS NULL OR h.isdeleted = FALSE) AND h.address = :address"),
    @NamedQuery(name = "House.findByIsactive", query = "SELECT h FROM House h WHERE (h.isdeleted IS NULL OR h.isdeleted = FALSE) AND h.isactive = :isactive"),
    @NamedQuery(name = "House.findBySeasonstartdate", query = "SELECT h FROM House h WHERE (h.isdeleted IS NULL OR h.isdeleted = FALSE) AND h.seasonstartdate = :seasonstartdate"),
    @NamedQuery(name = "House.findBySeasonenddate", query = "SELECT h FROM House h WHERE (h.isdeleted IS NULL OR h.isdeleted = FALSE) AND h.seasonenddate = :seasonenddate"),
    @NamedQuery(name = "House.findByWeekprice", query = "SELECT h FROM House h WHERE (h.isdeleted IS NULL OR h.isdeleted = FALSE) AND h.weekprice = :weekprice"),
    @NamedQuery(name = "House.findByNumberofplaces", query = "SELECT h FROM House h WHERE (h.isdeleted IS NULL OR h.isdeleted = FALSE) AND h.numberofplaces = :numberofplaces"),
    @NamedQuery(name = "House.findByIsdeleted", query = "SELECT h FROM House h WHERE h.isdeleted = :isdeleted")})
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
    @Temporal(TemporalType.TIMESTAMP)
    private Date seasonstartdate;
    @Column(name = "SEASONENDDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date seasonenddate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "WEEKPRICE")
    private BigDecimal weekprice;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NUMBEROFPLACES")
    private int numberofplaces;
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @Column(name = "OPTLOCKVERSION")
    @Version
    private Integer optlockversion;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "houseid")
    private List<Houseimage> houseimageList;

    public House() {
    }

    public House(Integer id) {
        this.id = id;
    }

    public House(Integer id, String housereg, int numberofplaces) {
        this.id = id;
        this.housereg = housereg;
        this.numberofplaces = numberofplaces;
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

    public BigDecimal getWeekprice() {
        return weekprice;
    }

    public void setWeekprice(BigDecimal weekprice) {
        this.weekprice = weekprice;
    }

    public int getNumberofplaces() {
        return numberofplaces;
    }

    public void setNumberofplaces(int numberofplaces) {
        this.numberofplaces = numberofplaces;
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

    public List<Houseimage> getHouseimageList() {
        return houseimageList;
    }

    public void setHouseimageList(List<Houseimage> houseimageList) {
        this.houseimageList = houseimageList;
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
