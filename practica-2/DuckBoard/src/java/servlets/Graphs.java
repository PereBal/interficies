/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import servlets.tools.Helper;

/**
 *
 * @author pere
 */
@WebServlet(name = "Graphs", urlPatterns = {"/graphs/*"})
public class Graphs extends HttpServlet {

  private int toMonthNumber(String monthName) throws Exception {
    final String[] months = {"enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre",
      "octubre", "noviembre", "diciembre"
    };
    int i;
    for (i = 0; i < 12; i++) {
      if (months[i].equals(monthName)) {
        return i + 1;
      }
    }
    throw new Exception();
  }

  // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
  /**
   * Handles the HTTP <code>GET</code> method. Maps all gets to: /graphs/:graphId?year=&month=
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {

    if (Helper.isAjax(request)) {
      try {
        JSONArray graph;
        int graphId = Integer.parseInt(request.getPathInfo().replaceAll("[^0-9]", ""));

        String dirtyMonth = request.getParameter("month");
        int year = Integer.parseInt(request.getParameter("year").replaceAll("[^0-9]", ""));

        db.highcharts.DBActions db = new db.highcharts.DBActions();
        if (dirtyMonth == null) {
          graph = db.getGraph(graphId, year);
        } else {
          graph = db.getGraph(graphId, year, toMonthNumber(dirtyMonth.toLowerCase().replaceAll("[^a-z]", "")));
        }

        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
          out.println(graph.toString());
        }
      } catch (Exception ex) {
        java.util.logging.Logger.getLogger(Graphs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        response.setContentType("text/plain;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

          out.println("###:::: ##:::  #####:::: ######:::: ######");
          out.println("####::: ##::: ##.. ##::: ##.. ##::: ##... ");
          out.println("## ##:: ##::: ##.. ##::: ##.. ##::: ##... ");
          out.println("##: ##: ##::: ##.. ##::: ######:::: ##### ");
          out.println("##:: ## ##::: ##.. ##::: ##:::::::: ##... ");
          out.println("##::: ####::: ##.. ##::: ##:::::::: ##... ");
          out.println("##:::: ###:::  #####:::: ##:::::::: ######");
        }
      }
    } else {
      
      request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
    }

  }

  /**
   * Handles the HTTP <code>POST</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    // SKIP Post Requests
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
