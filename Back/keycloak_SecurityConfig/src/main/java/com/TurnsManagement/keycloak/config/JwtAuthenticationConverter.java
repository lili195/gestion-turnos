package com.TurnsManagement.keycloak.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class JwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

    @Value("${jwt.auth.converter.principle-attribute}")
    private String principleAttribute;

    public AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        // Convertir las autoridades del token JWT
        authorities.addAll(jwtGrantedAuthoritiesConverter.convert(jwt));

        // Agregar el nombre del recurso como autoridad
        String resourceName = extractResourceName(jwt);
        if (resourceName != null) {
            authorities.add(new SimpleGrantedAuthority(resourceName));
        }

        // Crear y devolver el token de autenticación JWT
        return new JwtAuthenticationToken(jwt, authorities, getPrincipleName(jwt));
    }


    private String extractResourceName(Jwt jwt) {
        String resourceAccess;

        if(jwt.getClaim("name") == null){
            return null;
        }

        resourceAccess = jwt.getClaim("name");
        //System.out.print(resourceAccess);

        return  resourceAccess;
    }

    private String getPrincipleName(Jwt jwt) {
        if (principleAttribute != null) {
            return jwt.getClaim(principleAttribute);
        } else {
            return jwt.getSubject();
        }
    }
}