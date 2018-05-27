package SpringLab2;

import SpringLab2.Data.Application;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author Artur Kuzmik on 18.27.5
 */

@Configuration
@ComponentScan (basePackages = "SpringLab2")
public class Main {
    public static void main(String[] args) throws IOException {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(Main.class);
        Application app = context.getBean(Application.class);
        app.run();
    }
}
