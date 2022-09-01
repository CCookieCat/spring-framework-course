package com.ccat.springtest.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Transactional
@Component("usersDao")
public class UsersDAO {
    //Password encoding:
    @Autowired
    private PasswordEncoder passwordEncoder;

//    private NamedParameterJdbcTemplate jdbcTemplate;

//    @Autowired
//    public void setDataSource(DataSource jdbc) {
//        this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbc);
//    }

    //HIBERNATE:
    @Autowired
    private SessionFactory sessionFactory;

    public Session session() { //for queries:
        return sessionFactory.getCurrentSession();
    }

//    @Transactional
//    public boolean create(User user) {
////        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(user);
//
//        //Password Encoding:
//        MapSqlParameterSource params = new MapSqlParameterSource();
//        //manually set parameters:
//        params.addValue("password", passwordEncoder.encode(user.getPassword()));
//        params.addValue("username", user.getUsername());
//        params.addValue("name", user.getName());
//        params.addValue("email", user.getEmail());
//        params.addValue("enabled", user.getEnabled());
//        params.addValue("authority", user.getAuthority());
//
//        return jdbcTemplate.update("INSERT INTO users(username, name, email, password, authority, enabled) VALUES(:username,:name, :email,:password, :authority,:enabled)", params) == 1;
//    }

//    public boolean exists(String username) {
//        //Query for numbers of rows with same username in DB
//        return jdbcTemplate.queryForObject("SELECT count(*) FROM users WHERE username=:username",
//                new MapSqlParameterSource("username", username), Integer.class) > 0;
//    }

//    public List<User> getAllUsers() {
//        //Get results from SQL, map rows to User Bean.
//        return jdbcTemplate.query(
//                "SELECT * FROM users", BeanPropertyRowMapper.newInstance(User.class));
//    }

    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        //use HQL to run query ("Bean_Name").
//        return session().createQuery("from User").list();
        return session().createQuery("from User").list();
    }

    public boolean exists(String username) {
//        //Use Criteria to retrieve Object from DB
//        Criteria criteria = session().createCriteria(User.class);
//        //check username Field against username String
////        criteria.add(Restrictions.eq("username", username));
//        //For querying a Primary-Key:
//        criteria.add(Restrictions.idEq(username));
//        //get result Object:
//        User user = (User)criteria.uniqueResult();

        return getUser(username) != null;
    }

    @Transactional
    public void create(User user) {
        //get and encrypt user password:
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        session().save(user);
    }

    public User getUser(String username) {
        Criteria criteria = session().createCriteria(User.class);
        criteria.add(Restrictions.idEq(username));

        return (User)criteria.uniqueResult();
    }
}
