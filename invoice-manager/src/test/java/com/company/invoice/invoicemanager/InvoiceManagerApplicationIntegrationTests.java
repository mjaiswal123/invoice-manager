package com.company.invoice.invoicemanager;

import com.company.invoice.invoicemanager.dataobject.Invoice;
import com.company.invoice.invoicemanager.dataobject.Item;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InvoiceManagerApplicationIntegrationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	/**
	 * This is the integration to test the happy path to create Invoice with all values.
	 * @throws Exception
     */
	@Test
	public void testInvoiceCreate() throws Exception {

		//Populate the input request object
		Invoice invoiceRequest = new Invoice();
		invoiceRequest.setDesc("Invoice for paint job");
		invoiceRequest.setEmail("test@gmail.com");
		invoiceRequest.setDueDate(new Date());
		invoiceRequest.setName("First Invoice");

		List<Item> items = new ArrayList();
		Item lineitem1 = new Item();
		lineitem1.setDesciption("Line item1");
		lineitem1.setItemAmount(new Double(10.10));

		Item lineitem2 = new Item();
		lineitem2.setDesciption("Line item2");
		lineitem2.setItemAmount(new Double(5.05));
		items.add(lineitem1);
		items.add(lineitem2);
		invoiceRequest.setItems(items);

		//call the post method
		Invoice invoice = this.restTemplate.postForObject("/invoice", invoiceRequest, Invoice.class);
		assertThat(invoice).isNotNull();
		assertThat(invoice.getId()).isGreaterThan(0l);
		assertThat(invoice.getDesc()).isEqualTo("Invoice for paint job");
		assertThat(invoice.getName()).isEqualTo("First Invoice");
		assertThat(invoice.getEmail()).isEqualTo("test@gmail.com");
		assertThat(invoice.getItems().size()).isEqualTo(2);
		assertThat(invoice.getItems().get(0).getDesciption()).isEqualTo("Line item1");
		assertThat(invoice.getItems().get(0).getItemAmount()).isEqualTo(10.10);
		assertThat(invoice.getItems().get(1).getDesciption()).isEqualTo("Line item2");
		assertThat(invoice.getItems().get(1).getItemAmount()).isEqualTo(5.05);

	}

	/**
	 * This is the integration test to validate the Item is required in invoice create
	 * @throws Exception
	 */
	@Test
	public void testInvoiceCreateWithMinField() throws Exception {

		//Populate the input request object
		Invoice invoiceRequest = new Invoice();
		invoiceRequest.setDueDate(new Date());
		invoiceRequest.setName("Second Invoice");

		List<Item> items = new ArrayList();
		Item lineitem1 = new Item();
		lineitem1.setDesciption("Line item1");
		lineitem1.setItemAmount(new Double(10.10));
		items.add(lineitem1);
		invoiceRequest.setItems(items);

		//call the post method
		Invoice invoice = this.restTemplate.postForObject("/invoice", invoiceRequest, Invoice.class);
		assertThat(invoice).isNotNull();
		assertThat(invoice.getId()).isGreaterThan(0l);
		assertThat(invoice.getName()).isEqualTo("Second Invoice");
		assertThat(invoice.getItems().size()).isEqualTo(1);
		assertThat(invoice.getItems().get(0).getDesciption()).isEqualTo("Line item1");
		assertThat(invoice.getItems().get(0).getItemAmount()).isEqualTo(10.10);

	}

	/**
	 * This is the integration to test the happy path to create Invoice with all values.
	 * @throws Exception
	 */
	@Test
	public void testInvoiceCreateWithOutLineItem() throws Exception {

		//Populate the input request object
		Invoice invoiceRequest = new Invoice();
		invoiceRequest.setDueDate(new Date());
		invoiceRequest.setName("Third Invoice");

		System.out.println(invoiceRequest.toString());

		//call the post method
		ResponseEntity<Invoice> invoice = this.restTemplate.postForEntity("/invoice", invoiceRequest, Invoice.class);
		assertThat(invoice.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

	}


	/**
	 * This is the integration to test the CRUD operation on Invoice.
	 * @throws Exception
	 */
	@Test
	public void testInvoiceCRUD() throws Exception {

		//Populate the input request object
		Invoice invoiceRequest = new Invoice();
		invoiceRequest.setDesc("Invoice for paint job");
		invoiceRequest.setEmail("test@gmail.com");
		invoiceRequest.setDueDate(new Date());
		invoiceRequest.setName("First CRUD Invoice");

		List<Item> items = new ArrayList();
		Item lineitem1 = new Item();
		lineitem1.setDesciption("Line item1");
		lineitem1.setItemAmount(new Double(10.10));

		Item lineitem2 = new Item();
		lineitem2.setDesciption("Line item2");
		lineitem2.setItemAmount(new Double(5.05));
		items.add(lineitem1);
		items.add(lineitem2);
		invoiceRequest.setItems(items);

		//call the post method
		Invoice createdInvoice = this.restTemplate.postForObject("/invoice", invoiceRequest, Invoice.class);
		assertThat(createdInvoice).isNotNull();
		assertThat(createdInvoice.getId()).isGreaterThan(0l);
		assertThat(createdInvoice.getDesc()).isEqualTo("Invoice for paint job");
		assertThat(createdInvoice.getName()).isEqualTo("First CRUD Invoice");
		assertThat(createdInvoice.getEmail()).isEqualTo("test@gmail.com");
		assertThat(createdInvoice.getItems().size()).isEqualTo(2);
		assertThat(createdInvoice.getItems().get(0).getDesciption()).isEqualTo("Line item1");
		assertThat(createdInvoice.getItems().get(0).getItemAmount()).isEqualTo(10.10);
		assertThat(createdInvoice.getItems().get(1).getDesciption()).isEqualTo("Line item2");
		assertThat(createdInvoice.getItems().get(1).getItemAmount()).isEqualTo(5.05);

		//query the Invoice by GetbyId
		Invoice readInvoice = this.restTemplate.getForObject("/invoice/{id}",Invoice.class,createdInvoice.getId());
		//Comparing the created and queried object
		assertThat(readInvoice.getId()).isEqualTo(createdInvoice.getId());
		assertThat(readInvoice.getDesc()).isEqualTo(createdInvoice.getDesc());
		assertThat(readInvoice.getName()).isEqualTo(createdInvoice.getName());
		assertThat(readInvoice.getEmail()).isEqualTo(createdInvoice.getEmail());
		assertThat(readInvoice.getItems().size()).isEqualTo(2);
		assertThat(readInvoice.getItems().get(0).getDesciption()).isEqualTo(createdInvoice.getItems().get(0).getDesciption());
		assertThat(readInvoice.getItems().get(0).getItemAmount()).isEqualTo(createdInvoice.getItems().get(0).getItemAmount());
		assertThat(readInvoice.getItems().get(1).getDesciption()).isEqualTo(createdInvoice.getItems().get(1).getDesciption());
		assertThat(readInvoice.getItems().get(1).getItemAmount()).isEqualTo(createdInvoice.getItems().get(1).getItemAmount());

		//updated the Invoice description
		invoiceRequest.setDesc("Updated the description");
		invoiceRequest.setId(readInvoice.getId());

		this.restTemplate.put("/invoice/{id}", invoiceRequest, Invoice.class,createdInvoice.getId());
		Invoice read1Invoice = this.restTemplate.getForObject("/invoice/{id}",Invoice.class,createdInvoice.getId());
		assertThat(read1Invoice.getId()).isEqualTo(createdInvoice.getId());
		assertThat(read1Invoice.getDesc()).isEqualTo("Updated the description");

		//Delete the invoice
		this.restTemplate.delete("/invoice/{id}",Invoice.class,createdInvoice.getId());

	}

	/**
	 * This is the integration to test the CRUD operation on Invoice.
	 * @throws Exception
	 */
	@Test
	public void testInvoiceCreateAndQueryByName() throws Exception {

		//Populate the input request object
		Invoice invoiceRequest = new Invoice();
		invoiceRequest.setDesc("Invoice for paint job");
		invoiceRequest.setEmail("test@gmail.com");
		invoiceRequest.setDueDate(new Date());
		invoiceRequest.setName("FirstInvoice");

		List<Item> items = new ArrayList();
		Item lineitem1 = new Item();
		lineitem1.setDesciption("Line item1");
		lineitem1.setItemAmount(new Double(10.10));

		Item lineitem2 = new Item();
		lineitem2.setDesciption("Line item2");
		lineitem2.setItemAmount(new Double(5.05));
		items.add(lineitem1);
		items.add(lineitem2);
		invoiceRequest.setItems(items);

		//call the post method
		Invoice createdInvoice = this.restTemplate.postForObject("/invoice", invoiceRequest, Invoice.class);
		assertThat(createdInvoice).isNotNull();
		assertThat(createdInvoice.getId()).isGreaterThan(0l);
		assertThat(createdInvoice.getDesc()).isEqualTo("Invoice for paint job");
		assertThat(createdInvoice.getName()).isEqualTo("FirstInvoice");
		assertThat(createdInvoice.getEmail()).isEqualTo("test@gmail.com");
		assertThat(createdInvoice.getItems().size()).isEqualTo(2);
		assertThat(createdInvoice.getItems().get(0).getDesciption()).isEqualTo("Line item1");
		assertThat(createdInvoice.getItems().get(0).getItemAmount()).isEqualTo(10.10);
		assertThat(createdInvoice.getItems().get(1).getDesciption()).isEqualTo("Line item2");
		assertThat(createdInvoice.getItems().get(1).getItemAmount()).isEqualTo(5.05);

		//query the Invoice by Name
		Invoice readInvoice = this.restTemplate.getForObject("/invoice?name=FirstInvoice",Invoice.class);
		//Comparing the created and queried object
		assertThat(readInvoice.getId()).isEqualTo(createdInvoice.getId());
		assertThat(readInvoice.getDesc()).isEqualTo(createdInvoice.getDesc());
		assertThat(readInvoice.getName()).isEqualTo(createdInvoice.getName());
		assertThat(readInvoice.getEmail()).isEqualTo(createdInvoice.getEmail());
		assertThat(readInvoice.getItems().size()).isEqualTo(2);
		assertThat(readInvoice.getItems().get(0).getDesciption()).isEqualTo(createdInvoice.getItems().get(0).getDesciption());
		assertThat(readInvoice.getItems().get(0).getItemAmount()).isEqualTo(createdInvoice.getItems().get(0).getItemAmount());
		assertThat(readInvoice.getItems().get(1).getDesciption()).isEqualTo(createdInvoice.getItems().get(1).getDesciption());
		assertThat(readInvoice.getItems().get(1).getItemAmount()).isEqualTo(createdInvoice.getItems().get(1).getItemAmount());
		
	}

}
