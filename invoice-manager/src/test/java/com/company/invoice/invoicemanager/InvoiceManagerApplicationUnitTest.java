package com.company.invoice.invoicemanager;

import com.company.invoice.invoicemanager.controller.InvoiceController;
import com.company.invoice.invoicemanager.dataobject.Item;
import com.company.invoice.invoicemanager.repository.InvoiceRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.company.invoice.invoicemanager.dataobject.Invoice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

/**
 * Created by mjaiswal on 8/26/17.
 */
public class InvoiceManagerApplicationUnitTest {

    @InjectMocks
    private InvoiceController invoiceController;

    @Mock
    private InvoiceRepository invoiceRepository;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    /*
    * Test for  get by Id
    * */
    @Test
    public void testInvoiceGetById()
    {
        Invoice invoice = new Invoice();
        invoice.setId(1l);
        when(invoiceRepository.findById(1l)).thenReturn(invoice);
        ResponseEntity<Invoice> createdInvoice = invoiceController.invoiceById(1l);
        assertThat(createdInvoice.getBody().getId()).isEqualTo(1l);
    }

    /*
    * Test for  getById without providing Id
    * */
    @Test
    public void testInvoiceGetWithOutId()
    {
        Invoice invoice = new Invoice();
        when(invoiceRepository.findById(1l)).thenReturn(invoice);
        ResponseEntity<Invoice> createdInvoice = invoiceController.invoiceById(1l);
        assertThat(createdInvoice.getBody().getId()).isNull();
    }

    /*
    * Test for doing get by Id on Invalid Id
    * */
    @Test
    public void testInvoiceGetWithInvalidId()
    {
        Invoice invoice = new Invoice();
        invoice.setId(1l);
        when(invoiceRepository.findById(1l)).thenReturn(invoice);
        ResponseEntity<Invoice> createdInvoice = invoiceController.invoiceById(2l);
        assertThat(createdInvoice.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /*
    * Test for Invoice create without required line item
    * */
    @Test
    public void testInvoiceCreateWithOutItem()
    {
        Invoice invoice = new Invoice();
        invoice.setId(1l);
        invoice.setDesc("TEmp desc");

        when(invoiceRepository.save(invoice)).thenReturn(invoice);
        ResponseEntity<Invoice> createdInvoice = invoiceController.createInvoice(invoice);
        assertThat(createdInvoice.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

    }

    /*
    * Test for Invoice full object create
    * */
    @Test
    public void testInvoiceCreate()
    {
        Invoice invoice = new Invoice();
        invoice.setId(1l);
        invoice.setDesc("TEmp desc");
        invoice.setEmail("test@gmail.com");
        invoice.setDueDate(new Date());
        invoice.setName("First Invoice");

        List<Item> items = new ArrayList();
        Item lineitem1 = new Item();
        lineitem1.setDesciption("Line item1");
        lineitem1.setItemAmount(new Double(10.10));

        Item lineitem2 = new Item();
        lineitem2.setDesciption("Line item2");
        lineitem2.setItemAmount(new Double(5.05));
        items.add(lineitem1);
        items.add(lineitem2);
        invoice.setItems(items);

        when(invoiceRepository.save(invoice)).thenReturn(invoice);
        ResponseEntity<Invoice> createdInvoice = invoiceController.createInvoice(invoice);
        assertThat(createdInvoice.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(createdInvoice.getBody().getId()).isEqualTo(1l);

    }

    /*
     * Test for Invoice update without required line item
    * */
    @Test
    public void testInvoiceUpdateWithOutItem()
    {
        Invoice invoice = new Invoice();
        invoice.setId(1l);
        invoice.setDesc("TEmp desc");

        when(invoiceRepository.save(invoice)).thenReturn(invoice);
        ResponseEntity<Invoice> updateInvoice = invoiceController.updateInvoice(invoice);
        assertThat(updateInvoice.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

    }


    /*
 * Test for Invoice update without required line item
* */
    @Test
    public void testInvoiceUpdateWithOutId()
    {
        Invoice invoice = new Invoice();
        invoice.setDesc("TEmp desc");
        invoice.setEmail("test@gmail.com");
        invoice.setDueDate(new Date());
        invoice.setName("First Invoice");

        List<Item> items = new ArrayList();
        Item lineitem1 = new Item();
        lineitem1.setDesciption("Line item1");
        lineitem1.setItemAmount(new Double(10.10));

        Item lineitem2 = new Item();
        lineitem2.setDesciption("Line item2");
        lineitem2.setItemAmount(new Double(5.05));
        items.add(lineitem1);
        items.add(lineitem2);
        invoice.setItems(items);

        when(invoiceRepository.save(invoice)).thenReturn(invoice);
        ResponseEntity<Invoice> updatedInvoice = invoiceController.updateInvoice(invoice);
        assertThat(updatedInvoice.getBody()).isNull();

    }

    @Test
    public void testInvoiceUpdate()
    {
        Invoice invoice = new Invoice();
        invoice.setId(1l);
        invoice.setDesc("Updated desc");
        invoice.setEmail("test@gmail.com");
        invoice.setDueDate(new Date());
        invoice.setName("First Invoice");

        List<Item> items = new ArrayList();
        Item lineitem1 = new Item();
        lineitem1.setDesciption("Line item1");
        lineitem1.setItemAmount(new Double(10.10));

        Item lineitem2 = new Item();
        lineitem2.setDesciption("Line item2");
        lineitem2.setItemAmount(new Double(5.05));
        items.add(lineitem1);
        items.add(lineitem2);
        invoice.setItems(items);

        when(invoiceRepository.save(invoice)).thenReturn(invoice);
        ResponseEntity<Invoice> updatedInvoice = invoiceController.updateInvoice(invoice);
        assertThat(updatedInvoice.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updatedInvoice.getBody().getId()).isEqualTo(1l);
        assertThat(updatedInvoice.getBody().getDesc()).isEqualTo("Updated desc");
    }


}
