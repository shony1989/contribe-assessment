package com.contribe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class ContribeServerApplicantTestApplication 
{
    public static void main( String[] args )
    {
    	 SpringApplication.run(ContribeServerApplicantTestApplication.class, args);
    }
}
