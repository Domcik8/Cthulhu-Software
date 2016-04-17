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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "RESERVATION")
@NamedQueries({
    @NamedQuery(name = "Reservation.findAll", query = "SELECT r FROM Reservation r"),
    @NamedQuery(name = "Reservation.findById", query = "SELECT r FROM Reservation r WHERE r.id = :id"),
    @NamedQuery(name = "Reservation.findByReservationreg", query = "SELECT r FROM Reservation r WHERE r.reservationreg = :reservationreg"),
    @NamedQuery(name = "Reservation.findByStartdate", query = "SELECT r FROM Reservation r WHERE r.startdate = :startdate"),
    @NamedQuery(name = "Reservation.findByEnddate", query = "SELECT r FROM Reservation r WHERE r.enddate = :enddate"),
    @NamedQuery(name = "Reservation.findByIsdeleted", query = "SELECT r FROM Reservation r WHERE r.isdeleted = :isdeleted"),
    @NamedQuery(name = "Reservation.findByOptLockVersion", query = "SELECT r FROM Reservation r WHERE r.optLockVersion = :optLockVersion")})
public class Reservation implements Serializable {

    @Size(max = 255)
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "SEASONSTARTDATE")
    @Temporal(TemporalType.DATE)
    private Date seasonstartdate;
    @Column(name = "SEASONENDDATE")
    @Temporal(TemporalType.DATE)
    private Date seasonenddate;

    @Column(name = "ISDELETED")
    private Boolean isdeleted;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 255)
    @Column(name = "RESERVATIONREG")
    private String reservationreg;
    @Column(name = "STARTDATE")
    @Temporal(TemporalType.DATE)
    private Date startdate;
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
    @JoinColumn(name = "HOUSEID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private House houseid;
    @JoinColumn(name = "PERSONID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Person personid;
    @JoinColumn(name = "TYPEID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Type typeid;

    public Reservation() {
    }

    public Reservation(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public House getHouseid() {
        return houseid;
    }

    public void setHouseid(House houseid) {
        this.houseid = houseid;
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
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.reservationreg);
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
        final Reservation other = (Reservation) obj;
        if (!Objects.equals(this.reservationreg, other.reservationreg)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lt.vu.mif.entities.Reservation[ id=" + id + " ]";
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
    
}