package com.luv2code.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.luv2code.springdemo.entity.Customer;
import com.luv2code.springdemo.util.SortsUtils;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	
	// need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Customer> getCustomers() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Customer> getCustomers(int theSortField) {

		// get the current hibernate session
				Session currentSession = sessionFactory.getCurrentSession();
				
				// determine sort field
				
				String theFieldName = null;
				
				switch(theSortField) {
					case SortsUtils.FIRST_NAME: theFieldName = "firstName";
					break;
				
					case SortsUtils.LAST_NAME: theFieldName = "lastName";
					break;
				
					case SortsUtils.EMAIL: theFieldName = "Email";
					break;
					
					default:
						
						// if nothing matches, default to sort by last name
						
						theFieldName = "lastName";
				}
				
				// create a query ... sort by last name
				String queryString = "from cusomter order by " + theFieldName;
				
				Query<Customer> theQuery = 
						currentSession.createQuery(queryString, Customer.class);
				
				// execute query and get result list
				List<Customer> customers = theQuery.getResultList();
				
				// return the results
		
		return customers;
	}
	

	@Override
	public void saveCustomer(Customer theCustomer) {
	
		//get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// save/update the customer . . . finaly
		currentSession.saveOrUpdate(theCustomer);
	}


	@Override
	public Customer getCustomer(int theId) {

		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// now retrieve/read from database using the primary key
		Customer theCustomer = currentSession.get(Customer.class, theId);
		
		
		return theCustomer;
		
	}


	@Override
	public void deleteCustomer(int theId) {

		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// delete object with primary key
		Query theQuery = 
				currentSession.createQuery("delete from Customer where id=:customerId");
		theQuery.setParameter("customerId", theId);
		
		theQuery.executeUpdate();
		
	}


	@Override
	public List<Customer> searchCustomers(String theSearchName) {

		//get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query theQuery = null;
		
		//only search by name if theSearchName is not empty
		
		if (theSearchName != null && theSearchName.trim().length() > 0) {
			
			//search for firstName or lastName...case insensitive
			theQuery = currentSession.createQuery("from Customer where lower(firstName) like :theName or lower(lastName) like :theName", Customer.class);
			
			theQuery.setParameter("theName", "%" + theSearchName.toLowerCase() + "%");
		}
		else {
			// thesreachName is empty...so just get all customers
			theQuery = currentSession.createQuery("from Customer", Customer.class);
			
		}
		
		// execute query and get result list
		List<Customer> customers = theQuery.getResultList();
		
		return customers;
	}







}










