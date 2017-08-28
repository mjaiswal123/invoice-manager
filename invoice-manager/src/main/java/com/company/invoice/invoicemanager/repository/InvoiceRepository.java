package com.company.invoice.invoicemanager.repository;

import java.util.Date;
import java.util.List;

import com.company.invoice.invoicemanager.dataobject.Invoice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by mjaiswal on 8/26/17.
 */

@Repository
public interface InvoiceRepository extends CrudRepository<Invoice, Long> {

    Invoice findById(Long id);

    List<Invoice> findByName(String name);

    List<Invoice> findByEmail(String email);

    List<Invoice> findByDueDateGreaterThan(Date duedate);

    List<Invoice> findByDueDateLessThan(Date duedate);
}