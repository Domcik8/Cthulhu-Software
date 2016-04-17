/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.Size;

/**
 *
 * @author Dominik Lisovski
 */
@Entity
@Table(name = "SERVICE")
@NamedQueries({
    @NamedQuery(name = "Service.findAll", query = "SELECT s FROM Service s"),
    @NamedQuery(name = "Service.findById", query = "SELECT s FROM Service s WHERE s.id = :id"),
    @NamedQuery(name = "Service.findByServicereg", query = "SELECT s FROM Service s WHERE s.servicereg = :servicereg"),
    @NamedQuery(name = "Service.findByIsactive", query = "SELECT s FROM Service s WHERE s.isactive = :isactive"),
    @NamedQuery(name = "Service.findByStartdate", query = "SELECT s FROM Service s WHERE s.startdate = :startdate"),
    @NamedQuery(name = "Service.findByEnddate", query = "SELECT s FROM Service s WHERE s.enddate = :enddate"),
    @NamedQuery(name = "Service.findByWeekprice", query = "SELECT s FROM Service s WHERE s.weekprice = :weekprice"),
    @NamedQuery(name = "Service.findByNumberofplaces", query = "SELECT s FROM Service s WHERE s.numberofplaces = :numberofplaces"),
    @NamedQuery(name = "Service.findByIsdeleted", query = "SELECT s FROM Service s WHERE s.isdeleted = :isdeleted"),
    @NamedQuery(name = "Service.findByOptLockVersion", query = "SELECT s FROM Service s WHERE s.optLockVersion = :optLockVersion")})
public class Service implements Serializable {

    @Size(max = 255)
    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ISACTIVE")
    private Boolean isactive;
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @OneToMany(mappedBy = "serviceid")
    private List<Servicepictures> servicepicturesList;

    @Size(max = 255)
    @Column(name = "TITLE")
    private String title;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 255)
    @Column(name = "SERVICEREG")
    private String servicereg;
    @Column(name = "STARTDATE")
    @Temporal(TemporalType.DATE)
    private Date startdate;
    @Column(name = "ENDDATE")
    @Temporal(TemporalType.DATE)
    private Date enddate;
    @Column(name = "WEEKPRICE")
    private Integer weekprice;
    @Column(name = "NUMBEROFPLACES")
    private Integer numberofplaces;
    @Version
    @Column(name = "OPT_LOCK_VERSION")
    private Integer optLockVersion;
    @ManyToMany(mappedBy = "serviceList")
    private List<House> houseList;
    @ManyToMany(mappedBy = "serviceList")
    private List<Reservation> reservationList;
    @JoinColumn(name = "TYPEID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Type typeid;

    public Service() {
    }

    public Service(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getServicereg() {
        return servicereg;
    }

    public void setServicereg(String servicereg) {
        this.servicereg = servicereg;
    }


    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
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


    public Integer getOptLockVersion() {
        return optLockVersion;
    }

    public void setOptLockVersion(Integer optLockVersion) {
        this.optLockVersion = optLockVersion;
    }

    public List<House> getHouseList() {
        return houseList;
    }

    public void setHouseList(List<House> houseList) {
        this.houseList = houseList;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.servicereg);
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
        final Service other = (Service) obj;
        if (!Objects.equals(this.servicereg, other.servicereg)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lt.vu.mif.entities.Service[ id=" + id + " ]";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getIsactive() {
        return isactive;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
    }

    public Boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    public List<Servicepictures> getServicepicturesList() {
        return servicepicturesList;
    }

    public void setServicepicturesList(List<Servicepictures> servicepicturesList) {
        this.servicepicturesList = servicepicturesList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
