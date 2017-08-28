package com.company.invoice.invoicemanager.controller;

import com.company.invoice.invoicemanager.dataobject.Invoice;
import com.company.invoice.invoicemanager.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mjaiswal on 8/25/17.
 */

@RestController
public class InvoiceController {

    @Autowired
    InvoiceRepository repo;

    @RequestMapping(
            path = "/invoice/{id}",
            method = RequestMethod.GET)
    // Get is default, we are not required to specify this
    //public Invoice invoiceById(@PathVariable Long id) {
    public ResponseEntity<Invoice> invoiceById(@PathVariable Long id) {

        Invoice queriedInvoice = repo.findById(id);
        if(queriedInvoice == null)
        {
            return new ResponseEntity<Invoice>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Invoice>(queriedInvoice,HttpStatus.OK);
    }

    @RequestMapping(
            path = "/invoice",
            method = RequestMethod.POST, // Get is default, we are not required to specify this
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoiceRequestDO)  {

        if (invoiceRequestDO.getItems() == null || invoiceRequestDO.getItems().size() < 1)
            return new ResponseEntity<Invoice>(HttpStatus.BAD_REQUEST);

        Invoice savedInvoice = repo.save(invoiceRequestDO);

        return new ResponseEntity<Invoice>(savedInvoice,HttpStatus.OK);
    }

    @RequestMapping(
            path = "/invoice/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Invoice> updateInvoice(@RequestBody Invoice invoiceRequestDO) {

        if (invoiceRequestDO.getItems() == null || invoiceRequestDO.getItems().size() < 1)
            return new ResponseEntity<Invoice>(HttpStatus.BAD_REQUEST);

        Invoice selectedInvoice = repo.findById(invoiceRequestDO.getId());
        if(selectedInvoice == null)
        {
            //Can't update a invoive which is not in system
            return null;
        }

        Invoice updateInvoice = repo.save(invoiceRequestDO);

        if (updateInvoice == null)
        {
            return new ResponseEntity<Invoice>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<Invoice>(updateInvoice,HttpStatus.OK);
    }


    @RequestMapping(
            path = "/invoice/{id}",
            method = RequestMethod.DELETE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Invoice> deleteInvoice(
            @PathVariable Long id,@RequestBody Invoice invoiceRequestDO) throws Exception {

        repo.delete(id);

        return new ResponseEntity<Invoice>(HttpStatus.OK);
    }

    @RequestMapping(path = "/invoice", method = RequestMethod.GET)
    public List<Invoice> getInvoicebyName(@RequestParam("name") String name
    ) {

        List<Invoice> savedInvoices = new ArrayList();

        if (name != null)
            savedInvoices = repo.findByName(name);
        return savedInvoices;
    }


}
