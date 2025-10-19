package ru.databaseProj.firstLab.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Getter @Setter
@Table(name = "users")
@ToString(exclude = {"assignedTasks"})
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "assignee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TaskEntity> assignedTasks;

    public UserEntity() {}

    public UserEntity(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
