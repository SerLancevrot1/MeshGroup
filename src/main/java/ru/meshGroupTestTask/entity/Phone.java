package ru.meshGroupTestTask.entity;

import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode(of = "id")
@Entity
@Getter
@Setter
@ToString
@Table(name = "phones")
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String value;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
