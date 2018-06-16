package ru.marushkai.datagathering.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "counters")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Counters {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private VKUser vkUser;

    private Integer albums;

    private Integer videos;

    private Integer audios;

    private Integer photos;

    private Integer notes;

    private Integer friends;

    private Integer groups;

    private Integer onlineFriends;

    private Integer userVideos;

    private Integer followers;

    private Integer pages;
}
