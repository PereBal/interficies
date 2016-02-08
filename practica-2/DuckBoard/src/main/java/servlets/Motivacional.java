package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import servlets.tools.Sesion;

/**
 *
 * @author bcrespi
 */
@WebServlet(name = "Motivacional", urlPatterns = {"/motivational"})
public class Motivacional extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {

    Sesion session = new Sesion(request.getSession());

    if (!Sesion.isAutenticated(session)) {
      response.sendRedirect("/duckboard");
      return;
    }
    
    response.setContentType("text/plain;charset=UTF-8");
    request.getRequestDispatcher("/motivational.jsp").forward(request, response);
  }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
