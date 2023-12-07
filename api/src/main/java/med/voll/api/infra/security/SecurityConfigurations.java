package med.voll.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


//classe de configuração
//indicar o spring que vamos modificar as configuraçoes de segurança
@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;

    //expondo o objeto para o spring mostrando que estou devolvendo o objeto dele mesmo (SecurityFilterChain)
    @Bean

    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        // csrf - cross site request forgery, vamos desabilitar esta função de segurança
        // pois o token ja faz essa função de segurnça
        //desabilitando formulario do spring que traz sendo statefull e mostrando que quer stateless por ser uma api rest
        return http.csrf().disable().
                sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                se vier uma requisição para /login do tipo post é para permitir todas pois ela é publica
                .and().authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST,"/login").permitAll()
//                qualquer outra requisição precisa ter a autenticação de token
                .anyRequest().authenticated()
                //ordem dos filtros a serem chamados, o nosso filtro tem que vir primeiro para autenticar o usuario
                .and().addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }



    //ensinando o spring a injetar a classe authentication manager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{

        return configuration.getAuthenticationManager();
    }

    //algoritimo de hash de senha
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }







}
