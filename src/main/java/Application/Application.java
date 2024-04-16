package Application;

import Application.Model.Manager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Path;

// Main class to run the application
@SpringBootApplication
public class Application {
    public static void main(String[] args) throws IOException {
        SpringApplication.run(Application.class, args);
        Manager manager = new Manager();
        Manager.run(Path.of("data/course_data_2018.csv"));
    }
}