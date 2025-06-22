package school.sorokin.eventmanager.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import school.sorokin.eventmanager.model.User;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtTokenManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenManager.class);
    private final Key secretKey;
    public final Long expirationTime;

    public JwtTokenManager(
            @Value("${jwt.sign-key}") String secretKey,
            @Value("${jwt.lifetime}") Long expirationTime) {
        this.secretKey = new SecretKeySpec(
                secretKey.getBytes(StandardCharsets.UTF_8),
                SignatureAlgorithm.HS256.getJcaName()
        );
        this.expirationTime = expirationTime;
    }

    public boolean isTokenValid(String jwtToken) {
        try {
            Jwts.parser()
                    .setSigningKey(secretKey)
                    .build()
                    .parse(jwtToken);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public String generateJwtToken(User user) {
        LOGGER.info("Generate jwt token for login = {}", user.login());
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.role().name());
        return Jwts
                .builder()
                .claims(claims)
                .subject(user.login())
                .signWith(secretKey)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .compact();
    }

    public String getLoginFromToken(String jwt) {
        LOGGER.info("Get login from jwt token = {} ", jwt);
        return Jwts
                .parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(jwt)
                .getPayload()
                .getSubject();
    }

    public String getRoleFromToken(String jwt) {
        LOGGER.info("Get role from jwt token = {} ", jwt);
        return Jwts
                .parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(jwt)
                .getPayload()
                .get("role", String.class);
    }
}