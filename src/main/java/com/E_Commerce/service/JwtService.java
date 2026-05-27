package com.E_Commerce.service;

import java.security.Key;

import java.util.Date;

import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private final String SECRET="thisisaverylongsecretkeythisisaverylongsecretkey";
    private final Key key=Keys.hmacShaKeyFor(SECRET.getBytes());
    public String generate(String email){
        return Jwts.builder().subject(email).issuedAt(new Date()).expiration(new Date(
            System.currentTimeMillis()+86400000)).signWith(key).compact();
        }

}