/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.entities;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
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
@Table(name = "RESERVATION")
@NamedQueries({
    @NamedQuery(name = "Reservation.findAll", query = "SELECT r FROM Reservation r"),
    @NamedQuery(name = "Reservation.findById", query = "SELECT r FROM Reservation r WHERE r.id = :id"),
    @NamedQuery(name = "Reservation.findByReservationreg", query = "SELECT r FROM Reservation r WHERE r.reservationreg = :reservationreg"),
    @NamedQuery(name = "Reservation.findByStartdate", query = "SELECT r FROM Reservation r WHERE r.startdate = :startdate"),
    @NamedQuery(name = "Reservation.findByEnddate", query = "SELECT r FROM Reservation r WHERE r.enddate = :enddate"),
    @NamedQuery(name = "Reservation.findByOptLockVersion", query = "SELECT r FROM Reservation r WHERE r.optLockVersion = :optLockVersion")})
public class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "RESERVATIONREG")
    private String reservationreg;
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
    @Column(name = "OPT_LOCK_VERSION")
    @Version
    private Integer optLockVersion;
    @JoinTable(name = "MULTISELECTRESERVATIONTOSERVICE", joinColumns = {
        @JoinColumn(name = "PARENTID", referencedColumnName = "ID")}, inverseJoinColumns = {
        @JoinColumn(name = "CHILDID", referencedColumnName = "ID")})
    @ManyToMany
    private List<Service> serviceList;
    @JoinColumn(name = "HOUSEVERSIONID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private House houseversionid;
    @JoinColumn(name = "PERSONID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Objecttable personid;
    @JoinColumn(name = "HOUSEID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Objecttable houseid;
    @JoinColumn(name = "OBJECTID", referencedColumnName = "ID")
    @OneToOne(optional = false)
    private Objecttable objectid;
    @JoinColumn(name = "PERSONVERSIONID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Person personversionid;

    public Reservation() {
    }

    public Reservation(Long id) {
        this.id = id;
    }

    public Reservation(Long id, String reservationreg, Date startdate, Date enddate) {
        this.id = id;
        this.reservationreg = reservationreg;
        this.startdate = startdate;
        this.enddate = enddate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReservationreg() {
        return reservationreg;
    }

    public void setReservationreg(String reservationreg) {
        this.reservationreg = reservationreg;
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

    public House getHouseversionid() {
        return houseversionid;
    }

    public void setHouseversionid(House houseversionid) {
        this.houseversionid = houseversionid;
    }

    public Objecttable getPersonid() {
        return personid;
    }

    public void setPersonid(Objecttable personid) {
        this.personid = personid;
    }

    public Objecttable getHouseid() {
        return houseid;
    }

    public void setHouseid(Objecttable houseid) {
        this.houseid = houseid;
    }

    public Objecttable getObjectid() {
        return objectid;
    }

    public void setObjectid(Objecttable objectid) {
        this.objectid = objectid;
    }

    public Person getPersonversionid() {
        return personversionid;
    }

    public void setPersonversionid(Person personversionid) {
        this.personversionid = personversionid;
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
        if (!(object instanceof Reservation)) {
            return false;
        }
        Reservation other = (Reservation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lt.vu.mif.entities.Reservation[ id=" + id + " ]";
    }
    
}
