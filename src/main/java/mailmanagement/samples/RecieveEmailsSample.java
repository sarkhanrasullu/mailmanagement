package mailmanagement.samples;

import mailmanagement.util.MailUtil;
import javax.mail.Message;

/**
 *
 * @author Sarkhan Rasullu
 */
public class RecieveEmailsSample {
    
    public static void main(String[] args) throws Exception {
        
        Message[] messages = MailUtil.receiveEmails(true);//it will fetch properties from conf/properties file.
        //It is located in out of src folder

        //or MailUtil.receiveEmails(host, port, storeType, true, user, password);
        for (Message m : messages) {
            MailUtil.debugMail(m);
        }
    }
}
