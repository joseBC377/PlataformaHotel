package com.example.hotel.config;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.jsonwebtoken.security.Keys;
import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "application.jwt")
public class JwtConfig {

    //Escritura calmel case, sera escrito en kebab case en el application.properties
    private String secretKey; 
    private String tokenPrefix;
    private Integer tokenExpirationAfterDays; //En dias
    private Integer refreshTokenExpirationAfterDays; //En dias  , este token es de refrezco es decir cuando expira el token principal ,con este token de refrezco solicita uno nuevo sin tener que volver a iniciar sesion

    // Acces token, Metodos para obtener la expiracion  en milisegundos (El nombre del metodo puede ser cualquiera solo tiene que ser igual cuando se llama en JwtService)
    public long getTokenExpirationInMillis(){
        return tokenExpirationAfterDays * 24 * 60 * 60 * 1000;
    }

    //Refrech token 
    public long getRefreshTokenExpirationInMillis(){
        return refreshTokenExpirationAfterDays * 24 * 60 * 60 * 1000;
    }

    //El secret key conocido como clave secreta ,se usa para firmar y verificar el token
    @Bean
    SecretKey secretKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }


}
