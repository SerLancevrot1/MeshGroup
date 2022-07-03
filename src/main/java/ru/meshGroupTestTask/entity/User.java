package ru.meshGroupTestTask.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(of = "id")
@Entity
@Getter
@Setter
@ToString
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long age;

    private String email;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
            fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Phone> phones = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY)
    private Profile profile;
}
