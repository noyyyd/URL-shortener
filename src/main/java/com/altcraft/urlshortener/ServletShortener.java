package com.altcraft.urlshortener;

import com.altcraft.urlshortener.commands.Command;
import com.altcraft.urlshortener.commands.ReduceLink;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "shortenerServlet", value = "/mainPage")
public class ServletShortener extends HttpServlet {
    private static final String HIDDEN = "hidden";
    private static final String REDUCE = "reduce";
    private static final String COMMAND = "command";

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        request.getServletContext().setAttribute(HIDDEN, false);

        if (request.getParameter(COMMAND) != null) {
            Command command = defineCommand(request.getParameter(COMMAND));
            command.execute(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        doGet(req, resp);
    }

    private Command defineCommand(String commandName)  {
        if (commandName.equals(REDUCE)) {
            return new ReduceLink();
        } else {
            throw new IllegalArgumentException();
        }
    }
}