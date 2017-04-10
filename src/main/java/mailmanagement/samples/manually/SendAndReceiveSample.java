package mailmanagement.samples.manually;

import javax.mail.Message;
import mailmanagement.beans.MailBean;
import mailmanagement.util.MailManager;
import mailmanagement.util.MailUtil;
import management.configuration.MailConfigurationCenter;
import management.configuration.POP3Configuration;
import management.configuration.SMTPConfiguration;

/**
 *
 * @author Sarkhan Rasullu
 */
public class SendAndReceiveSample {

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
        String email = "";
        String password = "";
        int smtpPort = -1;
        int pop3Port = -1;

        //smtp configuration
        SMTPConfiguration smtp = SMTPConfiguration.instance(host, smtpPort, email, password);

        //pop3 configuration
        POP3Configuration pop3 = POP3Configuration.instance(host, pop3Port, email, password);

        MailConfigurationCenter conf = MailConfigurationCenter
                .builder()
                .register(smtp)
                .register(pop3)
                .build();

        MailManager mailManager = MailManager.instance(conf);

        String subject = "test subject";
        String message = "test message";
        String to = "serxanresullu@gmail.com";
        MailBean mail = new MailBean(subject, message, to);

        mailManager.sendEmail(mail);

        Message[] messages = mailManager.receiveEmails(true);

        for (Message m : messages) {
            MailUtil.debugMail(m);
        }

    }

    /*
        this sample describe how to receive emails with configuration file
     */
    public static void sampleWithConfigurationFile() throws Exception {

        //smtp configuration
        SMTPConfiguration smtp = SMTPConfiguration.instanceDefaultSmtpConfiguration();

        //pop3 configuration
        POP3Configuration pop3 = POP3Configuration.instanceDefaultPop3Configuration();

        MailConfigurationCenter conf = MailConfigurationCenter
                .builder()
                .register(smtp)
                .register(pop3)
                .build();

        MailManager mailManager = MailManager.instance(conf);

        String subject = "test subject";
        String message = "test message";
        String to = "serxanresullu@gmail.com";
        MailBean mail = new MailBean(subject, message, to);

        Message[] messages = mailManager.receiveEmails(true);

        for (Message m : messages) {
            MailUtil.debugMail(m);
        }

    }
}
