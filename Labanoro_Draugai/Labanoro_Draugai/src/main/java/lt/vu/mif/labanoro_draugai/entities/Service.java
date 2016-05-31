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
@Table(name = "SERVICE")
@NamedQueries({
    @NamedQuery(name = "Service.findAll", query = "SELECT s FROM Service s WHERE (s.isdeleted IS NULL OR s.isdeleted = FALSE)"),
    @NamedQuery(name = "Service.findById", query = "SELECT s FROM Service s WHERE (s.isdeleted IS NULL OR s.isdeleted = FALSE) AND s.id = :id"),
    @NamedQuery(name = "Service.findByTitle", query = "SELECT s FROM Service s WHERE (s.isdeleted IS NULL OR s.isdeleted = FALSE) AND s.title = :title"),
    @NamedQuery(name = "Service.findByDescription", query = "SELECT s FROM Service s WHERE (s.isdeleted IS NULL OR s.isdeleted = FALSE) AND s.description = :description"),
    @NamedQuery(name = "Service.findByServicereg", query = "SELECT s FROM Service s WHERE (s.isdeleted IS NULL OR s.isdeleted = FALSE) AND s.servicereg = :servicereg"),
    @NamedQuery(name = "Service.findByIsactive", query = "SELECT s FROM Service s WHERE (s.isdeleted IS NULL OR s.isdeleted = FALSE) AND s.isactive = :isactive"),
    @NamedQuery(name = "Service.findBySeasonstartdate", query = "SELECT s FROM Service s WHERE (s.isdeleted IS NULL OR s.isdeleted = FALSE) AND s.seasonstartdate = :seasonstartdate"),
    @NamedQuery(name = "Service.findBySeasonenddate", query = "SELECT s FROM Service s WHERE (s.isdeleted IS NULL OR s.isdeleted = FALSE) AND s.seasonenddate = :seasonenddate"),
    @NamedQuery(name = "Service.findByWeekprice", query = "SELECT s FROM Service s WHERE (s.isdeleted IS NULL OR s.isdeleted = FALSE) AND s.weekprice = :weekprice"),
    @NamedQuery(name = "Service.findByNumberofplaces", query = "SELECT s FROM Service s WHERE (s.isdeleted IS NULL OR s.isdeleted = FALSE) AND s.numberofplaces = :numberofplaces"),
    @NamedQuery(name = "Service.findByIsdeleted", query = "SELECT s FROM Service s WHERE s.isdeleted = :isdeleted")})
public class Service implements Serializable {

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
    @Column(name = "SERVICEREG")
    private String servicereg;
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
    @Column(name = "NUMBEROFPLACES")
    private Integer numberofplaces;
    @Column(name = "ISDELETED")
    private Boolean isdeleted;
    @Column(name = "OPTLOCKVERSION")
    //@Version
    private Integer optlockversion;
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

    public Service(Integer id, String servicereg) {
        this.id = id;
        this.servicereg = servicereg;
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

    public String getServicereg() {
        return servicereg;
    }

    public void setServicereg(String servicereg) {
        this.servicereg = servicereg;
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

    public Integer getOptlockversion() {
        return optlockversion;
    }

    public void setOptlockversion(Integer optlockversion) {
        this.optlockversion = optlockversion;
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
        return "lt.vu.mif.labanoro_draugai.entities.Service[ id=" + id + " ]";
    }
    
}
