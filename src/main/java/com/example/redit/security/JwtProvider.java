package com.example.redit.security;

import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.time.Instant;
import static java.util.Date.from;
import javax.annotation.PostConstruct;
import com.example.redit.exceptions.SpringRedditException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import com.example.redit.exceptions.SpringRedditException;
import com.example.redit.model.User;
import com.example.redit.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import static io.jsonwebtoken.Jwts.parser;
import static java.util.Date.from;


@Service
public class JwtProvider{
	private KeyStore keyStore;
	@Value("${jwt.expiration.time}")
	private Long jwtExpirationInMillis;

	@PostConstruct
	public void init() {
		try {
			keyStore=KeyStore.getInstance("JKS");
			InputStream resourceAsStream= getClass().getResourceAsStream("/springblog.jks");
			keyStore.load(resourceAsStream,"secret".toCharArray());
		}catch(KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
			throw new SpringRedditException("Exception occured while loading keystore");
		}
	}
	
	public String generateToken(Authentication authentication) {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
		return Jwts.builder()
				.setSubject(principal.getUsername())
				.signWith(getPrivateKey())
				.setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
				.compact();
	}

	public String generateTokenWithUserName(String username) {
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(from(Instant.now()))
				.signWith(getPrivateKey())
				.setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
				.compact();
	}



	private PrivateKey getPrivateKey() {
		try {
			return (PrivateKey)keyStore.getKey("springblog","secret".toCharArray());
		}catch(KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
			throw new SpringRedditException("Exception occured while receiving key from keystore");
		}
	}

    public boolean validateToken(String jwt) {
        parser().setSigningKey(getPublicKey()).parseClaimsJws(jwt);
        return true;
    }

	private  PublicKey getPublicKey() {
		try {
			return keyStore.getCertificate("springblog").getPublicKey();
		}catch(KeyStoreException e) {
			throw new SpringRedditException("Exception occured while receiving public key from keystore");
		}
	}
	
	public String getUsernameFromJwt(String token) {
		Claims claims=parser().setSigningKey(getPublicKey()).parseClaimsJws(token).getBody();
		return claims.getSubject();
	}

	public Long getJwtExpirationInMillis() {
		return jwtExpirationInMillis;
	}
}
