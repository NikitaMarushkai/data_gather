package ru.marushkai.datagathering.services;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.users.UserXtrCounters;
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

//    private List getUserSubscriptions(VKUser vkUser) {
//        vk.groups().get
//    }

//    private List getUserWallPosts(VKUser vkUser) {
//
//    }

    @Transactional
    public void saveUsers(List<String> userIds) {
        List<UserXtrCounters> users = getUserInfoById(userIds);
        users.forEach(u -> {
            VKUser user = new VKUser();
            user.setVkId(u.getId());
            user.setFirstName(u.getFirstName());
            user.setLastName(u.getLastName());
            user.setAbout(u.getAbout());
            user.setActivities(u.getActivities());
            user.setBdate(u.getBdate());
            user.setBooks(u.getBooks());
            if (u.getCareer() != null) {
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
                Set<City> citySet = new HashSet<>();
                City city = new City();
                city.setCityId(u.getCity().getId());
                city.setCityName(u.getCity().getTitle());
                city.setVkUser(user);
                citySet.add(city);
                user.setCity(citySet);
            }
            user.setConnections(u.getFacebookName());

            Set<Contacts> contacts = new HashSet<>();
            Contacts contacts1 = new Contacts();
            contacts1.setVkUser(user);
            contacts1.setHomePhone(u.getHomePhone());
            contacts1.setMobilePhone(u.getMobilePhone());
            contacts.add(contacts1);
            user.setContacts(contacts);

            if (u.getCounters() != null) {
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
                Set<Country> countries = new HashSet<>();
                Country country = new Country();
                country.setCountryId(u.getCountry().getId());
                country.setCountryName(u.getCountry().getTitle());
                country.setVkUser(user);
                countries.add(country);
                user.setCountry(countries);
            }

            if (u.getUniversities() != null) {
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

            user.setHomeTown(u.getHomeTown());
            user.setInterests(u.getInterests());
            if (u.getLastSeen() != null) {
                user.setLast_seen(u.getLastSeen().getTime());
            }

            if (u.getMilitary() != null) {
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

            user.setMovies(u.getMovies());
            user.setMusic(u.getMusic());

            if (u.getOccupation() != null) {
                Set<Occupation> occupations = new HashSet<>();
                Occupation occupation = new Occupation();
                occupation.setVkUser(user);
                occupation.setOccupationId(u.getOccupation().getId());
                occupation.setOccupationName(u.getOccupation().getName());
                occupation.setType(u.getOccupation().getType());
                user.setOccupation(occupations);
            }

            if (u.getPersonal() != null) {
                Personal personal = new Personal();
                personal.setVkUser(user);
                personal.setAlcohol(u.getPersonal().getAlcohol());
                personal.setInspiredBy(u.getPersonal().getInspiredBy());
                personal.setLangs(u.getPersonal().getLangs());
                personal.setLifeMain(u.getPersonal().getLifeMain());
                personal.setPeopleMain(u.getPersonal().getPeopleMain());
                personal.setPolitical(u.getPersonal().getPolitical());
                personal.setReligion(u.getPersonal().getReligion());
                personal.setSmoking(u.getPersonal().getSmoking());
                user.setPersonal(personal);
            }

            user.setPhotoId(u.getPhotoId());
            user.setQuotes(u.getQuotes());

            if (u.getRelatives() != null) {
                user.setRelatives(u.getRelatives().stream().map(relative -> {
                    Relatives relatives = new Relatives();
                    relatives.setVkUser(user);
                    relatives.setRelativeId(relative.getId());
                    relatives.setRelativeName(relative.getType());
                    return relatives;
                }).collect(Collectors.toSet()));
            }

            if (u.getSchools() != null) {
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
                user.setSex(u.getSex().getValue());
            }
            user.setTrending(u.isTrending());
            user.setTv(u.getTv());
            user.setVerified(u.isVerified());


            vkUserRepository.save(user);
            careerRepository.save(user.getCareer());
            cityRepository.save(user.getCity());
            contactsRepository.save(user.getContacts());
            countersRepository.save(user.getCounters());
            countryRepository.save(user.getCountry());
            educationRepositroy.save(user.getEducation());
            militaryRepository.save(user.getMilitary());
            occupationRepository.save(user.getOccupation());
            personalRepositroy.save(user.getPersonal());
            relativesRepository.save(user.getRelatives());
            schoolsRepository.save(user.getSchools());
        });
    }
}
