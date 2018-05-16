package ru.marushkai.datagathering.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

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

    private String name;
    private String surname;
    private String education;
    private String interests;
    private String workExperience;
    private String city;
    private String homeCity;
    private String[] languages;
    private String[] music;

    private String vkID;
    private String[] groups;
    private String groupLikes;
    private String wallTexts;

}
