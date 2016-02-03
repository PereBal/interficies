
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
import org.json.JSONArray;

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
