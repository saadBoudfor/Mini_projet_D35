package fr.univtln.dosso_boudfor.mini_projet_d35.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by sboudfor274 on 17/12/15.
 **/

@Entity
@XmlRootElement
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"texte", "proprio_ID"})})
public class Label implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(min=3, max=30)
    @Column(name = "texte")
    private String texte;


    @NotNull
    @ManyToOne
    @JoinColumn(name = "task_ID")
    private Task associatedTask;


    @NotNull
    @ManyToOne
    @JoinColumn(name = "proprio_ID")
    private User proprio;



    public Label(){
    }


}
