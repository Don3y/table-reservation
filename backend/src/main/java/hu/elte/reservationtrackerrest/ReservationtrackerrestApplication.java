package hu.elte.reservationtrackerrest;

import hu.elte.reservationtrackerrest.entities.User;
import hu.elte.reservationtrackerrest.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;


@SpringBootApplication
public class ReservationtrackerrestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReservationtrackerrestApplication.class, args);
    }

    @Autowired
    private UserRepository userRepository;

   

}
