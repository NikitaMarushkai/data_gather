package ru.marushkai.datagathering.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "personal")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Personal {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private VKUser vkUser;

    private Integer political;

    @Column(columnDefinition="TEXT")
    private String[] langs;

    @Column(columnDefinition="TEXT")
    private String religion;

    @Column(columnDefinition="TEXT")
    private String inspiredBy;

    private Integer peopleMain;

    private Integer lifeMain;

    private Integer smoking;

    private Integer alcohol;
}
