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
    @Column(length = 5000)
    private String about;
    @Column(length = 5000)
    private String activities;
    @Column(length = 5000)
    private String bdate;
    @Column(length = 5000)
    private String books;

    @OneToMany(mappedBy = "vkUser")
    private Set<Career> career;

    @OneToMany(mappedBy = "vkUser")
    private Set<City> city;

    @Column(length = 5000)
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

    @Column(length = 5000)
    private String homeTown;
    @Column(length = 5000)
    private String interests;
    private Integer last_seen;

    @OneToMany(mappedBy = "vkUser")
    private Set<Military> military;

    @Column(length = 5000)
    private String movies;
    @Column(length = 5000)
    private String music;

    @OneToMany(mappedBy = "vkUser")
    private Set<Occupation> occupation;


    /* occupation - информация о текущем роде занятия пользователя. Объект, содержащий следующие поля:
    type (string) — тип. Возможные значения:
    work — работа;
    school — среднее образование;
    university — высшее образование.
    id (integer) — идентификатор школы, вуза, сообщества компании (в которой пользователь работает);
    name (string) — название школы, вуза или места работы;*/
    @OneToOne
    @PrimaryKeyJoinColumn
    private Personal personal;
    /* информация о полях из раздела «Жизненная позиция».
     political (integer) — политические предпочтения. Возможные значения:
             1 — коммунистические;
 2 — социалистические;
 3 — умеренные;
 4 — либеральные;
 5 — консервативные;
 6 — монархические;
 7 — ультраконсервативные;
 8 — индифферентные;
 9 — либертарианские.
         langs (array) — языки.
             religion (string) — мировоззрение.
             inspired_by (string) — источники вдохновения.
     people_main (integer) — главное в людях. Возможные значения:
             1 — ум и креативность;
 2 — доброта и честность;
 3 — красота и здоровье;
 4 — власть и богатство;
 5 — смелость и упорство;
 6 — юмор и жизнелюбие.
             life_main (integer) — главное в жизни. Возможные значения:
             1 — семья и дети;
 2 — карьера и деньги;
 3 — развлечения и отдых;
 4 — наука и исследования;
 5 — совершенствование мира;
 6 — саморазвитие;
 7 — красота и искусство;
 8 — слава и влияние;
     smoking (integer) — отношение к курению. Возможные значения:
             1 — резко негативное;
 2 — негативное;
 3 — компромиссное;
 4 — нейтральное;
 5 — положительное.
         alcohol (integer) — отношение к алкоголю. Возможные значения:
             1 — резко негативное;
 2 — негативное;
 3 — компромиссное;
 4 — нейтральное;
 5 — положительное.*/
    @Column(length = 5000)
    private String photoId;
    @Column(length = 5000)
    private String quotes;

    @OneToMany(mappedBy = "vkUser")
    private Set<Relatives> relatives; // Родственники

    @OneToMany(mappedBy = "vkUser")
    private Set<Schools> schools;


    private Integer sex;
    private Boolean trending; //информация о том, есть ли на странице пользователя «огонёк».
    @Column(length = 5000)
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
