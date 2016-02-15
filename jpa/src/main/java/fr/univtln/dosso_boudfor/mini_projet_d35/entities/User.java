package fr.univtln.dosso_boudfor.mini_projet_d35.entities;

import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by yssouf on 22/11/15.
 * @version 1.0
 * @author Dosso
 * @author Boudfor
 * Pour notre application, un utilisateur est définit par son Nom, son prenom ainsi que son adresse mail.
 */


@NamedQueries({
        @NamedQuery(name="User.findAll", query="SELECT u FROM User u"),
        @NamedQuery(name="User.connection", query="SELECT u FROM User u WHERE u.email LIKE :uEmail and u.password LIKE :uPassword"),
        @NamedQuery(name="User.check", query="SELECT u FROM User u WHERE u.email LIKE :uEmail and u.password LIKE :uPassword"),
})
@Entity
@XmlRootElement
public class User implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(min=2)
    private String nom;

    @NotNull
    @Size(min=2)
    private String prenom;

    @NotNull
    @Email(message = "email non valid")
    @Column(unique = true)   // important pour l'appli
    private String email;

    @NotNull
    @Size(min=3)
    private String password;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @XmlTransient
    private Set<Project> mesProjets = new HashSet<>();

    @ManyToMany(mappedBy = "participants", fetch = FetchType.LAZY)
    @XmlTransient
    private Set<Project> mesParticipations = new HashSet<>();

    @OneToMany(mappedBy = "proprio", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @XmlTransient
    private Set<Label> mesLabels = new HashSet<>();

    @OneToMany(mappedBy = "manager", fetch = FetchType.LAZY)
    @XmlTransient
    private Set<Task> tasksAssignedToMe = new HashSet<>();


    /********************************************************/

    public User() {
    }


    public User(String nom, String prenom, String email, String password) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
    }

    /********************************************************/

    public long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }


    public Set<Project> getMesProjets() {
        return mesProjets;
    }

    public Set<Project> getMesParticipations() {
        return mesParticipations;
    }

    public Set<Label> getMesLabels() {
        return mesLabels;
    }

    public Set<Task> getTasksAssignedToMe() {
        return tasksAssignedToMe;
    }

    /********************************************************/

    public void setId(long id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public void addNewProjet(Project project){
        this.mesProjets.add(project);
    }

    public void participerTo(Project project){
        this.mesParticipations.add(project);
    }

    public void addLabel(Label label){
        this.mesLabels.add(label);
    }

    public void takeTask(Task task){
        this.tasksAssignedToMe.add(task);
    }

    /********************************************************/

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
