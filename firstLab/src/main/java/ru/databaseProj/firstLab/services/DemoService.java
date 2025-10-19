package ru.databaseProj.firstLab.services;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.databaseProj.firstLab.entities.*;
import ru.databaseProj.firstLab.repositories.TaskRepository;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class DemoService {
    private final UserService userService;
    private final ProjectService projectService;
    private final TaskService taskService;
    private final TaskRepository taskRepository;

    @PostConstruct
    public void runDemo() {
        log.info("STARTING DEMO SERVICE!");
        try {
            log.info("CREATING USERS!");
            var user1 = new UserEntity();
            user1.setName("Kuznetsov Maxim Dmitrievich");
            user1.setEmail("lab01@mail.ru");
            var savedUser1 = userService.save(user1);
            log.info("Saved user 1: {}", savedUser1);

            var user2 = new UserEntity();
            user2.setName("Ivanov Ivan Ivanovich");
            user2.setEmail("dummy@gmail.com");
            var savedUser2 = userService.save(user2);
            log.info("Saved user 2: {}", savedUser2);

            log.info("CREATING PROJECTS!");
            var project1 = new ProjectEntity();
            project1.setName("Database Lab01 Project");
            project1.setDescription("Database Lab01 Project for UrFU Course 2025");
            var savedProject1 = projectService.save(project1);
            log.info("Saved project 1: {}", savedProject1);

            var project2 = new ProjectEntity();
            project2.setName("Dummy Project");
            project2.setDescription("Dummy Project Description");
            var savedProject2 = projectService.save(project2);
            log.info("Saved project 1: {}", savedProject2);

            log.info("CREATING TASKS!");
            var task1 = new TaskEntity();
            task1.setTitle("Repository");
            task1.setDescription("Initialize application repository on GitHub");
            task1.setStatus(StatusType.COMPLETED);
            task1.setPriority(PriorityType.HIGH);
            task1.setAssignee(savedUser1);
            task1.setProject(savedProject1);
            task1.setDeadline(LocalDate.now().plusDays(1));
            var savedTask1 = taskService.save(task1);
            log.info("Saved task 1: {}", savedTask1);

            var task2 = new TaskEntity();
            task2.setTitle("Design Database Schema");
            task2.setDescription("Design the initial database schema for the application.");
            task2.setStatus(StatusType.COMPLETED);
            task2.setPriority(PriorityType.CRITICAL);
            task2.setAssignee(savedUser2);
            task2.setProject(savedProject1);
            task2.setDeadline(LocalDate.now().plusDays(3));
            var savedTask2 = taskService.save(task2);
            log.info("Saved task 2: {}", savedTask2);

            var task3 = new TaskEntity();
            task3.setTitle("Implement User Service");
            task3.setDescription("Create the UserService with CRUD operations.");
            task3.setStatus(StatusType.IN_TESTING);
            task3.setPriority(PriorityType.HIGH);
            task3.setAssignee(savedUser1);
            task3.setProject(savedProject1);
            task3.setDeadline(LocalDate.now().plusDays(7));
            var savedTask3 = taskService.save(task3);
            log.info("Saved TASK 3: {}", savedTask3);

            log.info("RETRIEVING DATA!");
            log.info("======== ALL USERS =======");
            var allUsers = userService.getAll();
            allUsers.forEach(u -> log.info("User: {}", u));

            log.info("======== ALL PROJECTS =======");
            var allProjects = projectService.getAll();
            allProjects.forEach(project -> log.info("Project: {}", project));

            log.info("======== ALL TASKS =======");
            var allTasks = taskService.getAll();
            allTasks.forEach(task -> log.info("Task: {}", task.getDescription()));

            log.info("======== COMPLETED TASKS =======");
            var completedTasks = taskService.findByStatus(StatusType.COMPLETED);
            completedTasks.forEach(task -> log.info("Task: {}", task.getDescription()));

            log.info("======== TASKS ASSIGNED TO USER W/ ID 1 =======");
            var taskForUser1 = taskService.findByAssigneeId(savedUser1.getId());
            log.info("Task for user 1: {}", taskForUser1);

            log.info("======== UPDATING TASK 3 STATUS TO COMPLETED =======");
            savedTask3.setStatus(StatusType.COMPLETED);
            var updatedTask3 = taskService.update(task3.getId(), savedTask3);
            log.info("Updated task 3: {}", updatedTask3);

            log.info("======== DELETING TASK 3 =======");
            taskService.deleteById(task3.getId());
            var allTasksUpdated = taskService.getAll();
            allTasksUpdated.forEach(task -> log.info("Task#{}: {}", task.getId(), task.getDescription()));
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        log.info("FINISHED DEMO SERVICE!");
    }
}
