package ru.meshGroupTestTask.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@EqualsAndHashCode(of = "id")
@Entity
@Getter
@Setter
@ToString
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal cash;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
