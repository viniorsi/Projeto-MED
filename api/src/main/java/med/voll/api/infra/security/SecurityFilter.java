package med.voll.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


// esta classe serve para não deixar que as requisições das controllers sejam chamadas sem servem validadas
//passa por aqui e se tiver o token ela libera a requisição chamada
@Component // serve para o spring carregar a classe e ela não tiver um tipo especifico, algo generico
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository repository;

    //recupera token do cabeçalho e valida o token
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var tokenJWT = recuperarToken(request);

        //se o token estiver ativo ele vai pegar o usuario dentro do token e procurar as
        // credenciasi dentro do banco com o repository
        if(tokenJWT != null) {
            var subjetc = tokenService.getSubject(tokenJWT);
            var usuario = repository.findByLogin(subjetc);
            var authentication = new UsernamePasswordAuthenticationToken(usuario,null,usuario.getAuthorities());

            //força a autenticação do usuario logado
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }

        System.out.println(subjetc);


        //serve para chamar a cadeia de filtros
        filterChain.doFilter(request,response);

    }

    //recuperando token do cabeçalho da requisição
    private String recuperarToken(HttpServletRequest request) {

        var authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader != null){
            return authorizationHeader.replace("Bearer" , "");
        }

    return null;
    }
}
