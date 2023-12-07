package med.voll.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import med.voll.api.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


//gera e valida token
@Service
public class TokenService {

    //pega o valor da aplication.properties
    @Value("${api.security.token.secret}")
    private String secret;
    public String gerarToken(Usuario usuario) {
        //try catch da propria biblioteca do auth

        try {
            //algoritimo de assinatura que sera usado
            //recebe como parametro uma string que é a chave secreta para fazer a geração de token

            var algoritmo = Algorithm.HMAC256(secret);
            //JWT vem da propria biblioteca
            //withIssuer é uma assinatura de que api vem este token
            //withSubject quem é o dono do token login do usuario
            //withClaim passa o id do usuario, passa outras infos
            //withExpirtesAt tempo de expiração do token
            return JWT.create().withIssuer("API Voll.Med")
                    .withSubject(usuario.getLogin())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(dataExpiracao())
                    //passa o algoritimo usada para fazer a assinatura
                    .sign(algoritmo);
        } catch (JWTCreationException exception){
                throw new RuntimeException("erro ao gerar token jwt" + exception);
        }

    }

    //verifica se o token ta valido e retorna o usuario que esta relacionado a ele
    public String getSubject(String tokenJWT){



        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("API Voll.Med")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception){
                throw new RuntimeException("Token JWT invalido ou expirado");
        }

    }


    //metodo para pegar um tempo de expiração do token
    private Instant dataExpiracao() {
        //pega hora atual e soma 2 horas e converte para instant
        //zoneoffset passa o fuso horario para fazer essa conversao
        return LocalDateTime.now().plusHours(2)
                .toInstant(ZoneOffset.of("-03:00"));
    }

}
