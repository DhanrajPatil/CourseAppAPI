/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import java.util.List;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import models.Users;
import services.UserService;

/**
 *
 * @author Dhanraj Patil
 */
@Provider
public class SecurityFilter implements ContainerRequestFilter {

    final static private String AUTHENTICATION_HEADER_KEY = "Authorization";
    final static private String AUTH_KEY_SUFFIX = "authorize";
    final static private String REQUEST_METHOD_OPT = "OPTIONS";
    
    @Override
    public void filter(ContainerRequestContext requestContext) {
        
        // if 'authorize' request go to request without filter.
         // if request method is "OPTIONS" then allow it to proceed without any authentication
        if( requestContext.getUriInfo().getPath().contains(AUTH_KEY_SUFFIX) ||
                requestContext.getMethod().equals(REQUEST_METHOD_OPT)) {
            return;
        }
            
        //getting headers from request
        List<String> headerKey = requestContext.getHeaders().get(AUTHENTICATION_HEADER_KEY);
        Users user = null;

        UserService service = new UserService();
        Response unauthorized =  Response
                        .status(Response.Status.UNAUTHORIZED)
                        .build();
        if(headerKey != null && headerKey.size() > 0){
            String authValue = headerKey.get(0);
            user = service.logInCheck(authValue);
            if(user == null){
                requestContext.abortWith( unauthorized );
            }else{
                return;
            }
        }else{
            // aborting request with unauthorized status bacause auth key not present
            requestContext.abortWith( unauthorized );
        }
    }
    
}
