package com.example.weatherapp.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

/*    private final UserService userService;

    public WebSecurityConfig(UserService userService) {
        this.userService = userService;
    }*/

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        auth.inMemoryAuthentication().
                withUser("Oybek").
                password(encoder.encode("123")).
                /* authorities("READ" , "ADMIN")*/
                        roles("READ", "WRITE");
/*        auth.userDetailsService(userService)
                .passwordEncoder(NoOpPasswordEncoder.getInstance());*/

    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().and()
                .authorizeRequests()./*anyRequest()*/antMatchers("/main/get-temperature")
                .hasAnyRole("READ").anyRequest().authenticated()
                /*.and().authorizeRequests().antMatchers("/", "/registration/add-user").permitAll()*/;

    }
}
