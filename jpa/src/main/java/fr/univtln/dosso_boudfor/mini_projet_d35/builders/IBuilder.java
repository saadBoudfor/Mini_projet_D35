package fr.univtln.dosso_boudfor.mini_projet_d35.builders;

import fr.univtln.dosso_boudfor.mini_projet_d35.exceptions.BuilderException;


/**
 * * Created by yssouf on 02/12/15.
 */


public interface IBuilder<T, Q> {

    public Q build() throws BuilderException;

    public T init();


}
