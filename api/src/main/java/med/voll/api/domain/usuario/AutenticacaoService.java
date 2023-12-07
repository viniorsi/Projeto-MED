package med.voll.api.domain.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



// serviço de autenticação
//userdateilsService serve para dizer para o spring que a classe authenticação service é uma
// service de autenticação
@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

 // logica de autenticação
    // metodo da propria interface para procurar usuario
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //procura no banco de dados por login
        return repository.findByLogin(username);
    }

}
