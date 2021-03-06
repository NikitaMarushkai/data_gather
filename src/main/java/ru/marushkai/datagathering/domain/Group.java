package ru.marushkai.datagathering.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "user_group")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Group {

    @Id
    @GeneratedValue
    private Long id;

    @Column(columnDefinition="TEXT")
    private String groupId;

    @Column(columnDefinition="TEXT")
    private String groupName;
}
