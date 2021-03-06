package db.highcharts;

import db.highcharts.graphs.Graph;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.json.JSONArray;

public class DBActions {

  private final Graph[] graphs = new Graph[]{
    new db.highcharts.graphs.Graph01(),
    new db.highcharts.graphs.Graph02(),
    new db.highcharts.graphs.Graph03(),
    new db.highcharts.graphs.Graph04(),
    new db.highcharts.graphs.Graph05()};

  public JSONArray getGraph(int graphId, int year) {
    return createGraph(graphId, year, -1);
  }

  public JSONArray getGraph(int graphId, int year, int month) {
    return createGraph(graphId, year, month);
  }

  private JSONArray createGraph(int graphId, int year, int month) {
    if (graphId - 1 < 0 || graphId - 1 > graphs.length) {
      return null;
    }
    Graph g = graphs[graphId - 1];
    try (DBConnection conn = new DBConnection();) {
      conn.open();

      Statement st = conn.getConection().createStatement();
      ResultSet rs;
      if (month >= 0) {
        rs = g.query(st, year, month);
      } else {
        rs = g.query(st, year);
      }
      return g.toJSON(rs);
    } catch (SQLException ex) {
      java.util.logging.Logger.getLogger(DBActions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      return null;
    }
  }
}
