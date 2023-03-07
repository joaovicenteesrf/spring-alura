package med.voll.api.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
// A anotação abaixo serve para informar ao Spring que vamos personalizar as configurações de segurança.
@EnableWebSecurity
public class SecurityConfigurations {

    /*
    Anotação Bean serve para exportar uma classe para o Spring, fazendo com que ele consiga carregá-la e realize a sua
    injeção de dependência em outras classes.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        /*
        Esse método desabilita o tratamento contra ataques do tipo Cross Site Request Forgery, pois tokens serão
        utilizados e, portanto, já fazem essa validação.

        Na sequência, é dito para o Spring desabilitar o processo de autenticação em que é retornado o formulário e a
        autenticação é STATEFULL. Estamos informando que queremos uma atenticação do tipo STATELESS pois estamos
        desenvolvendo uma API do tipo REST.
         */
        return http
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
