package ru.marushkai.datagathering.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "wall_posts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WallPosts {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private VKUser vkUser;

    @ManyToOne
    @JoinColumn(name = "post_origin", nullable = true)
    private Subscriptions subscriptions;

    @Column(length = 5000)
    private String postContent;
}

