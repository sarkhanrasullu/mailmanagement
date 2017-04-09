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
import org.apache.commons.lang3.StringEscapeUtils;

public class MailUtil {

    private static final Logger LOG = Logger.getLogger(MailUtil.class.getName());
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");

    private static Folder emailFolder = null;
    private static POP3Store emailStore = null;

    private static Folder getEmailFolder() {
        return emailFolder;
    }

    private static void closeFolder() throws Exception {
        LOG.log(Level.INFO, "closing mail folder");

        if (emailFolder != null) {
            emailFolder.close(true);
        }
        if (emailStore != null) {
            emailStore.close();
        }
        LOG.log(Level.INFO, "mail folder closed");

    }

    public static Message[] receiveEmails(boolean useSsl) throws Exception {
        String mailPop3Host = PropertyUtil.getProperty("host", "mail.properties");
        int mailport = Integer.parseInt(PropertyUtil.getProperty("port", "mail.properties"));
        String mailStoreType = "pop3";
        String mailUser = PropertyUtil.getProperty("email", "mail.properties");
        String mailPassword = PropertyUtil.getProperty("password", "mail.properties");
        return receiveEmails(mailPop3Host, mailport, mailStoreType, useSsl, mailUser, mailPassword);
    }

    public static Message[] receiveEmails(String host, int port, String storeType, boolean useSsl, String user, String password) throws Exception {
        Properties properties = new Properties();
        Session emailSession = null;
        properties.put("mail.pop3.ssl.enable", useSsl);
        emailSession = Session.getDefaultInstance(properties);

        emailStore = (POP3Store) emailSession.getStore(storeType);
        emailStore.connect(host, port, user, password);

        emailFolder = emailStore.getFolder("INBOX");
        emailFolder.open(Folder.READ_WRITE);
        Flags seen = new Flags(Flags.Flag.SEEN);
        FlagTerm unseenFlagTerm = new FlagTerm(seen, false);

        Message[] messages = emailFolder.getMessages();
//        search(unseenFlagTerm);
        return messages;
    }

    public static void markMessage(Message message, Flag flag) {
        try {
            message.setFlag(flag, true);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    public static String emailToHtml(Message message) {
        String res = "<br/>";

        try {
            res += "<b>From:</b> " + message.getFrom()[0] + "<br/>";
            res += "<b>Subject:</b> " + message.getSubject() + "<br/>";
            res += "<b>Sent:</b> " + sdf.format(message.getSentDate()) + "<br/>";
            try {
                res += "<b>Body:</b><br/> " + StringEscapeUtils.escapeHtml4(StreamUtil.getStringFromInputStream(message.getInputStream())) + "<br/><br/><hr/>";
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, null, ex);
            }
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
        } finally {
            return res;
        }
    }

    public static void debugMail(Message message) {
        String res = "\n";

        try {
            res += "From: " + message.getFrom()[0] + "\n";
            res += "Subject: " + message.getSubject() + "\n";
            res += "Sent: " + sdf.format(message.getSentDate()) + "\n";
            try {
                res += "Body:\n\n" + StreamUtil.getStringFromInputStream(message.getInputStream());
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, null, ex);
            }
            LOG.log(Level.INFO, res);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    public static void sendEmail(MailBean mailBean) {
        String username = PropertyUtil.getProperty("senderemail", "mail.properties");
        String password = PropertyUtil.getProperty("senderpassword", "mail.properties");
        String host = PropertyUtil.getProperty("senderhost", "mail.properties");
        String port = PropertyUtil.getProperty("senderport", "mail.properties");
        try {
            Properties props = new Properties();
            props.setProperty("mail.smtps.host", host);
            if (username.contains("@gmail")) {
                props.put("mail.smtp.starttls.enable", "true");
            } else {
                props.put("mail.smtp.socketFactory.port", port);
                props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            }
            props.put("mail.smtps.auth", "true");
            props.put("mail.smtp.port", port);

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(username));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailBean.getTo(), false));
            msg.setSubject(mailBean.getSubject());
            msg.setContent(mailBean.getMessage(), "text/html; charset=utf-8");
            msg.setHeader("X-Mailer", "Pass link");
            msg.setSentDate(new Date());

            SMTPTransport t = (SMTPTransport) session.getTransport("smtp");
            System.out.println("connecting to smtp server...");
            t.connect(host, username, password);
            System.out.println("connected to smtp server");
            System.out.println("message is sending...");
            t.sendMessage(msg, msg.getAllRecipients());
            System.out.println("message sent");
            System.out.println("connnection is closing...");
            t.close();
            System.out.println("connections closed");
        } catch (Exception ex) {
            Logger.getLogger(MailUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
