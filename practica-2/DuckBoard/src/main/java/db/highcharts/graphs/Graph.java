/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.highcharts.graphs;

/**
 *
 * @author pere
 */
public interface Graph {

  java.sql.ResultSet query(java.sql.Statement st, int year) throws java.sql.SQLException;

  java.sql.ResultSet query(java.sql.Statement st, int year, int month) throws java.sql.SQLException;

  org.json.JSONArray toJSON(java.sql.ResultSet rs);

}
