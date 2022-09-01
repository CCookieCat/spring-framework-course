package com.ccat.springtest.dao;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

//Custom Row Mapper
public class OfferRowMapper implements RowMapper<Offer> {
    @Override
    public Offer mapRow(ResultSet rs, int i) throws SQLException {

        User user = new User();
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setName(rs.getString("name"));
        user.setAuthority(rs.getString("authority"));
        user.setEnabled(true);

        //resultSet returned from DB query - mapped to single row
        Offer offer = new Offer();

        offer.setId(rs.getInt("id")); //ID-COLUMN
        offer.setInformation(rs.getString("information")); //INFORMATION-COLUMN
        offer.setUser(user);

        return offer;
    }
}
