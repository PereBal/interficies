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
public class Graph04 implements Graph {

  private static final String q = "SELECT "
          + "CASE "
          + " WHEN mes_num BETWEEN 3 AND 5 THEN 'primavera'"
          + " WHEN mes_num BETWEEN 6 AND 9 THEN 'verano'"
          + " WHEN mes_num BETWEEN 10 AND 12 THEN 'otoño'"
          + " WHEN mes_num BETWEEN 1 AND 2 THEN 'invierno' "
          + "END AS estacion,"
          + "isla, COUNT(isla) AS cnt_isla, anyo, mes_num "
          + "FROM " + database.highcharts.DBProperties.DB + ".sm_procesados "
          + "GROUP BY estacion, isla "
          + "HAVING ";

  @Override
  public java.sql.ResultSet query(java.sql.Statement st, int year) throws java.sql.SQLException {
    return st.executeQuery(q + "anyo=" + year + " ORDER BY estacion;");
  }

  @Override
  public java.sql.ResultSet query(java.sql.Statement st, int year, int month) throws java.sql.SQLException {
    throw new UnsupportedOperationException("We agreed on this, it's unnecessary. Go have fun elsewhere!");
  }

  @Override
  public JSONArray toJSON(ResultSet rs) {
    JSONArray seasons = new JSONArray();
    try {
      String cseason;
      String prseason = null;
      JSONObject seasonMap = new JSONObject();
      JSONArray seasonValues = new JSONArray();

      while (rs.next()) {
        cseason = rs.getString("estacion");
        if (prseason != null && !cseason.equals(prseason)) {
          seasonMap.put(prseason, seasonValues);
          seasons.put(seasonMap);
          seasonMap = new JSONObject();
          seasonValues = new JSONArray();
        }
        seasonValues.put(new JSONObject().put(rs.getString("isla"), rs.getInt("cnt_isla")));
        prseason = cseason;
      }
      seasonMap.put(prseason, seasonValues);
      seasons.put(seasonMap);
    } catch (SQLException ex) {
      java.util.logging.Logger.getLogger(Graph04.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    return seasons;
  }

}
