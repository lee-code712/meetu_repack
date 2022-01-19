package project.meetu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan(value = {"project.meetu.model.dao.mapper"})
@SpringBootApplication
public class MeetuApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeetuApplication.class, args);
	}

}
