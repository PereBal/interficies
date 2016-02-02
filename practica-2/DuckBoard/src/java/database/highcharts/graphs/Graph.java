/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.highcharts.graphs;

/**
 *
 * @author pere
 */
public interface Graph {

  String query(int year);

  String query(int year, int month);

  org.json.JSONArray toJSON(java.sql.ResultSet rs);

}
