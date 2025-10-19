package ru.databaseProj.firstLab.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.databaseProj.firstLab.repositories.ProjectRepository;
import ru.databaseProj.firstLab.entities.ProjectEntity;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectEntity save(ProjectEntity projectEntity) {
        return projectRepository.save(projectEntity);
    }

    public List<ProjectEntity> getAll() {
        return projectRepository.findAll();
    }

    public ProjectEntity findById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("PROJECT with ID: " + id + " not found"));
    }

    public List<ProjectEntity> getByName(String name) {
        return projectRepository.findByName(name);
    }

    public List<ProjectEntity> findProjectsByTaskStatus(String taskStatus) {
        return projectRepository.findProjectsByTaskStatus(taskStatus);
    }

    public ProjectEntity update(Long id, ProjectEntity projectDetails) {
        var project = findById(id);
        project.setName(projectDetails.getName());
        project.setDescription(projectDetails.getDescription());
        return projectRepository.save(project);
    }

    public void deleteById(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new NoSuchElementException("PROJECT with ID: " + id + " not found");
        }
        projectRepository.deleteById(id);
    }
}
