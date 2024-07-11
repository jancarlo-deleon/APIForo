package com.jdeleonc.foro.forohub.security.filter;

import com.jdeleonc.foro.forohub.model.Usuario;
import com.jdeleonc.foro.forohub.repository.UsuarioRepository;
import com.jdeleonc.foro.forohub.security.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Obtener el token JWT

        var authHeader = request.getHeader("Authorization");//

        if (authHeader != null){

            var token  =authHeader.replace("Bearer ", "");
            var nombreUsuario = tokenService.getSubject(token); //Extraer username

            if (nombreUsuario != null){
                //Token Valido
                Usuario usuario = (Usuario) usuarioRepository.findByUsername(nombreUsuario);
                var authentication = new UsernamePasswordAuthenticationToken(usuario,null,
                        usuario.getAuthorities()); //Forzamos inicio de sesion
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        }
        filterChain.doFilter(request,response);
    }
}