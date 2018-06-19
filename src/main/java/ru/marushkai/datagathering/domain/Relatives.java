package ru.marushkai.datagathering.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "relatives")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Relatives {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private VKUser vkUser;

    private Integer relativeId;

    @Column(columnDefinition="TEXT")
    private String relativeName;
}
