package com.cazen.iti.config.social;

import com.cazen.iti.repository.SocialUserConnectionRepository;
import com.cazen.iti.repository.CustomSocialUsersConnectionRepository;
import com.cazen.iti.security.social.CustomSignInAdapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.linkedin.connect.LinkedInConnectionFactory;
import org.springframework.social.security.AuthenticationNameUserIdSource;
// jhipster-needle-add-social-connection-factory-import-package

import javax.inject.Inject;

/**
 * Basic Spring Social configuration.
 *
 * <p>Creates the beans necessary to manage Connections to social services and
 * link accounts from those services to internal Users.</p>
 */
@Configuration
@EnableSocial
public class SocialConfiguration implements SocialConfigurer {
    private final Logger log = LoggerFactory.getLogger(SocialConfiguration.class);

    @Inject
    private SocialUserConnectionRepository socialUserConnectionRepository;

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {


        // Facebook configuration
        String facebookClientId = environment.getProperty("spring.social.facebook.clientId");
        String facebookClientSecret = environment.getProperty("spring.social.facebook.clientSecret");
        if (facebookClientId != null && facebookClientSecret != null) {
            log.debug("Configuring FacebookConnectionFactory");
            connectionFactoryConfigurer.addConnectionFactory(
                new FacebookConnectionFactory(
                    facebookClientId,
                    facebookClientSecret
                )
            );
        } else {
            log.error("Cannot configure FacebookConnectionFactory id or secret null");
        }

        // LinkedIn configuration
        String linkedInClientId = environment.getProperty("spring.social.linkedin.clientId");
        String linkedInClientSecret = environment.getProperty("spring.social.linkedin.clientSecret");
        if (linkedInClientId != null && linkedInClientSecret != null) {
            log.debug("Configuring LinkedInConnectionFactory");
            connectionFactoryConfigurer.addConnectionFactory(
                new LinkedInConnectionFactory(
                    linkedInClientId,
                    linkedInClientSecret
                )
            );
        } else {
            log.error("Cannot configure LinkedInConnectionFactory id or secret null");
        }

/*        // GitHub configuration
        String githubClientId = environment.getProperty("spring.social.github.clientId");
        String githubClientSecret = environment.getProperty("spring.social.github.clientSecret");
        if (githubClientId != null && githubClientSecret != null) {
            log.debug("Configuring GitHubConnectionFactory");
            connectionFactoryConfigurer.addConnectionFactory(
                new GitHubConnectionFactory(
                    githubClientId,
                    githubClientSecret
                )
            );
        } else {
            log.error("Cannot configure GitHubConnectionFactory id or secret null");
        }*/

        // jhipster-needle-add-social-connection-factory
    }

    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        return new CustomSocialUsersConnectionRepository(socialUserConnectionRepository, connectionFactoryLocator);
    }

    @Bean
    public SignInAdapter signInAdapter() {
        return new CustomSignInAdapter();
    }

    @Bean
    public ProviderSignInController providerSignInController(ConnectionFactoryLocator connectionFactoryLocator, UsersConnectionRepository usersConnectionRepository, SignInAdapter signInAdapter) throws Exception {
        ProviderSignInController providerSignInController = new ProviderSignInController(connectionFactoryLocator, usersConnectionRepository, signInAdapter);
        providerSignInController.setSignUpUrl("/social/signup");
        return providerSignInController;
    }

    @Bean
    public ProviderSignInUtils getProviderSignInUtils(ConnectionFactoryLocator connectionFactoryLocator, UsersConnectionRepository usersConnectionRepository) {
        return new ProviderSignInUtils(connectionFactoryLocator, usersConnectionRepository);
    }
}
