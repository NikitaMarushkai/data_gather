package ru.marushkai.datagathering.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "liked_posts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LikedPosts {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private VKUser vkUser;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = true)
    private Subscriptions subscriptions;

    @Column(columnDefinition="TEXT")
    private String postContent;
}
