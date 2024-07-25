package com.maizi.common.redis;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * JwtUtil 是一个用于生成和验证 JWT 的工具类。
 * 用户登录成功后，服务端会颁发一个包含用户信息和有效期的JWT给客户端，
 * 客户端随后每次请求携带JWT，服务端可以验证JWT的合法性和有效期来识别用户身份。
 */
@Component
public class JwtUtil {


    // @Value("${jwt.secret}")  // 从配置文件中注入 JWT 密钥
    private String secret = "your-very-strong-and-secure-secret-key";


    // @Value("${jwt.expiration}")// 从配置文件中注入 JWT 过期时间（以秒为单位）
    private long expiration = 3600L;

    /**
     * 生成 JWT 令牌
     *
     * @param username 用户名
     * @return 生成的 JWT 令牌
     */
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    /**
     * 创建 JWT 令牌
     *
     * @param claims  载荷
     * @param subject 主题（通常是用户名）
     * @return 生成的 JWT 令牌
     */
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims) // 设置载荷
                .setSubject(subject) // 设置主题
                .setIssuedAt(new Date(System.currentTimeMillis())) // 设置签发时间
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000)) // 设置过期时间
                .signWith(SignatureAlgorithm.HS256, secret) // 设置签名算法和密钥
                .compact();
    }


    /**
     * 从令牌中获取用户名
     *
     * @param token JWT 令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * 从令牌中获取过期日期
     *
     * @param token JWT 令牌
     * @return 过期日期
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * 从令牌中获取特定的声明
     *
     * @param <T>            声明类型
     * @param token          JWT 令牌
     * @param claimsResolver 声明解析器
     * @return 声明的值
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * 验证 JWT 令牌
     *
     * @param token    JWT 令牌
     * @param username 用户名
     * @return 是否有效
     */
    public Boolean  validateToken(String token, String username) {
        final String extractedUsername = getUsernameFromToken(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    /**
     * 解析令牌获取所有声明
     *
     * @param token JWT 令牌
     * @return 声明
     */
    private Claims getAllClaimsFromToken(String token) {

        return Jwts.parser().setSigningKey(secret) // 设置签名密钥
                .parseClaimsJws(token) // 解析令牌
                .getBody(); // 获取载荷
    }

    /**
     * 检查令牌是否过期
     *
     * @param token JWT 令牌
     * @return 是否过期
     */
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
}
