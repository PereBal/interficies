
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.highcharts;

import database.highcharts.graphs.Graph;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.json.JSONObject;

/**
 *
 * @author PereBal
 * @author dobleme
 */
public class DBActions {

  private final Graph[] graphs = new Graph[]{
    new database.highcharts.graphs.Graph01(),
    new database.highcharts.graphs.Graph02(),
    new database.highcharts.graphs.Graph03(),
    new database.highcharts.graphs.Graph04(),
    new database.highcharts.graphs.Graph05()};

  public JSONObject getGraph(int graphId, int year) {
    if (graphId-1 < 0 || graphId-1 > graphs.length) {
      return null;
    }
    Graph g = graphs[graphId-1];
    try (DBConnection conn = new DBConnection();) {
      conn.open();

      Statement st = conn.getConection().createStatement();
      ResultSet rs = st.executeQuery(g.query(year));
      return g.toJSON(rs);
    } catch (SQLException ex) {
      ex.printStackTrace();
      return null;
    }
  }

  public JSONObject getGraph(int graphId, int year, int month) {
    if (graphId-1 < 0 || graphId-1 > graphs.length) {
      return null;
    }
    Graph g = graphs[graphId-1];
    try (DBConnection conn = new DBConnection();) {
      conn.open();

      Statement st = conn.getConection().createStatement();
      ResultSet rs = st.executeQuery(g.query(year, month));
      return g.toJSON(rs);
    } catch (SQLException ex) {
      ex.printStackTrace();
      return null;
    }
  }
}
