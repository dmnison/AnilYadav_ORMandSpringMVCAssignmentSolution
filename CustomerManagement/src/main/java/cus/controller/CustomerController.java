package cus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cus.entity.Customer;
import cus.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService CustomerService;

	// add mapping for "/list"

	@RequestMapping("/list")
	public String listBooks(Model theModel) {

		System.out.println("request recieved");
		// get details from db
		List<Customer> theCustomers = CustomerService.findAll();

		// add to the spring model
		theModel.addAttribute("Customers", theCustomers);

		return "list-customer";
	}

	@RequestMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {

		// create model attribute to bind form data
		Customer theCustomer = new Customer();

		theModel.addAttribute("Customer", theCustomer);

		return "customer-form";
	}

	@RequestMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int theId, Model theModel) {

		// get the details from the service
		Customer theCustomer = CustomerService.findById(theId);

		// set detail as a model attribute to pre-populate the form
		theModel.addAttribute("Customer", theCustomer);

		// send over to our form
		return "customer-form";
	}

	@PostMapping("/save")
	public String saveBook(@RequestParam("id") int id, @RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName, @RequestParam("email") String email)
			 {

		System.out.println(id);
		Customer theCustomer;
		if (id != 0) {
			theCustomer = CustomerService.findById(id);
			theCustomer.setFirstName(firstName);
			theCustomer.setLastName(lastName);
			theCustomer.setEmail(email);
			
		} else
			theCustomer = new Customer(firstName, lastName, email);
		// save the customer record
		CustomerService.save(theCustomer);

		// use a redirect to prevent duplicate submissions
		return "redirect:/customer/list";

	}

	@RequestMapping("/delete")
	public String delete(@RequestParam("customerId") int theId) {

		// delete the student record
		CustomerService.deleteById(theId);

		// redirect to /Customer/list
		return "redirect:/customer/list";

	}

}