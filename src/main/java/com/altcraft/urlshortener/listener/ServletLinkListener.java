package com.altcraft.urlshortener.listener;

import com.altcraft.urlshortener.datalayer.dao.MySqlDAO;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

@WebServlet(name = "linkListenerServlet", value = "/l/*")
public class ServletLinkListener extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ServletLinkListener.class);
    private static final String DISPATCHER_ERROR_MESSAGE = "Dispatcher error";
    private static final String REDIRECT_ERROR_MESSAGE = "Redirect error";
    private static final String ERROR_PAGE_PATH = "/errorPage.jsp";
    private static final int ERROR_STATUS = 200;
    private static final int HASH = 24;
    private final MySqlDAO mySqlDAO = new MySqlDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String originalLink = mySqlDAO.findOriginalLink(request.getRequestURL().toString().substring(HASH));

        if (originalLink != null) {
            try {
                response.sendRedirect(originalLink);
            } catch (IOException e) {
                logger.error(REDIRECT_ERROR_MESSAGE, e);
            }
        } else {
            response.setStatus(ERROR_STATUS);
            ServletContext servletContext = request.getServletContext();
            RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(ERROR_PAGE_PATH);
            try {
                requestDispatcher.forward(request, response);
            } catch (ServletException | IOException e) {
                logger.error(DISPATCHER_ERROR_MESSAGE, e);
            }
        }
    }
}
