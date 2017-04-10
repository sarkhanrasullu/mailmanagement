package mailmanagement.util;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 *
 * @author Sarkhan Rasullu
 */
public class MailUtil {

    private static final Logger LOG = Logger.getLogger(MailUtil.class.getName());

    public static String emailToHtml(Message message) {
        String res = "<br/>";

        try {
            res += "<b>From:</b> " + message.getFrom()[0] + "<br/>";
            res += "<b>Subject:</b> " + message.getSubject() + "<br/>";
            res += "<b>Sent:</b> " + message.getSentDate() + "<br/>";
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
            res += "Sent: " + message.getSentDate() + "\n";
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

}
