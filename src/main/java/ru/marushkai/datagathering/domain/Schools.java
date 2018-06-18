package ru.marushkai.datagathering.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "schools")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Schools {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private VKUser vkUser;

    @Column(length = 5000)
    private String schoolId;

    private Integer countryId;

    private Integer cityId;

    private String schoolName;

    private Integer yearFrom;

    private Integer yearTo;

    private Integer yearGraduated;

    @Column(length = 5000)
    private String classLetter;


    @Column(length = 5000)
    private String type;
}
