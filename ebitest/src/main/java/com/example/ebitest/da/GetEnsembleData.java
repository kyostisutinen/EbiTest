package com.example.ebitest.da;

import com.example.ebitest.dom.EbiTestReturnDom;

import java.sql.*;
import java.util.ArrayList;

public class GetEnsembleData {
    private ArrayList<EbiTestReturnDom> returnList  = new ArrayList<EbiTestReturnDom>();
    private final String geneName;
    private final String species;
    private final short limit;
    private final String url;
    private final String username;

    // Pass here the following parameters
    // queryParam : string that will be searched from db
    // queryDisplayLabel : string that will be searched from db
    // queryLimit : Max number of records retrieved from db
    // url : database url
    // username : database username
    public GetEnsembleData(String queryGeneName, String querySpecies, short queryLimit, String url, String username) {
        this.geneName = queryGeneName;
        this.species = querySpecies;
        this.limit = queryLimit;
        this.url = url;
        this.username = username;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public final String getGeneName() {
        return geneName;
    }

    public final String getSpecies() {
        return species;
    }

    public final short getLimit() {
        return limit;
    }

    public ArrayList<EbiTestReturnDom> fetchData() throws Exception {
        // Set SQL geneName, species, url and username (no pwd needed)
        String sqlSelectEnsembleData = "SELECT DISTINCT display_label FROM ensembl_website_102.gene_autocomplete WHERE display_label LIKE ? AND species = ? ORDER BY display_label LIMIT 0, ?";
        String connectionUrl = getUrl();
        String username = "user=" + getUsername() + "&password="; // No pwd

        try (Connection conn = DriverManager.getConnection(connectionUrl + "?" + username);
             PreparedStatement ps = conn.prepareStatement(sqlSelectEnsembleData);)
        {
            // Set parameters
            ps.setString(1, getGeneName() + "%");
            ps.setString(2, getSpecies());
            ps.setShort(3, getLimit());
            ResultSet rs = ps.executeQuery();

            // Loop and save results into ArrayList
            while (rs.next()) {
                returnList.add(new EbiTestReturnDom(rs.getString("display_label"),getSpecies()));
            }
        } catch (SQLException e) {
            System.out.println(e);
            throw new Exception(e.getMessage());
            // handle the exception
        } catch (Exception e) {
            System.out.println(e);
            throw new Exception(e.getMessage());
            // handle the exception
        }

        return returnList;
    }
}
