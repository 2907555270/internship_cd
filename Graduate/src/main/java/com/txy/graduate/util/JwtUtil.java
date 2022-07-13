package com.txy.graduate.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
@ConfigurationProperties(prefix = "markerhub.jwt")
public class JwtUtil {

	private String secret;
	private String header;
	private long expireTime;

	/**
	 * 生产token
	 * @param userName
	 * @return
	 */
	public String createToken(String userName){
		Date now = new Date();
		Date expireDate = new Date(now.getTime() + 1000 * expireTime);

		return Jwts.builder()
				.setHeaderParam("typ","JWT")
				.setSubject(userName)
				.setIssuedAt(now)
				.setExpiration(expireDate)// 7天过期时间
				.signWith(SignatureAlgorithm.HS512, secret)// 设置512加密算法
				.compact();//合成
	}

	/**
	 * 解析token
	 * @param token
	 * @return
	 */
	public Claims getClaimByToken(String token){
		try {
			return Jwts.parser()
					.setSigningKey(secret)
					.parseClaimsJws(token)
					.getBody();
		}catch (Exception e){
			return null;
		}
	}

	/**
	 * 判断token是否过期
	 * @param claims
	 * @return
	 */
	public boolean isTokenExpired(Claims claims){
		return claims.getExpiration().before(new Date());
	}



}
