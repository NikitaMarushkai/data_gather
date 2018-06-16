package ru.marushkai.datagathering.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "subscriptions")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Subscriptions {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private VKUser vkUser;

    @OneToMany(mappedBy = "subscriptions")
    private Set<WallPosts> wallPosts;

    @OneToMany(mappedBy = "subscriptions")
    private Set<LikedPosts> likedPosts;

    private Long groupId;

    private String groupName;

    private String groupGenre;

    private Boolean isQuoted;


}
