package com.company.invoice.invoicemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan (basePackages ="com.company.*" )
public class InvoiceManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvoiceManagerApplication.class, args);
	}
}
