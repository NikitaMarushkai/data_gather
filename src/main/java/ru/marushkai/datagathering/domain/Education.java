package ru.marushkai.datagathering.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "education")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Education {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private VKUser vkUser;

    private Integer university;

    @Column(length = 5000)
    private String universityName;

    private Integer faculty;

    @Column(length = 5000)
    private String facultyName;

    private Integer graduation;
}
