package fr.univtln.dosso_boudfor.mini_projet_d35.entities;

import fr.univtln.dosso_boudfor.mini_projet_d35.enums.Priority;
import fr.univtln.dosso_boudfor.mini_projet_d35.enums.Status;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sboudfor274 on 24/11/15.
 * @version 1.0
 * @author Dosso
 * @author Boudfor
 * Une tache est definit par un titre, son contenue(optionnel) et une date de creation et d'écheance.
 *
 */


@NamedQueries({
        @NamedQuery(name="Task.findAll", query="SELECT t FROM Task t"),
        @NamedQuery(name="Task.findAllByProject", query="SELECT t FROM Task t WHERE t.parent.id = :pId "),
        @NamedQuery(name="Task.findNotExpiredByProject", query="SELECT t FROM Task t WHERE (t.delaisEcheance >= :tDate or t.delaisEcheance is null) AND t.parent.id = :pId "),
        @NamedQuery(name="Task.findNotExpiredByUser", query="SELECT t FROM Task t WHERE (t.delaisEcheance >= :tDate or t.delaisEcheance is null ) AND t.parent.owner.id = :oId ")
})
@Entity
@XmlRootElement
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"titre", "parent_ID"})})
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(min=2, max=30)
    @Column(name = "titre")
    private String titre;

    @NotNull
    @Size(min=3)
    private String contenue;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date dateCreation;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Priority priority;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_ID")
    @XmlTransient
    private Project parent;

    @Temporal(TemporalType.DATE)
//    @Future
    private Date delaisEcheance;

    @Temporal(TemporalType.DATE)
    private Date lastModifDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsible_ID")
    @XmlTransient
    private User manager;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "associatedTask", cascade = CascadeType.REMOVE)
    @XmlTransient
    private Set<Label> labels = new HashSet<>();


    /********************************************************/

    public Task() {

    }

    public Task(String titre, String contenue, Status status, Priority priority, Project parent) {
        this.titre = titre;
        this.contenue = contenue;
        this.status = status;
        this.priority = priority;
        this.parent = parent;
        this.dateCreation = new Date();

    }

    public Task(String titre, String contenue, Status status, Date delaisEcheance, Priority priority, Project parent) {
        this.titre = titre;
        this.contenue = contenue;
        this.status = status;
        this.delaisEcheance = delaisEcheance;
        this.priority = priority;
        this.parent = parent;
        this.dateCreation = new Date();

    }

    /********************************************************/

    public long getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public String getContenue() {
        return contenue;
    }

    public Status getStatus() {
        return status;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public Priority getPriority() {
        return priority;
    }

    public Project getParent() {
        return parent;
    }

    public Date getDelaisEcheance() {
        return delaisEcheance;
    }

    public Date getLastModifDate() {
        return lastModifDate;
    }

    public User getManager() {
        return manager;
    }

    public Set<Label> getLabels() {
        return labels;
    }

    /********************************************************/

    public void setId(long id) {
        this.id = id;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setContenue(String contenue) {
        this.contenue = contenue;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setParent(Project parent) {
        this.parent = parent;
    }

    public void setDelaisEcheance(Date delaisEcheance) {
        this.delaisEcheance = delaisEcheance;
    }

    public void setLastModifDate(Date lastModifDate) {
        this.lastModifDate = lastModifDate;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public void addLabel(Label label){
        this.labels.add(label);
    }

    /********************************************************/

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", contenue='" + contenue + '\'' +
                ", status=" + status +
                ", dateCreation=" + dateCreation +
                ", priority=" + priority +
                ", delaisEcheance=" + delaisEcheance +
                ", lastModifDate=" + lastModifDate +
                '}';
    }
}
