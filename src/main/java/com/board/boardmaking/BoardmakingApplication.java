package com.board.boardmaking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BoardmakingApplication {

	public static void main(String[] args) {
		//객체 생성부터 해야지 이 바보야
		hello hello = new hello();
		hello.setAge(11);
		//hello.getAge();
		System.out.println("hello.age = " + hello.age);

		SpringApplication.run(BoardmakingApplication.class, args);


	}



}
