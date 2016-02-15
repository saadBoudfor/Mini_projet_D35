package fr.univtln.dosso_boudfor.mini_projet_d35.annotations;

import javax.inject.Qualifier;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 *  Created by fdosso335 on 01/12/15.
 **/


@Qualifier
@Inherited
@Target({TYPE, METHOD, PARAMETER, FIELD})
@Retention(RUNTIME)
public @interface JPADAO {

}

