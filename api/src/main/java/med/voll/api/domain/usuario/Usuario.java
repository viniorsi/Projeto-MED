package med.voll.api.domain.usuario;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "usuarios")
@Entity(name = "Usuario")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

//implementando a interface UserDatails para o spring entender o login e senha
//implementando todos metodos da interface

public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;
    private String senha;


    //serve para se no projeto tiver controle de permissao (admin/usuario) precisa de uma classe
    // que represente estes perfis
    //simulamos uma coleção para compilar o projeto porem nao teremos esse controle de permissao
    //todos usaram o projeto
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    //qual atributo é nosso username
    @Override
    public String getUsername() {
        return login;
    }



    // para usar estes metodos de controle basta apenas criar atributos que repesentam estas funções e passar eles
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }


    //conta do usuario pode ser bloqueada
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }


    //a conta tem data de expiração
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
