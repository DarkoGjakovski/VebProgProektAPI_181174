package anemona.api.security.jwt;

import anemona.api.security.services.UserPrinciple;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Base64;
import java.util.Date;


@Component
public class JwtProvider {

    private static Logger logger = LoggerFactory.getLogger(JwtProvider.class);
    String secret = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";

    Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret),
            SignatureAlgorithm.HS256.getJcaName());

    @Value("${tutorial.app.jwtSecret}")
    private String jwtSecret;

    @Value("${tutorial.app.jwtExpiration}")
    private int jwtExpiration;

    public String generateJwtToken(Authentication authentication){
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject((userPrinciple.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime()+jwtExpiration* 1000L))
                .signWith(hmacKey)
                .compact();
    }

    public boolean validateJwtToken(String authToken){
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        }catch (SignatureException e){
            logger.error("Invalid JWT signature -> Message {}", e.getMessage());
        }catch (MalformedJwtException e){
            logger.error("Invalid JWT token -> Message {}", e.getMessage());
        }
        catch (ExpiredJwtException e){
            logger.error("Expired JWT signature -> Message {}", e.getMessage());
        }
        catch (UnsupportedJwtException e){
            logger.error("Unsupported JWT signature -> Message {}", e.getMessage());
        }
        catch (IllegalArgumentException e){
            logger.error("Illegal JWT signature -> Message {}", e.getMessage());
        }
        return false;
    }

    public String getUserNameFromJwtToken(String token){
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

}
