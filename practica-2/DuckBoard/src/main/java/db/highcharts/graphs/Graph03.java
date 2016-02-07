/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.highcharts.graphs;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author pere
 */
public class Graph03 implements Graph {

  private String q = "SELECT polaridad, dia_sem, count(dia_sem) AS cnt_dia_sem, anyo, mes_num "
          + "FROM " + db.highcharts.DBProperties.DB + ".sm_procesados "
          + "GROUP BY polaridad, dia_sem "
          + "HAVING polaridad IS NOT NULL ";

  @Override
  public java.sql.ResultSet query(java.sql.Statement st, int year) throws java.sql.SQLException {
    return st.executeQuery(q + "AND anyo=" + year + ";");
  }

  @Override
  public java.sql.ResultSet query(java.sql.Statement st, int year, int month) throws java.sql.SQLException {
    return st.executeQuery(q + "AND anyo=" + year + " AND mes_num=" + month + ";");
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
          week_polarity.put("name", prpolar).put("data", week);
          polarity.put(week_polarity);
          week_polarity = new JSONObject();
          week = new JSONArray();
        }
        week.put(rs.getInt("cnt_dia_sem"));
        prpolar = cpolar;
      }
      week_polarity.put("name", prpolar).put("data", week);
      polarity.put(week_polarity);
    } catch (SQLException ex) {
      java.util.logging.Logger.getLogger(Graph03.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    return polarity;
  }

}
