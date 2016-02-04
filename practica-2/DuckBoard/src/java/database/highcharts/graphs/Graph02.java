/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.highcharts.graphs;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author pere
 */
public class Graph02 implements Graph {

  private final String q = "SELECT idioma, COUNT(idioma) AS cnt_idioma, anyo, mes_num "
          + "FROM " + database.highcharts.DBProperties.DB + ".sm_procesados GROUP BY idioma ";

  @Override
  public java.sql.ResultSet query(java.sql.Statement st, int year) throws java.sql.SQLException {
    return st.executeQuery(q + "HAVING anyo=" + year + ";");
  }

  @Override
  public java.sql.ResultSet query(java.sql.Statement st, int year, int month) throws java.sql.SQLException {
    return st.executeQuery(q + "HAVING anyo=" + year + " "
            + "AND mes_num=" + month + ";");
  }

  @Override
  public JSONArray toJSON(ResultSet rs) {
    JSONArray langs = new JSONArray();
    try {
      int count = 0;
      while (rs.next()) {
        count += rs.getInt("cnt_idioma");
      }
      rs.beforeFirst();

      double val;
      final double max_count = (double) count;
      while (rs.next()) {
        val = rs.getDouble("cnt_idioma") / max_count * 100.0;
        val = new BigDecimal(Double.toString(val)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        langs.put(new JSONObject().put("name", rs.getString("idioma")).put("data", val));
      }
    } catch (SQLException ex) {
      java.util.logging.Logger.getLogger(Graph02.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    return langs;
  }

}
