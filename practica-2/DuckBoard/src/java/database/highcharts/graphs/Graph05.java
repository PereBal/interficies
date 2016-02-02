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

  private static final int LIM_FIELDS = 5;
  private static final String[] FIELDS = new String[]{"mallorca", "menorca", "ibiza", "formentera", "baleares", "cine", "playa",
    "baile", "teatro", "musica", "conciertos", "restaurantes", "arte", "hotel"};

  private String q;

  public Graph05() {
    q = "SELECT ";
    int i = 0;
    for (; i < LIM_FIELDS; i++) {
      q += "COUNT(T_" + FIELDS[i] + ") AS " + FIELDS[i] + ",";
    }
    for (int len = FIELDS.length - 1; i < len; i++) {
      q += "COUNT(T_" + FIELDS[i] + ")+";
    }
    q += "COUNT(T_" + FIELDS[i] + ") AS FueradeB,";

    i = 0;
    for (int len = LIM_FIELDS; i < len; i++) {
      q += "COUNT(T_" + FIELDS[i] + ")+";
    }
    q += "COUNT(T_" + FIELDS[i] + ") AS sum_total,";

    q += "anyo,mes_num ";
    q += "FROM " + database.highcharts.DBProperties.DB + ".sm_procesados ";
  }

  @Override
  public String query(int year) {
    System.out.println(q);
    return q + "HAVING anyo=" + year + ";";
  }

  @Override
  public String query(int year, int month) {
    return q + "HAVING anyo=" + year + " "
            + "AND mes_num=" + month + ";";
  }

  @Override
  public JSONArray toJSON(ResultSet rs) {
    JSONArray island = new JSONArray();
    try {
      if (rs.next()) {
        int i;
        double val;
        final double max_val = rs.getDouble("sum_total");
        for (i = 0; i < LIM_FIELDS; i++) {
          val = (rs.getDouble(FIELDS[i]) / max_val) * 100.0;
          val = new BigDecimal(Double.toString(val)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
          island.put(new JSONObject().put(FIELDS[i], val));
        }
        val = (rs.getDouble("FueradeB") / max_val) * 100.0;
        val = new BigDecimal(Double.toString(val)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        island.put(new JSONObject().put("FueradeB", val));
      }
    } catch (SQLException ex) {
      java.util.logging.Logger.getLogger(Graph05.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    return island;
  }

}
