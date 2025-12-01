package com.devCircle.devCircle;

import com.devCircle.devCircle.entity.Post;
import com.devCircle.devCircle.entity.User;
import com.devCircle.devCircle.repository.PostRepository;
import com.devCircle.devCircle.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@SpringBootApplication
public class DevCircleApplication {

    public DevCircleApplication(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(DevCircleApplication.class, args);
    }

    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner init(UserRepository userRepo, PostRepository postRepo) {
        return args -> {
            if (userRepo.count() == 0) {
                User user = User.builder()
                        .displayName("alice")
                        .email("alice@email.com")
                        .password(passwordEncoder.encode("1234")) // just for test, later use hashed
                        .role("BEGINNER")
                        //.skills("Java,Spring Boot")
                        .githubUrl("https://www.flashscore.pl/")
                        .linkedinUrl("https://www.reddit.com/")
                        .build();

                userRepo.save(user);

                Post post = Post.builder()
                        .title("Need help with JWT Auth")
                        .description("Trying to implement JWT in Spring Boot")
                        //.tags(List.of("A", "B", "C"))
                        .type("REQUEST")
                        .status("OPEN")
                        .createdAt(LocalDateTime.now())
                        .author(user)
                        .build();

                postRepo.save(post);

                System.out.println("✅ Inserted test user & post");
            }
        };
    }
}
