package management.configuration;

import javax.mail.Store;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Sarkhan Rasullu
 */
public abstract class AbstractMailServerConfiguration {

    private String host;
    private Integer port;
    private String user;
    private String password;

    private AbstractMailServerConfiguration() {
    }

    public AbstractMailServerConfiguration(String host, Integer port, String user, String password) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        validate();
    }

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    
    private void validate() {
        if (StringUtils.isBlank(getHost())
                || getPort() == null
                || StringUtils.isBlank(getUser())
                || StringUtils.isBlank(getPassword())) {
            throw new RuntimeException("Some fields are not set.Please provide all of the parameters");
        }
    }
}
