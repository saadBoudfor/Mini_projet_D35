package fr.univtln.dosso_boudfor.mini_projet_d35.entities;

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
 *  @version 1.0
 * @author Dosso
 * @author Boudfor
 */


@NamedQueries({
        @NamedQuery(name="Project.findProjectByNameAndOwnerId", query="SELECT p FROM Project p WHERE p.titre LIKE :pTitle and p.owner.id = :oId "),
        @NamedQuery(name="Project.findProjectCreatedByOwnerId", query="SELECT p FROM Project p WHERE p.owner.id = :oId "),
        @NamedQuery(name="Project.getParticipations", query="SELECT p FROM Project p WHERE p.owner.id = :oId ")
})
@Entity
@XmlRootElement
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"titre", "owner_ID"})})
public class Project implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(min=2)
    @Column(name = "titre")
    private String titre;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date dateCreation;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_ID")
    private User owner;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @XmlTransient
    private Set<Task> taches = new HashSet<>();


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "Projet_Participant",
            joinColumns = {@JoinColumn(name = "projet_ID")},
            inverseJoinColumns = {@JoinColumn(name = "participant_ID")})
    @XmlTransient
    private Set<User> participants = new HashSet<>();

    /********************************************************/

    public Project(){

    }

    public Project(String titre, Date dateCreation, User owner) {
        this.titre = titre;
        this.dateCreation = dateCreation;
        this.owner = owner;
    }

    /********************************************************/

    public long getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public User getOwner() {
        return owner;
    }

    public Set<Task> getTaches() {
        return taches;
    }


    public Set<User> getParticipants() {
        return participants;
    }

    /********************************************************/

    public void setId(long id) {
        this.id = id;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void addTask(Task task){
        this.taches.add(task);
    }

    public void addParticipant(User newParticipant){
        this.participants.add(newParticipant);
    }

    /********************************************************/

    @Override
    public String toString() {
        return titre;
    }

}
