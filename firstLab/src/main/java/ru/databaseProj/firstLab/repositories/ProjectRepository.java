package ru.databaseProj.firstLab.repositories;

import ru.databaseProj.firstLab.entities.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
    List<ProjectEntity> findByName(String name);

    @Query("SELECT DISTINCT p FROM ProjectEntity p JOIN p.tasks t WHERE t.status = :status")
    List<ProjectEntity> findProjectsByTaskStatus(@Param("status") String status);
}
