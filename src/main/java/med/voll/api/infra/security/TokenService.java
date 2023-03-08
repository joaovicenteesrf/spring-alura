package med.voll.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import med.voll.api.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

// Classe responsável por gerar os tokens JWT
@Service
public class TokenService {

    /*
    O tratamento abaixo é feito por questões de segurança. Na linha 27, entre parênteses é passada a senha do token, mas
    não é ideal que seja explicitado no código. No arquivo application.properties, linha 10, foi especificado que a senha
    será lida através de uma variável de ambiente, e caso não esteja disponível, que seja utilizada a senha padrão.

    A anotação 'Value' informa que essa variável secret deve receber o valor informado lá.
     */
    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(Usuario usuario) {
        System.out.println(secret);
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                    // Identificar API que é responsável pela geração do token
                    .withIssuer("API Voll.med")
                    // Identifica o usuário "dono" do token
                    .withSubject(usuario.getLogin())
                    // Data de expiração do Token
                    .withExpiresAt(dataExpiracao())
                    .sign(algoritmo);
        } catch (JWTCreationException exception){
            throw new RuntimeException("erro ao gerrar token jwt", exception);
        }
    }

    private Instant dataExpiracao() {
        // Adiciona expiração do token após 2h
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
