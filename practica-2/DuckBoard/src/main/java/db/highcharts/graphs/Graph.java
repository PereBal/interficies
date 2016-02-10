package db.highcharts.graphs;

public interface Graph {

  java.sql.ResultSet query(java.sql.Statement st, int year) throws java.sql.SQLException;

  java.sql.ResultSet query(java.sql.Statement st, int year, int month) throws java.sql.SQLException;

  org.json.JSONArray toJSON(java.sql.ResultSet rs);

}
