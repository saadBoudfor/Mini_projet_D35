package fr.univtln.dosso_boudfor.mini_projet_d35.exceptions;

import javax.persistence.EntityNotFoundException;

/**
 ** Created by yssouf on 02/12/15.
 */


public class TaskNotFoundException extends EntityNotFoundException {


    public TaskNotFoundException() {
        super();
    }

    public TaskNotFoundException(String message) {
        super(message);
    }

}
