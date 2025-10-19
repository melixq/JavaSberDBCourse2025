package ru.databaseProj.firstLab.repositories;

import ru.databaseProj.firstLab.entities.StatusType;
import ru.databaseProj.firstLab.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    List<TaskEntity> findByStatus(StatusType status);
    List<TaskEntity> findByProjectId(Long projectId);
    List<TaskEntity> findByAssigneeId(Long assigneeId);
}
