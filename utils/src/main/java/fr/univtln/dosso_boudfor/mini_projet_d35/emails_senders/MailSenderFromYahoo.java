package fr.univtln.dosso_boudfor.mini_projet_d35.emails_senders;

import fr.univtln.dosso_boudfor.mini_projet_d35.enums.EmailServers;
import fr.univtln.dosso_boudfor.mini_projet_d35.qualifiers.EmailSender;

/**
 * Created by yssouf on 31/12/15.
 **/

@EmailSender(EmailServers.Yahoo)
public class MailSenderFromYahoo extends MailSender {

    public MailSenderFromYahoo() {
        super();

        this.host = "smtp.mail.yahoo.com";
        this.username = "fanga_yssouf@yahoo.fr";
        this.password = "moussA111";
        this.from = "fanga_yssouf@yahoo.fr";

        this.init();
    }

}
