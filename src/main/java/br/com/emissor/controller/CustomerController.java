package br.com.emissor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.emissor.controller.response.InvoiceIssuerResponse;
import br.com.emissor.exceptions.BusinessException;
import br.com.emissor.repository.CustomerRepository;
import br.com.emissor.repository.entity.Customer;

@Controller
public class CustomerController {

	@Autowired
	private CustomerRepository customerRepository;

	@RequestMapping(value="customer", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<?>  get(
		   @RequestParam (name="nome",required=false) String nome) throws BusinessException {
		if (nome != null) {
			return new ResponseEntity<>( customerRepository.findByName(nome), HttpStatus.OK);
		}
		return new ResponseEntity<>( customerRepository.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping(value="customer/{id}", method=RequestMethod.GET, produces="application/json")
	public @ResponseBody  ResponseEntity<Customer>  findOne(@PathVariable("id") Integer id) throws BusinessException{
		return new ResponseEntity<Customer>( customerRepository.findOne(id), HttpStatus.OK);
	}
	
	@RequestMapping(value="customer", method= RequestMethod.PUT)
	public @ResponseBody  ResponseEntity<Customer>  put(@RequestBody Customer customer) throws BusinessException{
		Customer result = customerRepository.save(customer);
		if(result == null) 
			throw new BusinessException("Não foram encontrado dadoos");
		return new ResponseEntity<Customer>( result, HttpStatus.OK);
	}
	@RequestMapping(value="customer", method= RequestMethod.POST)
	@Transactional(propagation=Propagation.REQUIRED)
	public @ResponseBody  ResponseEntity<Customer>  post(@RequestBody Customer customer) throws BusinessException{
//		if (church.getEndereco() != null) {
//			customerRepository.save(church.getEndereco());
//		}
		Customer result = customerRepository.save(customer);
		if(result == null) 
			throw new BusinessException("Não foram encontrado dadoos");
		return new ResponseEntity<Customer>( result, HttpStatus.OK);
	}

	@RequestMapping(value="customer/{id}", method=RequestMethod.DELETE)
	public @ResponseBody  ResponseEntity<InvoiceIssuerResponse>  delete(@PathVariable("id") Integer id) throws BusinessException {
		customerRepository.delete(customerRepository.findOne(id));
		return new ResponseEntity<InvoiceIssuerResponse>( new InvoiceIssuerResponse(0, "EXCLUIDO COM SUCESSO!"), HttpStatus.OK);
	}
	
}
