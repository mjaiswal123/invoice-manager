package com.company.invoice.invoicemanager.repository;

import com.company.invoice.invoicemanager.dataobject.Invoice;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by mjaiswal on 8/26/17.
 */
public interface ItemRepository extends CrudRepository<Invoice, Long> {

}
