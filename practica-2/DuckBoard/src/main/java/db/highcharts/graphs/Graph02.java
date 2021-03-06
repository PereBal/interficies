package db.highcharts.graphs;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;

public class Graph02 implements Graph {

  private final String q = "SELECT idioma, COUNT(idioma) AS cnt_idioma, anyo, mes_num "
          + "FROM " + db.highcharts.Sample.DB + ".sm_procesados GROUP BY idioma ";

  @Override
  public java.sql.ResultSet query(java.sql.Statement st, int year) throws java.sql.SQLException {
    return st.executeQuery(q + "HAVING anyo=" + year + ";");
  }

  @Override
  public java.sql.ResultSet query(java.sql.Statement st, int year, int month) throws java.sql.SQLException {
    return st.executeQuery(q + "HAVING anyo=" + year + " "
            + "AND mes_num=" + month + ";");
  }

  /*
   {
                    name: 'Proprietary or Undetectable',
                    y: 0.2,
                    dataLabels: {
                        enabled: false
                    }
                }

  */
  
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
        langs.put(new JSONArray().put(rs.getString("idioma")).put(val));
      }
    } catch (SQLException ex) {
      java.util.logging.Logger.getLogger(Graph02.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    return langs;
  }

}
