package com.example.redit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;


@AllArgsConstructor
@Entity
@Table(name = "token")
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    public VerificationToken() {//Long id, String token, User user, Instant expiryDate) {
//		super();
//		this.id = id;
//		this.token = token;
//		this.user = user;
//		this.expiryDate = expiryDate;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Instant getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Instant expiryDate) {
		this.expiryDate = expiryDate;
	}
	private String token;
    @OneToOne(fetch = LAZY)
    private User user;
    private Instant expiryDate;
}