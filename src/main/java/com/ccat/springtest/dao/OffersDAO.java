package com.ccat.springtest.dao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

@Repository //For Exception Translation
@Component("offersDao")
@Transactional
public class OffersDAO {
    private NamedParameterJdbcTemplate jdbc;

    //pass the Datasource from datasource-bean
    @Autowired
    public void setDataSource(DataSource jdbc) {
        this.jdbc = new NamedParameterJdbcTemplate(jdbc);
    }

    //HIBERNATE:
    @Autowired
    private SessionFactory sessionFactory;

    private Session session() {
        return sessionFactory.getCurrentSession();
    }

//    public List<Offer> getOffers() {
//        //return offer Objects from DB-row | Use Template-class query(query, rowMapping) method -> List<Object>
//        return jdbc.query("SELECT * FROM offers, users WHERE offers.username=users.username AND users.enabled = true",
//                new OfferRowMapper());
//    }

    //get all Offers for particular User:
//    public List<Offer> getOffers(String username) {
//        //return offer Objects from DB-row | Use Template-class query(query, rowMapping) method -> List<Object>
//        return jdbc.query("SELECT * FROM offers, users WHERE offers.username=users.username AND users.enabled = true AND offers.username=:username",
//                new MapSqlParameterSource("username", username),
//                new OfferRowMapper());
//    }

    //Retrieve single Offer Object:
//    public Offer getOffer(int id) {
//        //NamedParameter placeholder Setters:
//        MapSqlParameterSource params = new MapSqlParameterSource();
//        params.addValue("id", id);
//
//        //Use Template-class query(query, params, rowMapping) method -> List<Object>
//        return jdbc.queryForObject("SELECT * FROM offers, users WHERE offers.id=:id and users.enabled=true", params,
//                new OfferRowMapper());
//    }

    //delete Method:
//    public boolean deleteById(int id) {
//        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
//        //return true if successful:
//        return jdbc.update("DELETE FROM offers WHERE id=:id", params) == 1;
//    }

    //create Method:
//    public boolean createOffer(Offer offer) {
//        //Offer Parameters:
//        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(offer);
//
//        return jdbc.update(
//                "INSERT INTO offers(username, information) VALUES(:username,:information)", params) == 1;
//    }

    //batch create Method:
    @Transactional
    public int[] saveUpdateOffer(List<Offer> offers) {
        SqlParameterSource[] params = SqlParameterSourceUtils.createBatch(offers.toArray());
        return jdbc.batchUpdate(
                "INSERT INTO offers(username, information) VALUES(:username,:information)", params);
    }

    //update Method:
//    public boolean updateOffer(Offer offer) {
//        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(offer);
//        return jdbc.update(
//                "UPDATE offers SET information=:information WHERE id=:id", params) == 1;
//    }

    //Hibernate Implementation:

//    public void updateOffer(Offer offer) { //updating Object
//        session().update(offer);
//    }

    public void saveUpdateOffer(Offer offer) { //both create&update:
        session().saveOrUpdate(offer);
    }

    @SuppressWarnings("unchecked")
    public List<Offer> getOffers() { //join query:
        Criteria criteria = session().createCriteria(Offer.class);
        criteria.createAlias("user", "u") //with Offer.class - property
                .add(Restrictions.eq("u.enabled", true)); //WHERE clause

        return criteria.list();
    }

    @SuppressWarnings("unchecked")
    public List<Offer> getOffers(String username) { //multiple Criteria
        Criteria criteria = session().createCriteria(Offer.class);
        criteria.createAlias("user", "u")
                .add(Restrictions.eq("u.enabled", true))
                .add(Restrictions.eq("u.username", username));

        return criteria.list();
    }

    public boolean deleteById(int id) {
        Query query = session().createQuery("delete from Offer where id=:id"); //refer to Object Offer
        query.setLong("id", id); //set value of placeholder id
        return query.executeUpdate() == 1;
    }

    public Offer getOffer(int id) { //retrieve Offer by Id:
        Criteria criteria = session().createCriteria(Offer.class);
        criteria.createAlias("user", "u");

        criteria.add(Restrictions.eq("u.enabled", true));
        criteria.add(Restrictions.idEq(id)); //check Primary-Key

        return (Offer)criteria.uniqueResult();
    }
}
