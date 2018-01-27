package richk.RMS.web.account;

import richk.RMS.Session;
import richk.RMS.database.DatabaseException;
import richk.RMS.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet({"/SignUp"})
public class SignUp extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final int keyLength = 32;
    private String password;

    public SignUp() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        Session session = (Session) httpSession.getAttribute("session");
        if (session == null) {
            try {
                session = new Session();
                httpSession.setAttribute("session", session);
            } catch (DatabaseException e) {
                httpSession.setAttribute("error", e);
                request.getRequestDispatcher("JSP/error.jsp").forward(request, response);
            }
        }

        try {
            String email = request.getParameter("email");
            String pass = request.getParameter("password");
        //    String name = request.getParameter("name");
        //    String lastname = request.getParameter("lastname");


            if (email != null) {
                // se l'email è già presente nel DB
                if (session.getDatabaseManager().IsUserPresent(email)) {
                    // TODO password gia presente vuoi recuperarla? guarda se html o popup js
                } else {
                    if (pass != null) {
                        if (pass.length() >= 8) {

                            // inserisce in entrambi i db
                            session.getDatabaseManager().AddUser(new User(email, pass));
                            // se sono stati messi mette nel DB anche nome e cognome
//                            if (name != null && lastname != null) {
//                                session.getDatabaseManager().EditPerson(new Person(email, name, lastname, null, null, null, null, null, null, null, null, null));
//                            }

                            // metti persona nella sessione
//                            session.setPerson(session.getDatabaseManager().GetPerson(email));

                            //httpSession.setAttribute("emailUser", email);
                            //response.sendRedirect("controlPanel.html");

                            //response.setHeader("Location", "/controlPanel.html");

                            response.sendRedirect("controlPanel.html");

                            //request.getRequestDispatcher("controlPanel.html").forward(request, response);
                        } else {
                            // pass corta
                            // TODO rimanda da qualche parte perche c'è errore
                            httpSession.setAttribute("error", "pass corta");
                            request.getRequestDispatcher("JSP/error.jsp").forward(request, response);
                        }
                    } else {
                        // mancano email o password
                        // TODO rimanda da qualche parte perche c'è errore
                        httpSession.setAttribute("error", "mancano email o password");
                        request.getRequestDispatcher("JSP/error.jsp").forward(request, response);
                    }
                }

            }


            //PrintWriter printWriter = response.getWriter();
            //printWriter.println("PTO");

//            String data = request.getParameter("email");
//            printWriter.println(data);
//            printWriter.flush();


        } catch (Exception e) {
            httpSession.setAttribute("error", e);
            request.getRequestDispatcher("JSP/error.jsp").forward(request, response);
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

}