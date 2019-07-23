package mailmanagement.samples.manually;

import mailmanagement.util.MailManager;
import javax.mail.Message;
import management.configuration.POP3Configuration;
import mailmanagement.util.MailUtil;
import management.configuration.MailConfigurationCenter;

/**
 *
 * @author Sarkhan Rasullu
 *
 *Salam Mellim
 */
public class RecieveEmailsSample {

    public static void main(String[] args) throws Exception {
        sampleWithConfigurationFile();
//        sampleWithoutConfigurationFile();
    }

    /*
        this sample describe how to receive emails without configuration file
     */
    public static void sampleWithoutConfigurationFile() throws Exception {
        //your mail server credentials
        String host = "";
        String email = "serxanresullu@zoho.com";
        String password = "";
        int pop3Port = -1;

        //pop3 configuration
        POP3Configuration pop3 = POP3Configuration.instance(host, pop3Port, email, password);

        MailConfigurationCenter conf = MailConfigurationCenter
                .builder()
                .register(pop3)
                .build();

        MailManager mailSender = MailManager.instance(conf);
        Message[] messages = mailSender.receiveEmails(true);

        //or MailManager.receiveEmails(host, port, storeType, true, user, password);
        for (Message m : messages) {
            MailUtil.debugMail(m);
        }
    }

    /*
        this sample describe how to receive emails with configuration file
     */
    public static void sampleWithConfigurationFile() throws Exception {

        POP3Configuration pop3 = POP3Configuration.instanceDefaultPop3Configuration();

        MailConfigurationCenter conf = MailConfigurationCenter
                .builder()
                .register(pop3)
                .build();

        MailManager mailSender = MailManager.instance(conf);
        Message[] messages = mailSender.receiveEmails(true);

        for (Message m : messages) {
            MailUtil.debugMail(m);
        }
    }
}
