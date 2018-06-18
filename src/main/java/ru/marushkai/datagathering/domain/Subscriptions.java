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

    private Integer groupId;

    @Column(length = 5000)
    private String groupName;

    @Column(length = 5000)
    private String groupDescription;

    @Column(length = 5000)
    private String groupStatus;

    private Boolean isQuoted;


}
