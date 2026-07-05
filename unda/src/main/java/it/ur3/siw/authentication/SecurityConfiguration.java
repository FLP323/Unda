package it.ur3.siw.authentication;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final DataSource dataSource;

    public SecurityConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
        manager.setUsersByUsernameQuery(
            "SELECT username, password, 1 as enabled FROM utente WHERE username=?");
        manager.setAuthoritiesByUsernameQuery(
            "SELECT username, role FROM utente WHERE username=?");
        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SecurityFilterChain restFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/rest/**")
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.GET, "/rest/artists/**", "/rest/albums/**", "/rest/playlists/**", "/rest/canzoni/**").permitAll()
                .requestMatchers("/rest/admin/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/rest/user/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .anyRequest().authenticated()
            )
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
            .exceptionHandling(exception -> exception
                .authenticationEntryPoint((request, response, authException) -> response.sendError(401, "Unauthorized"))
            );
        return http.build();
    }

    @Bean
    protected SecurityFilterChain configure(final HttpSecurity httpSecurity) throws Exception {

    	httpSecurity.authorizeHttpRequests(authorize -> {
			// Risorse statiche sempre pubbliche
			authorize.requestMatchers(HttpMethod.GET, "/", "/index", "/register", "/login", "/css/**", "/images/**").permitAll();
			authorize.requestMatchers(HttpMethod.POST, "/register", "/login").permitAll();
			/*// [ALMENO USER] funzionalità dell'utente registrato (GET e POST)
			authorize.requestMatchers(HttpMethod.GET, "/rest/user/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN");
			authorize.requestMatchers(HttpMethod.POST, "/rest/user/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN");
			// [SOLO ADMIN] funzionalità dell'amministratore (GET e POST)
			authorize.requestMatchers(HttpMethod.GET, "/rest/user/**").hasAnyAuthority("ROLE_ADMIN");
			authorize.requestMatchers(HttpMethod.POST, "/rest/user/**").hasAnyAuthority("ROLE_ADMIN");*/
			// [TUTTI] funzionalità dell'utente qualsiasi (GET)
			authorize.requestMatchers(HttpMethod.GET, "/albums/**", "/artists/**", "/playlists/**", "/rest/**").permitAll();
			
			// Sezione utente registrato generica
			authorize.requestMatchers("/user/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN");
			// Sezione admin generica
			authorize.requestMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN");
			// Tutto il resto richiede autenticazione
			authorize.anyRequest().authenticated();
		});

        httpSecurity.formLogin(form -> {
            form.loginPage("/login").permitAll();
            form.defaultSuccessUrl("/", true);
            form.failureUrl("/login?error=true");
        });

        httpSecurity.logout(logout -> {
            logout.logoutUrl("/logout");
            logout.logoutSuccessUrl("/");
            logout.invalidateHttpSession(true);
            logout.deleteCookies("JSESSIONID");
            logout.clearAuthentication(true);
            logout.permitAll();
        });

        return httpSecurity.build();
    }
    
}
