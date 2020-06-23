package edu.phones.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class InfrastructureSessionFilter extends OncePerRequestFilter {

    @Autowired
    private SessionManager sessionManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String sessionToken = request.getHeader("Authorization");

        if("admin".equals(sessionToken)){ // Valido que sea empleado
            filterChain.doFilter(request, response);
        }else{
            response.setStatus(HttpStatus.FORBIDDEN.value());   // Si el token es null (invalido) envia un status 403
        }                                                       // sin dejarlo entrar al metodo del controller

    }
}
