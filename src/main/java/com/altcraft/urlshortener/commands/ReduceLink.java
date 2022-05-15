package com.altcraft.urlshortener.commands;

import com.altcraft.urlshortener.datalayer.LinkInfo;
import com.altcraft.urlshortener.datalayer.dao.MySqlDAO;
import com.altcraft.urlshortener.model.LinkShortener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class ReduceLink implements Command {
    private static final Logger logger = Logger.getLogger(ReduceLink.class);
    private static final String LOG_ERROR_MESSAGE = "Dispatcher error";
    private static final String ORIGINAL_LINK = "originalLink";
    private static final String SHORTED_LINK = "shortedLink";
    private static final String HIDDEN = "hidden";
    private static final String MAIN_PAGE_PATH = "/mainPage.jsp";
    private static final String DATEFORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final int SUCCESSFUL_STATUS = 200;
    private final LinkShortener linkShortener;
    private final MySqlDAO mySqlDAO;

    public ReduceLink() {
        this.linkShortener = new LinkShortener();
        this.mySqlDAO = new MySqlDAO();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        if (!Objects.equals(request.getParameter(ORIGINAL_LINK), "")) {
            String shortedLink = mySqlDAO.findHash(request.getParameter(ORIGINAL_LINK));

            if (shortedLink == null) {
                LinkInfo linkInfo = new LinkInfo();

                shortedLink = linkShortener.reduceLinkHashids(request.getParameter(ORIGINAL_LINK));

                linkInfo.setOriginalURL(request.getParameter(ORIGINAL_LINK));
                linkInfo.setShortedURL(shortedLink);
                linkInfo.setTimeCreated(getTime());
                mySqlDAO.saveLink(linkInfo);
            }

            request.getServletContext().setAttribute(SHORTED_LINK, shortedLink);
            request.getServletContext().setAttribute(HIDDEN, true);
            response.setStatus(SUCCESSFUL_STATUS);
        }

        ServletContext servletContext = request.getServletContext();
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(MAIN_PAGE_PATH);
        try {
            requestDispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error(LOG_ERROR_MESSAGE, e);
        }
    }

    private String getTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATEFORMAT);

        return simpleDateFormat.format(date);
    }
}
