package com.leone.HairCutBooker;

import com.leone.HairCutBooker.exception.GlobalExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(GlobalExceptionHandler.class)
public class HairCutBookerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HairCutBookerApplication.class, args);
	}


}
