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

/**
 *       Interceptor
 *
 *  Filtra los request antes que lleguen al controller.
 *  Registrado en spring en el Configuration
 */

@Service
public class SessionFilter extends OncePerRequestFilter {

    @Autowired
    private SessionManager sessionManager;

    //Filtra los request fijandose que esten logueados
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String sessionToken = request.getHeader("Authorization");
        Session session = sessionManager.getSession(sessionToken);

        if(null!= session){
            filterChain.doFilter(request, response);
        }else{
            response.setStatus(HttpStatus.FORBIDDEN.value());   // Si el token es null envia un status 403
        }                                                       // sin dejarlo entrar al metodo del controller

    }


}
