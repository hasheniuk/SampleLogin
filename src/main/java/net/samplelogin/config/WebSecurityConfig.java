package net.samplelogin.config;

import net.samplelogin.util.Assert;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.inject.Inject;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // TODO remove some ant matchers after integration security with social
        http.authorizeRequests()
                .antMatchers("/", "/css/*", "/js/*", "/connect/*", "/auth/*", "/profile", "/registration").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .defaultSuccessUrl("/profile") // TODO add failure url
                .loginPage("/auth")
                .usernameParameter("email")
                .passwordParameter("password")
                .permitAll()
                .and()
            .logout()
                .permitAll();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    @Inject
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        Assert.notNull(userDetailsService, UserDetailsService.class);
        this.userDetailsService = userDetailsService;
    }

    @Inject
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        Assert.notNull(auth, AuthenticationManagerBuilder.class);
        auth.userDetailsService(userDetailsService);
    }
}
