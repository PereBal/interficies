package db.highcharts.graphs;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONObject;

public class Graph01 implements Graph {

  private static final String[] FIELDS = new String[]{"mallorca", "menorca", "ibiza", "formentera", "baleares", "cine",
    "playa", "baile", "teatro", "musica", "conciertos", "restaurantes", "arte", "hotel"};

  private String q;

  public Graph01() {
    q = "SELECT ";
    for (String fname : FIELDS) {
      q += "COUNT(T_" + fname + ") AS " + fname + ",";
    }
    q += "anyo,mes_num ";
    q += "FROM " + db.highcharts.Sample.DB + ".sm_procesados ";
  }

  @Override
  public java.sql.ResultSet query(java.sql.Statement st, int year) throws java.sql.SQLException {
    return st.executeQuery(q + "HAVING " + db.highcharts.Sample.DB + ".sm_procesados.anyo=" + year + ";");
  }

  @Override
  public java.sql.ResultSet query(java.sql.Statement st, int year, int month) throws java.sql.SQLException {
    return st.executeQuery(q + "HAVING " + db.highcharts.Sample.DB + ".sm_procesados.anyo=" + year + " "
            + "AND " + db.highcharts.Sample.DB + ".sm_procesados.mes_num=" + month + ";");
  }

  @Override
  public JSONArray toJSON(ResultSet rs) {
    JSONArray tend = new JSONArray();
    try {
      if (rs.next()) {
        for (String fname : FIELDS) {
          tend.put(new JSONObject().put("name", fname).put("data", new JSONArray().put(rs.getInt(fname))));
        }
      }
    } catch (SQLException ex) {
      java.util.logging.Logger.getLogger(Graph01.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    return tend;
  }

}
