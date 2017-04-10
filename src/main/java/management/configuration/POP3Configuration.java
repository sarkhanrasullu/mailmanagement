package management.configuration;

import mailmanagement.util.PropertyUtil;

/**
 *
 * @author Sarkhan Rasullu
 */
public final class POP3Configuration extends AbstractMailServerConfiguration {

    private POP3Configuration(String host, Integer port, String email, String password) {
        super(host, port, email, password);
    }

    public static POP3Configuration instance(String host, Integer port, String email, String password) {
        return new POP3Configuration(host, port, email, password);
    }

    public static POP3Configuration instanceDefaultPop3Configuration() {
        String host = PropertyUtil.getProperty("pop3_host", "mail.properties");
        Integer port = PropertyUtil.getPropertyAsInt("pop3_port", "mail.properties");
        String user = PropertyUtil.getProperty("pop3_email", "mail.properties");
        String password = PropertyUtil.getProperty("pop3_password", "mail.properties");

        return new POP3Configuration(host, port, user, password);
    }

 
}
