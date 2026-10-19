package com.dance.controller;

import com.dance.model.Direction;
import com.dance.model.Enrollment;
import com.dance.repository.DirectionRepository;
import com.dance.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DanceController {

    @Autowired
    private DirectionRepository directionRepo;
    
    @Autowired
    private EnrollmentRepository enrollmentRepo;

    @GetMapping("/")
    public String home(Model model) {
        if (directionRepo.count() == 0) {
            directionRepo.save(new Direction(null, "Хип-хоп", "Уличный стиль", "Анна", "Пн, Ср 18:00"));
            directionRepo.save(new Direction(null, "Балет", "Классика", "Ольга", "Вт, Чт 16:00"));
        }
        model.addAttribute("directions", directionRepo.findAll());
        model.addAttribute("enrollments", enrollmentRepo.findAll());
        return "index"; // Говорит Spring Boot искать файл index.html в папке templates
    }

    @PostMapping("/enroll")
    public String enroll(@RequestParam String studentName, @RequestParam String phone, @RequestParam Long directionId) {
        Direction dir = directionRepo.findById(directionId).orElseThrow();
        Enrollment e = new Enrollment();
        e.setStudentName(studentName);
        e.setPhone(phone);
        e.setDirection(dir);
        enrollmentRepo.save(e);
        return "redirect:/"; // После отправки формы возвращает на главную
    }
}