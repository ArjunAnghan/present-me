package com.presentme.filters;

import com.presentme.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "FacultyFilter", urlPatterns = "/faculty/*")
public class GuideFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || user.getLevel() != 1 || user.getActivated() != 1) {
            session.setAttribute("user", null);
            request.setAttribute("message", "Invalid Login Method");
            request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, resp);
        }

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
