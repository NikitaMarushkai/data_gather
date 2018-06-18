package ru.marushkai.datagathering.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "career")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Career {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private VKUser vkUser;

    private Integer groupId;

    @Column(length = 5000)
    private String company;

    private Integer country_id;

    private Integer city_id;

    private Integer year_from;

    private Integer year_until;

    @Column(length = 5000)
    private String position;
}
