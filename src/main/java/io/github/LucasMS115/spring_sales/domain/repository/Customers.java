package io.github.LucasMS115.spring_sales.domain.repository;

import io.github.LucasMS115.spring_sales.domain.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface Customers extends JpaRepository<Customer, Integer> {

    // query methods convention
    List<Customer> findByNameLike(String name);

    // custom query strings

    //hql
//    @Query(value = "SELECT c FROM Customer c WHERE c.name like :name")
//    List<Customer> customFindByNameLike(@Param("name") String name);

    //sql
    @Query(value = "SELECT * FROM Customer c WHERE c.name LIKE CONCAT('%',:name,'%')", nativeQuery = true)
    List<Customer> customFindByNameLike(@Param("name") String name);

    Customer getByName(String name);

    @Modifying
    @Transactional // don't know if this is really necessary, but seems like it is
    @Query("DELETE FROM Customer c WHERE c.name = :name ")
    void customDeleteByName(@Param("name") String name);
}

// **** JPA VERSION ****
//
//@Repository
//public class Customers {
//
//    @Autowired
//    private EntityManager entityManager;
//
//    @Transactional
//    public List<Customer> listAll(){
//        return entityManager
//                .createQuery(" FROM Customer ", Customer.class)
//                .getResultList();
//    }
//
//    @Transactional (readOnly = true) // optimize the query, dnt use cash, make it a bit faster
//    public Customer getById(Integer id){
//        //the name in the entity property, not the column name (if they're not the same)
//        String jpql_query_string = " SELECT c FROM Customer c WHERE c.id = :id ";
//        TypedQuery<Customer> query = entityManager.createQuery(jpql_query_string, Customer.class);
//        query.setParameter("id", id);
//        return query.getSingleResult();
//    }
//
//    @Transactional (readOnly = true)
//    public List<Customer> listByName(String name){
//        String jpql_query_string = " SELECT c FROM Customer c WHERE c.name like :name ";
//        TypedQuery<Customer> query = entityManager.createQuery(jpql_query_string, Customer.class);
//        query.setParameter("name", "%" + name + "%");
//        return query.getResultList();
//    }
//
//    @Transactional
//    public Customer getByName(String name){
//        String jpql_query_string = " SELECT c FROM Customer c WHERE c.name like :name ";
//        TypedQuery<Customer> query = entityManager.createQuery(jpql_query_string, Customer.class);
//        query.setParameter("name", "%" + name + "%");
//        return query.getSingleResult();
//    }
//
//    @Transactional
//    public Customer create(Customer customer){
//        entityManager.persist(customer);
//        return customer;
//    }
//
//    @Transactional
//    public Customer update(Customer customer){
//        entityManager.merge(customer);
//        return customer;
//    }
//
//    @Transactional
//    public Boolean delete(Integer id){
//        Customer deletedCustomer = entityManager.find(Customer.class, id); //(class, identifier)
//        delete(deletedCustomer);
//        return true;
//    }
//
//    @Transactional
//    public Boolean delete(Customer customer){
//        //to do: check if this method returns the deleted object
//        if(!entityManager.contains(customer)){
//           customer = entityManager.merge(customer); //sync if not synced
//        }
//
//        entityManager.remove(customer);
//        return true;
//    }
//
//}


// **** JDBC VERSION ****

/*
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
*/