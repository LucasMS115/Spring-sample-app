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
    private String SELECT_BY_NAME = "SELECT * FROM CUSTOMER WHERE NAME like ?";
    private String SELECT_BY_ID = "SELECT * FROM CUSTOMER WHERE CUSTOMER_ID = ?";
    private String UPDATE = "UPDATE CUSTOMER set NAME = ? WHERE CUSTOMER_ID = ?";
    private String DELETE = "DELETE FROM CUSTOMER WHERE CUSTOMER_ID = ?";

    //RowMapper maps the results to a java class
    private RowMapper<Customer> getRowMapper() {
        return new RowMapper<Customer>(){
            @Override
            public Customer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                //resultSet.getString is used to get a column value from its name
                return new Customer(resultSet.getInt("CUSTOMER_ID"), resultSet.getString("name"));
            }
        };
    }

    public List<Customer> listAll(){
        return jdbcTemplate.query(
                SELECT_ALL,
                getRowMapper()
        );
    }

    public Customer getById(Integer id){
        return jdbcTemplate.queryForObject(
                SELECT_BY_ID,
                getRowMapper(),
                id
        );
    }

    public List<Customer> listByName(String name){
        return jdbcTemplate.query(
                SELECT_BY_NAME,
                getRowMapper(),
                "%" + name + "%"
        );
    }

    public Customer getByName(String name){
        return jdbcTemplate.queryForObject(
                SELECT_BY_NAME,
                getRowMapper(),
                "%" + name + "%"
        );
    }

    public Customer create(Customer customer){
        //jdbcTemplate.update is used for insert, delete n update operations
        jdbcTemplate.update(INSERT, new Object[]{customer.getName()});
        return customer;
    }

    public Customer update(Customer customer){
        //the parameters inside o object array have to in the same sequence as they show up at the query
        jdbcTemplate.update(UPDATE, new Object[]{customer.getName(), customer.getId()});
        return customer;
    }

    public Boolean delete(Integer id){
        //to do: check if this method returns the deleted object
        jdbcTemplate.update(DELETE, new Object[]{id});
        return true;
    }

    public Boolean delete(Customer customer){
        delete(customer.getId());
        return true;
    }

}
