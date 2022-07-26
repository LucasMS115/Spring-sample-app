package io.github.LucasMS115.spring_sales.security.jwt;

import io.github.LucasMS115.spring_sales.domain.entity.AppUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

@Service
public class JwtService {
    @Value("${security.jwt.expiration}")
    private String expiration;

    @Value("${security.jwt.signature-key}")
    private String signatureKey; // will be used to generate/encrypt and decrypt the token

    public String generateToken(AppUser appUser) {
        long expirationString = Long.parseLong(expiration);

        LocalDateTime expirationDateHour = LocalDateTime.now().plusMinutes(expirationString);
        Instant dateInstant = expirationDateHour.atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(dateInstant);

        //other infos to be stored inside the token
        HashMap<String, Object> payloadClaims = new HashMap<>();
        payloadClaims.put("email", "user@email.com");
        payloadClaims.put("status", "banned");

        return Jwts.builder()
                .setSubject(appUser.getUsername())
                .setExpiration(date)
                .addClaims(payloadClaims)
                .signWith(SignatureAlgorithm.HS512, signatureKey)
                .compact();
    }

    private Claims getClaims(String token) throws ExpiredJwtException {
        return Jwts.parser()
                .setSigningKey(signatureKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public Boolean isTokenValid(String token) {
        try {
            Claims claims = getClaims(token);
            Date expirationDate = claims.getExpiration();
            LocalDateTime convertedExpirationDate = expirationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            return convertedExpirationDate.isAfter(LocalDateTime.now());
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsername(String token) throws ExpiredJwtException {
        return getClaims(token).getSubject();
    }

    //testing the token generation
//    public static void main(String[] args) {
//        ConfigurableApplicationContext context = SpringApplication.run(SpringSalesApplication.class);
//        JwtService service = context.getBean(JwtService.class);
//        AppUser user = AppUser.builder().username("Lucas").build();
//        String token = service.generateToken(user);
//        System.out.println(token);
//        System.out.println(service.getClaims(token));
//        System.out.println(service.isTokenValid(token));
//        System.out.println(service.getUsername(token));
//    }
}
