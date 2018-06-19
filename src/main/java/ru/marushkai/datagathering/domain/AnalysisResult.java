package ru.marushkai.datagathering.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "analysis_result")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AnalysisResult {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private VKUser vkUser;

    private Boolean isInterestedInEdu;

    @Column(columnDefinition="TEXT")
    private String knowledgeSpectre;

    @Column(columnDefinition="TEXT")
    private String nonEduInterests;

    private Boolean isConflict;

    private Boolean realExperience;

    private Double readyToProvideInfo;

    @Column(columnDefinition="TEXT")
    private String quotedGroups;
}
