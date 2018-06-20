package ru.marushkai.datagathering.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "personal")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Personal {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private VKUser vkUser;

    @Column(columnDefinition = "TEXT")
    private String political;

    @Column(columnDefinition="TEXT")
    private String[] langs;

    @Column(columnDefinition="TEXT")
    private String religion;

    @Column(columnDefinition="TEXT")
    private String inspiredBy;

    @Column(columnDefinition = "TEXT")
    private String peopleMain;

    @Column(columnDefinition = "TEXT")
    private String lifeMain;

    @Column(columnDefinition = "TEXT")
    private String smoking;

    @Column(columnDefinition = "TEXT")
    private String alcohol;

    public void setId(Long id) {
        this.id = id;
    }

    public void setVkUser(VKUser vkUser) {
        this.vkUser = vkUser;
    }

    public void setPolitical(Integer political) {
        if (political != null) {
            switch (political) {
                case 1:
                    this.political = "коммунистические";
                    break;
                case 2:
                    this.political = "социалистические";
                    break;
                case 3:
                    this.political = "умеренные";
                    break;
                case 4:
                    this.political = "либеральные";
                    break;
                case 5:
                    this.political = "консервативные";
                    break;
                case 6:
                    this.political = "монархические";
                    break;
                case 7:
                    this.political = "ультраконсервативные";
                    break;
                case 8:
                    this.political = "индифферентные";
                    break;
                case 9:
                    this.political = "либертарианские";
                    break;
            }
        }
    }

    public void setLangs(String[] langs) {
        this.langs = langs;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public void setInspiredBy(String inspiredBy) {
        this.inspiredBy = inspiredBy;
    }

    public void setPeopleMain(Integer peopleMain) {
        if (peopleMain != null) {
            switch (peopleMain) {
                case 1:
                    this.peopleMain = "ум и креативность";
                    break;
                case 2:
                    this.peopleMain = "доброта и честность";
                    break;
                case 3:
                    this.peopleMain = "красота и здоровье";
                    break;
                case 4:
                    this.peopleMain = "власть и богатство";
                    break;
                case 5:
                    this.peopleMain = "смелость и упорство";
                    break;
                case 6:
                    this.peopleMain = "юмор и жизнелюбие";
                    break;
            }
        }
    }

    public void setLifeMain(Integer lifeMain) {
        if (lifeMain != null) {
            switch (lifeMain) {
                case 1:
                    this.lifeMain = "семья и дети";
                    break;
                case 2:
                    this.lifeMain = "карьера и деньги";
                    break;
                case 3:
                    this.lifeMain = "развлечения и отдых";
                    break;
                case 4:
                    this.lifeMain = "наука и исследования";
                    break;
                case 5:
                    this.lifeMain = "совершенствование мира";
                    break;
                case 6:
                    this.lifeMain = "саморазвитие";
                    break;
                case 7:
                    this.lifeMain = "красота и искусство";
                    break;
                case 8:
                    this.lifeMain = "слава и влияние";
            }
        }
    }

    public void setSmoking(Integer smoking) {
        if (smoking != null) {
            switch (smoking) {
                case 1:
                    this.smoking = "резко негативное";
                    break;
                case 2:
                    this.smoking = "негативное";
                    break;
                case 3:
                    this.smoking = "компромиссное";
                    break;
                case 4:
                    this.smoking = "нейтральное";
                    break;
                case 5:
                    this.smoking = "положительное";
                    break;
            }
        }
    }

    public void setAlcohol(Integer alcohol) {
        if (alcohol != null) {
            switch (alcohol) {
                case 1:
                    this.alcohol = "резко негативное";
                    break;
                case 2:
                    this.alcohol = "негативное";
                    break;
                case 3:
                    this.alcohol = "компромиссное";
                    break;
                case 4:
                    this.alcohol = "нейтральное";
                    break;
                case 5:
                    this.alcohol = "положительное";
                    break;
            }
        }
    }
}
