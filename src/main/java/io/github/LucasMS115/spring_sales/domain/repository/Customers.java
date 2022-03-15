package io.github.LucasMS115.spring_sales.domain.repository;

import io.github.LucasMS115.spring_sales.domain.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class Customers {

    @Autowired
    private JdbcTemplate jdbcTemplate; //bring the db connection

    private String INSERT = "INSERT into CUSTOMER (name) values (?) ";
    private String SELECT_ALL = "SELECT * FROM CUSTOMER ";

    public Customer create(Customer customer){
        //jdbcTemplate.update is used for insert, delete n update operations
        jdbcTemplate.update(INSERT, new Object[]{customer.getName()});
        return customer;
    }

    public List<Customer> listAll(){
        //RowMapper maps the results to a java class
        return jdbcTemplate.query(
                SELECT_ALL,
                new RowMapper<Customer>(){
                @Override
                public Customer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                    //resultSet.getString is used to get a column value from its name
                    return new Customer(resultSet.getInt("CUSTOMER_ID"), resultSet.getString("name"));
                }
            }
        );
    }
}
