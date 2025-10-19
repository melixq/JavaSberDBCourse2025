package ru.databaseProj.firstLab.services;

import ru.databaseProj.firstLab.entities.StatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.databaseProj.firstLab.repositories.TaskRepository;
import ru.databaseProj.firstLab.entities.TaskEntity;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskEntity save(TaskEntity task) {
        return taskRepository.save(task);
    }

    public List<TaskEntity> getAll() {
        return taskRepository.findAll();
    }

    public TaskEntity findById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("TASK with ID: " + id + " not found"));
    }

    public TaskEntity update(Long id, TaskEntity taskDetails) {
        TaskEntity task = findById(id);
        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setStatus(taskDetails.getStatus());
        task.setPriority(taskDetails.getPriority());
        task.setDeadline(taskDetails.getDeadline());
        task.setAssignee(taskDetails.getAssignee());
        task.setProject(taskDetails.getProject());
        return taskRepository.save(task);
    }

    public void deleteById(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new NoSuchElementException("TASK with ID: " + id + " not found");
        }
        taskRepository.deleteById(id);
    }

    public List<TaskEntity> findByStatus(StatusType status) {
        return taskRepository.findByStatus(status);
    }

    public List<TaskEntity> findByProjectId(Long projectId) {
        return taskRepository.findByProjectId(projectId);
    }

    public List<TaskEntity> findByAssigneeId(Long assigneeId) {
        return taskRepository.findByAssigneeId(assigneeId);
    }

    public List<TaskEntity> findTasksByAssigneeIdAndStatus(Long assigneeId, StatusType status) {
        var tasks = taskRepository.findByAssigneeId(assigneeId);
        return tasks.stream()
                .filter(task -> task.getStatus() == status)
                .toList();
    }
}
