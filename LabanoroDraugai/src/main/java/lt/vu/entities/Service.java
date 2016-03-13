/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
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
    @NamedQuery(name = "Service.findByOptLockVersion", query = "SELECT s FROM Service s WHERE s.optLockVersion = :optLockVersion")})
public class Service implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "SERVICEREG")
    private String servicereg;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISACTIVE")
    private int isactive;
    @Basic(optional = false)
    @NotNull
    @Column(name = "STARTDATE")
    @Temporal(TemporalType.DATE)
    private Date startdate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ENDDATE")
    @Temporal(TemporalType.DATE)
    private Date enddate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "WEEKPRICE")
    private int weekprice;
    @Column(name = "NUMBEROFPLACES")
    private Integer numberofplaces;
    @Column(name = "OPT_LOCK_VERSION")
    private Integer optLockVersion;
    @ManyToMany(mappedBy = "serviceList")
    private List<House> houseList;
    @ManyToMany(mappedBy = "serviceList")
    private List<Reservation> reservationList;
    @JoinColumn(name = "OBJECTID", referencedColumnName = "ID")
    @OneToOne(optional = false)
    private Objecttable objectid;

    public Service() {
    }

    public Service(Long id) {
        this.id = id;
    }

    public Service(Long id, String servicereg, int isactive, Date startdate, Date enddate, int weekprice) {
        this.id = id;
        this.servicereg = servicereg;
        this.isactive = isactive;
        this.startdate = startdate;
        this.enddate = enddate;
        this.weekprice = weekprice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServicereg() {
        return servicereg;
    }

    public void setServicereg(String servicereg) {
        this.servicereg = servicereg;
    }

    public int getIsactive() {
        return isactive;
    }

    public void setIsactive(int isactive) {
        this.isactive = isactive;
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

    public int getWeekprice() {
        return weekprice;
    }

    public void setWeekprice(int weekprice) {
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

    public Objecttable getObjectid() {
        return objectid;
    }

    public void setObjectid(Objecttable objectid) {
        this.objectid = objectid;
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
        if (!(object instanceof Service)) {
            return false;
        }
        Service other = (Service) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lt.vu.entities.Service[ id=" + id + " ]";
    }
    
}
