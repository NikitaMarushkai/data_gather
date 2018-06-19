package ru.marushkai.datagathering.services;

import com.vk.api.sdk.actions.Wall;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.groups.GroupFull;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import com.vk.api.sdk.objects.wall.WallPost;
import com.vk.api.sdk.objects.wall.WallPostFull;
import com.vk.api.sdk.queries.EnumParam;
import com.vk.api.sdk.queries.groups.GroupField;
import com.vk.api.sdk.queries.users.UserField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.marushkai.datagathering.controllers.VKAuthController;
import ru.marushkai.datagathering.domain.*;
import ru.marushkai.datagathering.repositories.*;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    VkApiClient vk;
    @Autowired
    VKUserRepository vkUserRepository;
    @Autowired
    CareerRepository careerRepository;
    @Autowired
    CityRepository cityRepository;
    @Autowired
    ContactsRepository contactsRepository;
    @Autowired
    CountersRepository countersRepository;
    @Autowired
    CountryRepository countryRepository;
    @Autowired
    EducationRepositroy educationRepositroy;
    @Autowired
    MilitaryRepository militaryRepository;
    @Autowired
    OccupationRepository occupationRepository;
    @Autowired
    PersonalRepositroy personalRepositroy;
    @Autowired
    RelativesRepository relativesRepository;
    @Autowired
    SchoolsRepository schoolsRepository;
    @Autowired
    SubscriptionsRepository subscriptionsRepository;
    @Autowired
    WallPostRepository wallPostRepository;
    @Autowired
    AnalysisRepository analysisRepository;

    private List<UserXtrCounters> getUserInfoById(List<String> userIds) {
        List<UserXtrCounters> users = new ArrayList<>();
        List<UserField> userFields = Arrays.asList(UserField.values());
        try {
            for (int i = 0, j = 500; i < userIds.size(); i = j, j += 500) {
                users.addAll(vk.users().get(VKAuthController.actor)
                        .userIds(userIds.subList(i, j))
                        .fields(userFields)
                        .execute());
                Thread.sleep(300);
            }
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return users;
    }

    private List<GroupFull> getUserSubscriptions(VKUser vkUser) {
        List<GroupFull> groupFulls = new ArrayList<>();
        try {
            groupFulls.addAll(vk.groups().getExtended(VKAuthController.actor)
                    .userId(vkUser.getVkId())
                    .fields(GroupField.STATUS, GroupField.DESCRIPTION, GroupField.SCREEN_NAME)
                    .count(1000)
                    .execute().getItems());
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return groupFulls;
    }

    private List<GroupFull> getUserQuotedGroups(VKUser vkUser) {
        List<GroupFull> wallPostFulls = new ArrayList<>();
        try {
            wallPostFulls.addAll(vk.wall().getExtended(VKAuthController.actor)
                    .ownerId(vkUser.getVkId())
                    .count(50)
                    .execute().getGroups());
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return wallPostFulls;
    }

    private List<WallPostFull> getUserWallPosts(VKUser vkUser) {
        List<WallPostFull> wallPosts = new ArrayList<>();
        try {
            wallPosts.addAll(vk.wall().get(VKAuthController.actor)
                    .ownerId(vkUser.getVkId())
                    .count(50)
                    .execute().getItems());
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return wallPosts;
    }

//    private Set<LikedPosts> getUserLikedPost(VKUser vkUser) {
////        Text text = new Text("")
//        try {
//            vk.fave().getPosts(VKAuthController.actor)
//                    .count(100)
//                    .offset(0)
//                    .extended(true)
//                    .execute();
//        } catch (ApiException e) {
//            e.printStackTrace();
//        } catch (ClientException e) {
//            e.printStackTrace();
//        }
//    }

    @Transactional
    public void saveUsers(List<String> userIds) {
        List<UserXtrCounters> users = getUserInfoById(userIds);
        users.forEach(u -> {
            Integer fieldsFilled = 0;
            VKUser user = new VKUser();
            user.setVkId(u.getId());
            if (u.getFirstName() != null) {
                fieldsFilled++;
                user.setFirstName(u.getFirstName());
            }
            if (u.getLastName() != null) {
                fieldsFilled++;
                user.setLastName(u.getLastName());
            }
            if (u.getAbout() != null) {
                fieldsFilled++;
                user.setAbout(u.getAbout());
            }
            if (u.getActivities() != null) {
                fieldsFilled++;
                user.setActivities(u.getActivities());
            }
            if (u.getBdate() != null) {
                fieldsFilled++;
                user.setBdate(u.getBdate());
            }
            if (u.getBooks() != null) {
                fieldsFilled++;
                user.setBooks(u.getBooks());
            }
            if (u.getCareer() != null) {
                fieldsFilled++;
                user.setCareer(u.getCareer().stream().map(career -> {
                    Career userCareer = new Career();
                    userCareer.setVkUser(user);
                    userCareer.setGroupId(career.getGroupId());
                    userCareer.setCompany(career.getCompany());
                    userCareer.setCountry_id(career.getCountryId());
                    userCareer.setCity_id(career.getCityId());
                    userCareer.setYear_from(career.getFrom());
                    userCareer.setYear_until(career.getUntil());
                    userCareer.setPosition(career.getPosition());
                    return userCareer;
                }).collect(Collectors.toSet()));
            }
            if (u.getCity() != null) {
                fieldsFilled++;
                Set<City> citySet = new HashSet<>();
                City city = new City();
                city.setCityId(u.getCity().getId());
                city.setCityName(u.getCity().getTitle());
                city.setVkUser(user);
                citySet.add(city);
                user.setCity(citySet);
            }
            if (u.getFacebookName() != null) {
                fieldsFilled++;
                user.setConnections(u.getFacebookName());
            }

            Set<Contacts> contacts = new HashSet<>();
            Contacts contacts1 = new Contacts();
            contacts1.setVkUser(user);
            if (u.getHomePhone() != null) {
                fieldsFilled++;
                contacts1.setHomePhone(u.getHomePhone());
            }
            if (u.getMobilePhone() != null) {
                fieldsFilled++;
                contacts1.setMobilePhone(u.getMobilePhone());
            }
            contacts.add(contacts1);
            user.setContacts(contacts);

            if (u.getCounters() != null) {
                fieldsFilled++;
                Counters counters = new Counters();
                counters.setAlbums(u.getCounters().getAlbums());
                counters.setAudios(u.getCounters().getAudios());
                counters.setFollowers(u.getCounters().getFollowers());
                counters.setFriends(u.getCounters().getFriends());
                counters.setGroups(u.getCounters().getGroups());
                counters.setNotes(u.getCounters().getNotes());
                counters.setOnlineFriends(u.getCounters().getOnlineFriends());
                counters.setPages(u.getCounters().getPages());
                counters.setPhotos(u.getCounters().getPhotos());
                counters.setUserVideos(u.getCounters().getUserVideos());
                counters.setVideos(u.getCounters().getVideos());
                counters.setVkUser(user);
                user.setCounters(counters);
            }

            if (u.getCountry() != null) {
                fieldsFilled++;
                Set<Country> countries = new HashSet<>();
                Country country = new Country();
                country.setCountryId(u.getCountry().getId());
                country.setCountryName(u.getCountry().getTitle());
                country.setVkUser(user);
                countries.add(country);
                user.setCountry(countries);
            }

            if (u.getUniversities() != null) {
                fieldsFilled++;
                user.setEducation(u.getUniversities().stream().map(university -> {
                    Education userEdu = new Education();
                    userEdu.setFaculty(u.getFaculty());
                    userEdu.setFacultyName(u.getFacultyName());
                    userEdu.setGraduation(u.getGraduation());
                    userEdu.setUniversity(u.getUniversity());
                    userEdu.setUniversityName(u.getUniversityName());
                    userEdu.setVkUser(user);
                    return userEdu;
                }).collect(Collectors.toSet()));
            }

            if (u.getHomeTown() != null) {
                fieldsFilled++;
                user.setHomeTown(u.getHomeTown());
            }
            if (u.getInterests() != null) {
                fieldsFilled++;
                user.setInterests(u.getInterests());
            }
            if (u.getLastSeen() != null) {
                user.setLast_seen(u.getLastSeen().getTime());
            }

            if (u.getMilitary() != null) {
                fieldsFilled++;
                user.setMilitary(u.getMilitary().stream().map(military -> {
                    Military userMil = new Military();
                    userMil.setVkUser(user);
                    userMil.setCountryId(military.getCountryId());
                    userMil.setUnit(military.getUnit());
                    userMil.setUnitId(military.getUnitId());
                    userMil.setYear_from(military.getFrom());
                    userMil.setYear_until(military.getUntil());
                    return userMil;
                }).collect(Collectors.toSet()));
            }

            if (u.getMovies() != null) {
                fieldsFilled++;
                user.setMovies(u.getMovies());
            }
            if (u.getMusic() != null) {
                fieldsFilled++;
                user.setMusic(u.getMusic());
            }

            if (u.getOccupation() != null) {
                fieldsFilled++;
                Set<Occupation> occupations = new HashSet<>();
                Occupation occupation = new Occupation();
                occupation.setVkUser(user);
                occupation.setOccupationId(u.getOccupation().getId());
                occupation.setOccupationName(u.getOccupation().getName());
                occupation.setType(u.getOccupation().getType());
                user.setOccupation(occupations);
            }

            if (u.getPersonal() != null) {
                fieldsFilled += 3;
                Personal personal = new Personal();
                personal.setVkUser(user);
                personal.setAlcohol(u.getPersonal().getAlcohol());
                personal.setInspiredBy(u.getPersonal().getInspiredBy());
                if (u.getPersonal().getLangs() != null) {
                    personal.setLangs(u.getPersonal().getLangs().toArray(new String[]{}));
                }
                personal.setLifeMain(u.getPersonal().getLifeMain());
                personal.setPeopleMain(u.getPersonal().getPeopleMain());
                personal.setPolitical(u.getPersonal().getPolitical());
                personal.setReligion(u.getPersonal().getReligion());
                personal.setSmoking(u.getPersonal().getSmoking());
                user.setPersonal(personal);
            }

            if (u.getPhotoId() != null) {
                fieldsFilled++;
                user.setPhotoId(u.getPhotoId());
            }
            if (u.getQuotes() != null) {
                fieldsFilled++;
                user.setQuotes(u.getQuotes());
            }

            if (u.getRelatives() != null) {
                fieldsFilled++;
                user.setRelatives(u.getRelatives().stream().map(relative -> {
                    Relatives relatives = new Relatives();
                    relatives.setVkUser(user);
                    relatives.setRelativeId(relative.getId());
                    relatives.setRelativeName(relative.getType());
                    return relatives;
                }).collect(Collectors.toSet()));
            }

            if (u.getSchools() != null) {
                fieldsFilled += 3;
                user.setSchools(u.getSchools().stream().map(school -> {
                    Schools schools = new Schools();
                    schools.setVkUser(user);
                    schools.setCityId(school.getCity());
                    schools.setSchoolId(school.getId());
                    schools.setSchoolName(school.getName());
                    schools.setYearFrom(school.getYearFrom());
                    schools.setYearTo(school.getYearTo());
                    schools.setYearGraduated(school.getYearGraduated());
                    schools.setClassLetter(school.getClassName());
                    schools.setType(school.getTypeStr());
                    return schools;
                }).collect(Collectors.toSet()));
            }

            if (u.getSex() != null) {
                fieldsFilled++;
                user.setSex(u.getSex().getValue());
            }

            user.setTrending(u.isTrending());

            if (u.getTv() != null) {
                fieldsFilled++;
                user.setTv(u.getTv());
            }
            user.setVerified(u.isVerified());


            List<GroupFull> userSubscription = getUserSubscriptions(user);

            if (!userSubscription.isEmpty()) {
                user.setSubscriptions(userSubscription.stream().map(subscr -> {
                    Subscriptions subscriptions = new Subscriptions();
                    subscriptions.setGroupId(subscr.getId());
                    subscriptions.setGroupName(subscr.getScreenName());
                    subscriptions.setGroupStatus(subscr.getStatus());
                    subscriptions.setGroupDescription(subscr.getDescription());
                    subscriptions.setVkUser(user);
                    return subscriptions;
                }).collect(Collectors.toSet()));
            }

            List<GroupFull> quotedGroups = getUserQuotedGroups(user);

            Set<Subscriptions> subscriptions = user.getSubscriptions();

            if (subscriptions != null && !subscriptions.isEmpty()) {
                subscriptions.forEach(subscr -> {
                    if (quotedGroups.stream().map(GroupFull::getId).collect(Collectors.toSet()).contains(subscr.getGroupId())) {
                        subscr.setIsQuoted(true);
                    }
                });
                user.setSubscriptions(subscriptions);
            }


            //Если есть copyHistory - устанавливать subscriptions по ownerId из copyHistory!
            List<WallPostFull> wallPosts = getUserWallPosts(user);

            if (!wallPosts.isEmpty()) {
                user.setWallPosts(wallPosts.stream().map(post -> {
                    WallPosts wallPost = new WallPosts();
                    wallPost.setVkUser(user);
                    wallPost.setPostContent(post.getText());
                    if (post.getCopyHistory() != null && !post.getCopyHistory().isEmpty()){
                        post.getCopyHistory().stream().map(WallPost::getOwnerId).forEach(id -> {
                            if (subscriptions.stream().map(Subscriptions::getGroupId).collect(Collectors.toSet()).contains(id)) {
                                wallPost.setSubscriptions(subscriptions.stream()
                                        .filter(s -> s.getGroupId().equals(id))
                                        .collect(Collectors.toList())
                                        .get(0));
                            }
                        });
                    }
                    return wallPost;
                }).collect(Collectors.toSet()));
            }

            //Analysis results region
            AnalysisResult result = new AnalysisResult();
            result.setVkUser(user);
            result.setReadyToProvideInfo(user.getClass().getDeclaredFields().length / fieldsFilled.doubleValue() * 100);
            String obviousInterests = "";
            if (user.getMusic() != null) {
                obviousInterests += "Музыка: " + user.getMusic() + " ;";
            }
            if (user.getBooks() != null) {
                obviousInterests += "Книги: " + user.getBooks() + " ;";
            }
            if (user.getTv() != null) {
                obviousInterests += "Телепередачи: " + user.getTv() + " ;";
            }
            if (user.getMovies() != null) {
                obviousInterests += "Фильмы: " + user.getMovies() + " ;";
            }
            result.setNonEduInterests(obviousInterests);

            result.setQuotedGroups(user.getSubscriptions().stream().filter(Subscriptions::getIsQuoted).map(s -> {
                String group = "";
                group += "Название группы: " + s.getGroupName() + ", Статус группы: " + s.getGroupStatus() + ", " +
                        "Описание группы: " + s.getGroupDescription() + ";";
                return group;
            }).collect(Collectors.joining("\n--------------\n")));

            Set<String> eduKeywords = new HashSet<>();
            eduKeywords.add("школ");
            eduKeywords.add("университ");
            eduKeywords.add("инстит");
            eduKeywords.add("вуз");
            eduKeywords.add("образов");
            eduKeywords.add("учен");
            eduKeywords.add("олимпиад");
            eduKeywords.add("поступлен");
            eduKeywords.add("учеб");
            Set<String> knowledgeTechnicalKeywords = new HashSet<>();
            knowledgeTechnicalKeywords.add("электр");
            knowledgeTechnicalKeywords.add("нано");
            knowledgeTechnicalKeywords.add("вибр");
            knowledgeTechnicalKeywords.add("спец");
            knowledgeTechnicalKeywords.add("инструм");
            knowledgeTechnicalKeywords.add("авто");
            knowledgeTechnicalKeywords.add("it");
            knowledgeTechnicalKeywords.add("информ");
            knowledgeTechnicalKeywords.add("техн");
            knowledgeTechnicalKeywords.add("визуализ");
            knowledgeTechnicalKeywords.add("программ");
            knowledgeTechnicalKeywords.add("математик");
            knowledgeTechnicalKeywords.add("исследован");
            knowledgeTechnicalKeywords.add("физик");
            knowledgeTechnicalKeywords.add("физич");
            Set<String> knowledgeHumanKeywords = new HashSet<>();
            knowledgeHumanKeywords.add("литератур");
            knowledgeHumanKeywords.add("музык");
            knowledgeHumanKeywords.add("театр");
            knowledgeHumanKeywords.add("соц");
            knowledgeHumanKeywords.add("опрос");
            knowledgeHumanKeywords.add("фильм");
            knowledgeHumanKeywords.add("кино");
            knowledgeHumanKeywords.add("искусств");
            knowledgeHumanKeywords.add("истор");
            knowledgeHumanKeywords.add("культур");
            knowledgeHumanKeywords.add("религ");
            knowledgeHumanKeywords.add("поэт");
            knowledgeHumanKeywords.add("поэз");
            knowledgeHumanKeywords.add("педагог");

            eduKeywords.forEach(keyword -> {
                if (user.getWallPosts().stream()
                        .map(WallPosts::getPostContent)
                        .collect(Collectors.joining(";")).contains(keyword)) {
                    result.setIsInterestedInEdu(true);
                }
            });

            Integer techOccurences = 0;
            for (String keyword : knowledgeTechnicalKeywords) {
                if (user.getWallPosts().stream()
                        .map(WallPosts::getPostContent)
                        .collect(Collectors.joining(";")).contains(keyword)) {
                    techOccurences++;
                }
            }

            Integer humanOccurences = 0;
            for (String keyword : knowledgeHumanKeywords) {
                if (user.getWallPosts().stream()
                        .map(WallPosts::getPostContent)
                        .collect(Collectors.joining(";")).contains(keyword)) {
                    humanOccurences++;
                }
            }

            Integer totalOccurences = humanOccurences + techOccurences;

            Double humanPercentage = humanOccurences / totalOccurences * 100.0;
            Double techPercentage = techOccurences / totalOccurences * 100.0;

            result.setKnowledgeSpectre("Процент технической заинтересованности: " + techPercentage + "; " +
                    "Процент гуманитарной заинтересованности: " + humanPercentage);
            /*
            3. Заполнить конфликтность
            4. Заполнить реальный опыт
             */
            //End analysis results region

            vkUserRepository.save(user);
            if (user.getCareer() != null) {
                careerRepository.save(user.getCareer());
            }
            if (user.getCity() != null) {
                cityRepository.save(user.getCity());
            }
            if (user.getContacts() != null) {
                contactsRepository.save(user.getContacts());
            }
            if (user.getCounters() != null) {
                countersRepository.save(user.getCounters());
            }
            if (user.getCountry() != null) {
                countryRepository.save(user.getCountry());
            }
            if (user.getEducation() != null) {
                educationRepositroy.save(user.getEducation());
            }
            if (user.getMilitary() != null) {
                militaryRepository.save(user.getMilitary());
            }
            if (user.getOccupation() != null) {
                occupationRepository.save(user.getOccupation());
            }
            if (user.getPersonal() != null) {
                personalRepositroy.save(user.getPersonal());
            }
            if (user.getRelatives() != null) {
                relativesRepository.save(user.getRelatives());
            }
            if (user.getSchools() != null) {
                schoolsRepository.save(user.getSchools());
            }
            if (user.getSubscriptions() != null) {
                subscriptionsRepository.save(user.getSubscriptions());
            }
            if (user.getWallPosts() != null) {
                wallPostRepository.save(user.getWallPosts());
            }
            if (user.getAnalysisResult() != null) {
                analysisRepository.save(user.getAnalysisResult());
            }
        });
    }
}
