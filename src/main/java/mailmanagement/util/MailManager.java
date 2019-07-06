package mailmanagement.util;

import mailmanagement.beans.MailBean;
import com.sun.mail.pop3.POP3Store;
import com.sun.mail.smtp.SMTPTransport;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.search.FlagTerm;
import management.configuration.POP3Configuration;
import management.configuration.SMTPConfiguration;
import management.configuration.MailConfigurationCenter;

public class MailManager {

    private static final Logger LOG = Logger.getLogger(MailManager.class.getName());

    private Folder emailFolder;
    private POP3Store emailStore;
    private MailConfigurationCenter mailConfiguration;

    private MailManager(MailConfigurationCenter mailConfiguration) {
        this.mailConfiguration = mailConfiguration;
    }

    //define properties manually
    public static MailManager instance(MailConfigurationCenter mailConfiguration) {
        MailManager m = new MailManager(mailConfiguration);
        return m;
    }

    private Folder getEmailFolder() {
        return emailFolder;
    }

    private void closeFolder() throws Exception {
        LOG.log(Level.INFO, "closing mail folder");

        if (emailFolder != null) {
            emailFolder.close(true);
        }
        if (emailStore != null) {
            emailStore.close();
        }
        LOG.log(Level.INFO, "mail folder closed");

    }

    public Message[] receiveEmails(boolean useSsl) throws Exception {
        Properties properties = new Properties();
        Session emailSession = null;
        properties.put("mail.pop3.ssl.enable", useSsl);
        emailSession = Session.getDefaultInstance(properties);

        POP3Configuration conf = (POP3Configuration) mailConfiguration.getPop3Configuration();

        emailStore = (POP3Store) emailSession.getStore("pop3");
        emailStore.connect(conf.getHost(), conf.getPort(), conf.getUser(), conf.getPassword());

        emailFolder = emailStore.getFolder("INBOX");
        emailFolder.open(Folder.READ_WRITE);
        Flags seen = new Flags(Flags.Flag.SEEN);
        FlagTerm unseenFlagTerm = new FlagTerm(seen, false);

        Message[] messages = emailFolder.getMessages();
        return messages;
    }

    public void markMessage(Message message, Flag flag) {
        try {
            message.setFlag(flag, true);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    public void sendEmail(MailBean mailBean) {
        SMTPConfiguration conf = (SMTPConfiguration) mailConfiguration.getSmtpConfiguration();

        try {
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", conf.getHost());
            if (conf.getUser().contains("@gmail")) {
                props.put("mail.smtp.starttls.enable", "true");
            } else {
                props.put("mail.smtp.socketFactory.port", conf.getPort());
                props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            }
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", conf.getPort());

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(conf.getUser(), conf.getPassword());
                }
            });

            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(conf.getUser()));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailBean.getTo(), false));
            msg.setSubject(mailBean.getSubject());
            msg.setContent(mailBean.getMessage(), "text/html; charset=utf-8");
            msg.setHeader("X-Mailer", "Pass link");
            msg.setSentDate(new Date());

            SMTPTransport t = (SMTPTransport) session.getTransport("smtp");
            LOG.info("connecting to smtp server...");
            t.connect(conf.getHost(), conf.getUser(), conf.getPassword());
            LOG.info("connected to smtp server");
            LOG.info("message is sending...");
            t.sendMessage(msg, msg.getAllRecipients());
            LOG.info("message sent");
            LOG.info("connnection is closing...");
            t.close();
            LOG.info("connections closed");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

}
