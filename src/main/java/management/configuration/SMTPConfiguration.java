package management.configuration;

import mailmanagement.util.PropertyUtil;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Sarkhan Rasullu
 *
 */
public final class SMTPConfiguration extends AbstractMailServerConfiguration {

    private SMTPConfiguration(String host, Integer port, String user, String password) {
        super(host, port, user, password);
    }

    public static SMTPConfiguration instance(String host, Integer port, String email, String password) {
        return new SMTPConfiguration(host, port, email, password);
    }

    public static SMTPConfiguration instanceDefaultSmtpConfiguration() {
        String host = PropertyUtil.getProperty("smtp_host", "mail.properties");
        Integer port = PropertyUtil.getPropertyAsInt("smtp_port", "mail.properties");
        String user = PropertyUtil.getProperty("smtp_email", "mail.properties");
        String password = PropertyUtil.getProperty("smtp_password", "mail.properties");

        return new SMTPConfiguration(host, port, user, password);
    }
 

}
