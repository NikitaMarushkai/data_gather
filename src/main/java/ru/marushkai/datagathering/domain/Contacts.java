package ru.marushkai.datagathering.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "contacts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Contacts {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private VKUser vkUser;

    @Column(length = 5000)
    private String mobilePhone;

    @Column(length = 5000)
    private String homePhone;
}
