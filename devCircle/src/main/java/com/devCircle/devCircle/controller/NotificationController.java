package com.devCircle.devCircle.controller;

import com.devCircle.devCircle.dto.NotificationDto;
import com.devCircle.devCircle.entity.User;
import com.devCircle.devCircle.repository.UserRepository;
import com.devCircle.devCircle.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final UserRepository userRepository;

    @GetMapping
    public List<NotificationDto> myNotifications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            Principal principal) {

        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        return notificationService.getNotificationsForUser(user, page, size);
    }

    @PatchMapping("/{id}/read")
    public void markRead(@PathVariable Long id, Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        notificationService.markAsRead(id, user);
    }

    @GetMapping("/test")
    public void testNotification() {
        notificationService.sendCommentNotification(
                userRepository.findByEmail("alice@email.com").get(),
                "Hello from test endpoint!"
        );
    }

}
