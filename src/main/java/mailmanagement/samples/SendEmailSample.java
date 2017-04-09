package mailmanagement.samples;

import mailmanagement.beans.MailBean;
import mailmanagement.util.MailUtil;

/**
 *
 * @author Sarkhan Rasullu
 */
public class SendEmailSample {

    public static void main(String[] args) {
        String subject = "test subject";
        String message = "test message";
        String to = "serxanresullu@gmail.com";
        MailBean mail = new MailBean(subject, message, to);
        MailUtil.sendEmail(mail);
    }
}
