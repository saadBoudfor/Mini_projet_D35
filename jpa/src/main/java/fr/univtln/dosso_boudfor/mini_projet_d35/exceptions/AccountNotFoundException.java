package fr.univtln.dosso_boudfor.mini_projet_d35.exceptions;

import javax.persistence.EntityNotFoundException;

/**
 ** Created by yssouf on 02/12/15.
 */


public class AccountNotFoundException extends EntityNotFoundException {


    public AccountNotFoundException() {
        super();
    }

    public AccountNotFoundException(String message) {
        super(message);
    }

}
