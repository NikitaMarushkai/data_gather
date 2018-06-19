package ru.marushkai.datagathering.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "military")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Military {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private VKUser vkUser;

    @Column(columnDefinition="TEXT")
    private String unit;

    private Integer unitId;

    private Integer countryId;

    private Integer year_from;

    private Integer year_until;
}
