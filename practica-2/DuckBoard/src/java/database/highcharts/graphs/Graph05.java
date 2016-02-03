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
public class Graph05 implements Graph {

  private final String q;

  public Graph05() {
    q = "SELECT isla, COUNT(isla) AS cnt_isla, anyo, mes_num FROM " + database.highcharts.DBProperties.DB + ".sm_procesados "
            + "GROUP BY isla ";
  }

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
    JSONArray island = new JSONArray();
    try {

      if (rs.next()) {
        int count = rs.getInt("cnt_isla");
        do {
          count += rs.getInt("cnt_isla");
        } while (rs.next());
        rs.beforeFirst();

        double val;
        final double max_val = (double) count;
        while (rs.next()) {
          val = (rs.getDouble("cnt_isla") / max_val) * 100.0;
          val = new BigDecimal(Double.toString(val)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
          island.put(new JSONObject().put(rs.getString("isla"), val));
        }
      }
    } catch (SQLException ex) {
      java.util.logging.Logger.getLogger(Graph05.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    return island;
  }

}
