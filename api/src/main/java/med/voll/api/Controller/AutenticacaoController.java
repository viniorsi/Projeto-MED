package med.voll.api.Controller;


import jakarta.validation.Valid;
import med.voll.api.domain.usuario.DadosAutenticacao;
import med.voll.api.domain.usuario.Usuario;
import med.voll.api.infra.security.DadosTokenJWT;
import med.voll.api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {


    //chama a classe de autenticação que esta com o repository
    //precisamos ensinar o spring a injetar esta classe na security configurations
    @Autowired
    private AuthenticationManager manager;

    //nosso token service
    @Autowired
    private TokenService tokenService;


    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados){
        //login e senha usando uma classe do proprio spring passando os dados do nosso dto
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(),dados.senha());
        //chama classe com o repository para fazer a pesquisa no banco de dados e autenticar
        //ele recebe dados do dto do spring que vem da UsernamePasswordAuthenticatioToken, que preenchemos com
        //os dados do nosso dto
        var authentication = manager.authenticate(authenticationToken);
        //pega o usuariio de dentro do authentication pois ele recebe um usuario, e usamos o get principal
        //para pegar o usuario
        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        //retornando o token por um dto
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));

    }

}
