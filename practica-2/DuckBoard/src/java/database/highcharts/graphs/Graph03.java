/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.highcharts.graphs;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author pere
 */
public class Graph03 implements Graph {

  private String q = "SELECT polaridad, dia_sem, count(dia_sem) AS count_dia_sem, anyo, mes_num "
          + "FROM " + database.highcharts.DBProperties.DB + ".sm_procesados "
          + "GROUP BY polaridad, dia_sem "
          + "HAVING polaridad IS NOT NULL ";

  @Override
  public String query(int year) {
    return q + "AND anyo=" + year + ";";
  }

  @Override
  public String query(int year, int month) {
    return q + "AND anyo=" + year + " AND mes_num=" + month + ";";
  }

  @Override
  public JSONArray toJSON(ResultSet rs) {
    JSONArray polarity = new JSONArray();
    try {
      String cpolar;
      String prpolar = null;
      JSONObject week_polarity = new JSONObject();
      JSONArray week = new JSONArray();

      while (rs.next()) {
        cpolar = rs.getString("polaridad");
        if (prpolar != null && !cpolar.equals(prpolar)) {
          week_polarity.put(prpolar, week);
          polarity.put(week_polarity);
          week_polarity = new JSONObject();
          week = new JSONArray();
        }
        week.put(new JSONObject().put(rs.getString("dia_sem"), rs.getInt("count_dia_sem")));
        prpolar = cpolar;
      }
    } catch (SQLException ex) {
      java.util.logging.Logger.getLogger(Graph03.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    return polarity;
  }

}
