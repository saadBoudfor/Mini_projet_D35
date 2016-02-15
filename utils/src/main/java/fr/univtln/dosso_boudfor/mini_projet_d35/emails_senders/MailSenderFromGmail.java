package fr.univtln.dosso_boudfor.mini_projet_d35.emails_senders;

import fr.univtln.dosso_boudfor.mini_projet_d35.enums.EmailServers;
import fr.univtln.dosso_boudfor.mini_projet_d35.qualifiers.EmailSender;

/**
 * Created by yssouf on 31/12/15.
 **/

@EmailSender(EmailServers.Gmail)
public class MailSenderFromGmail extends MailSender {

    public MailSenderFromGmail() {
        super();

        this.host = "smtp.gmail.com";
        this.username = "youlbri01@gmail.com";
        this.password = "test0123";
        this.from = "youlbri01@gmail.com";

        this.init();
    }


}
