package mailmanagement.samples.manually;

import mailmanagement.beans.MailBean;
import management.configuration.SMTPConfiguration;
import mailmanagement.util.MailManager;
import management.configuration.MailConfigurationCenter;

/**
 *
 * @author Sarkhan Rasullu
 */
public class SendEmailSample {

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
        String passwors = "";
        int smtpPort = -1;

        //smtp configuration
        SMTPConfiguration smtp = SMTPConfiguration.instance(host, smtpPort, email, passwors);

        MailConfigurationCenter conf = MailConfigurationCenter.builder().register(smtp).build();

        MailManager mailSender = MailManager.instance(conf);

        String subject = "test subject";
        String message = "test message";
        String to = "serxanresullu@gmail.com";
        MailBean mail = new MailBean(subject, message, to);

        mailSender.sendEmail(mail);
    }

    /*
        this sample describe how to receive emails with configuration file
     */
    public static void sampleWithConfigurationFile() throws Exception {

        //smtp configuration
        SMTPConfiguration smtp = SMTPConfiguration.instanceDefaultSmtpConfiguration();

        MailConfigurationCenter conf = MailConfigurationCenter
                .builder()
                .register(smtp)
                .build();

        MailManager mailSender = MailManager.instance(conf);

        String subject = "test subject";
        String message = "test message";
        String to = "serxanresullu@gmail.com";
        MailBean mail = new MailBean(subject, message, to);

        mailSender.sendEmail(mail);
    }
}
