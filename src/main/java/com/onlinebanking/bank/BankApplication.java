package com.onlinebanking.bank;

import com.onlinebanking.bank.dto.UserDTO;
import com.onlinebanking.bank.entity.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankApplication {
	public static void main(String[] args) {
		SpringApplication.run(BankApplication.class, args);
	}
}
