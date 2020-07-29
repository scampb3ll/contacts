package com.singlestoneconsulting.contacts;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class RepoRestConfig extends RepositoryRestConfigurerAdapter {

    private final LocalValidatorFactoryBean beanValidator;

    public RepoRestConfig(LocalValidatorFactoryBean beanValidator) {
        this.beanValidator = beanValidator;
    }

    @Override
    public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener v) {
        v.addValidator("beforeCreate", beanValidator);
        v.addValidator("beforeSave", beanValidator);
        super.configureValidatingRepositoryEventListener(v);
    }
}
