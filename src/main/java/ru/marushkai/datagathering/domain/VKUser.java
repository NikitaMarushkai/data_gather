package ru.marushkai.datagathering.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "vk_user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VKUser {

    @Id
    @GeneratedValue
    private Long id;

    private Integer vkId;

    @Column(length = 1024)
    private String firstName;
    @Column(length = 1024)
    private String lastName;

    //From documentation
    @Column(columnDefinition="TEXT")
    private String about;
    @Column(columnDefinition="TEXT")
    private String activities;
    @Column(columnDefinition="TEXT")
    private String bdate;
    @Column(columnDefinition="TEXT")
    private String books;

    @OneToMany(mappedBy = "vkUser")
    private Set<Career> career;

    @OneToMany(mappedBy = "vkUser")
    private Set<City> city;

    @Column(columnDefinition="TEXT")
    private String connections;

    @OneToMany(mappedBy = "vkUser")
    private Set<Contacts> contacts;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Counters counters;

    @OneToMany(mappedBy = "vkUser")
    private Set<Country> country;

    @OneToMany(mappedBy = "vkUser")
    private Set<Education> education;

    @Column(columnDefinition="TEXT")
    private String homeTown;
    @Column(columnDefinition="TEXT")
    private String interests;
    private Integer last_seen;

    @OneToMany(mappedBy = "vkUser")
    private Set<Military> military;

    @Column(columnDefinition="TEXT")
    private String movies;
    @Column(columnDefinition="TEXT")
    private String music;

    @OneToMany(mappedBy = "vkUser")
    private Set<Occupation> occupation;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Personal personal;

    @Column(columnDefinition="TEXT")
    private String photoId;
    @Column(columnDefinition="TEXT")
    private String quotes;

    @OneToMany(mappedBy = "vkUser")
    private Set<Relatives> relatives; // Родственники

    @OneToMany(mappedBy = "vkUser")
    private Set<Schools> schools;


    private String sex;

    public void setSex(Integer sex) {
        if (sex != null) {
            switch (sex) {
                case 0:
                    this.sex = "пол не указан";
                    break;
                case 1:
                    this.sex = "женский";
                    break;
                case 2:
                    this.sex = "мужской";
                    break;
            }
        }
    }
    private Boolean trending; //информация о том, есть ли на странице пользователя «огонёк».
    @Column(columnDefinition="TEXT")
    private String tv;
    private Boolean verified;

    @OneToMany(mappedBy = "vkUser")
    private Set<Subscriptions> subscriptions;

    @OneToOne
    @PrimaryKeyJoinColumn
    private AnalysisResult analysisResult;

    @OneToMany(mappedBy = "vkUser")
    private Set<WallPosts> wallPosts;

    @OneToMany(mappedBy = "vkUser")
    private Set<LikedPosts> likedPosts;
}
