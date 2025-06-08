package kr.ac.hansung.cse.hellospringdatajpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"kr.ac.hansung.cse.hellospringdatajpa.entity"})
public class HelloSpringDataJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloSpringDataJpaApplication.class, args);
    }

}
