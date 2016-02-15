package fr.univtln.dosso_boudfor.mini_projet_d35.exceptions;

import javax.persistence.EntityNotFoundException;

/**
 ** Created by yssouf on 02/12/15.
 */


public class ProjectNotFoundException extends EntityNotFoundException {


    public ProjectNotFoundException() {
        super();
    }

    public ProjectNotFoundException(String message) {
        super(message);
    }

}
