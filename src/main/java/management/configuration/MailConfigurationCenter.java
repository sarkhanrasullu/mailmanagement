package management.configuration;

import java.util.HashMap;

/**
 *
 * @author Sarkhan Rasullu
 */
public class MailConfigurationCenter {

    private HashMap<Class<?>, AbstractMailServerConfiguration> configurations;

    private MailConfigurationCenter() {
        configurations = new HashMap<>();
    }

    private MailConfigurationCenter register(AbstractMailServerConfiguration configuration) {
        configurations.put(configuration.getClass(), configuration);
        return this;
    }

    public SMTPConfiguration getSmtpConfiguration() {
        return (SMTPConfiguration) configurations.get(SMTPConfiguration.class);
    }

    public POP3Configuration getPop3Configuration() {
        return (POP3Configuration) configurations.get(POP3Configuration.class);
    }

    public static MailConfigurationBuilder builder() {
        return new MailConfigurationBuilder();
    }

    public static class MailConfigurationBuilder {

        private final MailConfigurationCenter conf;

        public MailConfigurationBuilder() {
            this.conf = new MailConfigurationCenter();
        }

        public MailConfigurationBuilder register(AbstractMailServerConfiguration configuration) {
            conf.register(configuration);
            return this;
        }

        public MailConfigurationCenter build() {
            return conf;
        }
    }

}
