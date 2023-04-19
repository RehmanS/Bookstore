package com.bookstore.libraryservice;

import com.bookstore.libraryservice.client.RetrieveMessageErrorDecoder;
import feign.Logger;
import feign.codec.ErrorDecoder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class LibraryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryServiceApplication.class, args);
	}

	/* NOTE: Spring bizim yaratdigimiz RetrieveMessageErrorDecoder-i istifade etsin deye bu bean olmaldır.
	    Üsul : Feign client error handling */
//	@Bean
//	public ErrorDecoder errorDecoder(){
//		return new RetrieveMessageErrorDecoder();
//	}
//
//	@Bean
//	Logger.Level feignLoggerLevel(){
//		return Logger.Level.FULL;
//	}

	/* NOTE: Başqa bir üsulda fault tolerance üsuldur. Bu zaman exception qayıtsa belə process dayanmır
	    və başqa bir process həyata keçir. Client içindəki metodlara CircuitBreaker annotasiyası yazılır
	     exceptiona dayanıqlılıq adlanır bu. */

}
