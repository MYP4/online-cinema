package filter;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


import java.io.IOException;

import static util.UrlPathUtil.*;

@WebFilter("/*")
public class AurhorizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);

        boolean isLoginOrRegistrationPage = httpRequest.getRequestURI().equals(httpRequest.getContextPath() + LOGIN) ||
                httpRequest.getRequestURI().equals(httpRequest.getContextPath() + REGISTRATION);

        if (!isLoggedIn && !isLoginOrRegistrationPage) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + LOGIN);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
    }
}
