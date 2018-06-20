package ru.marushkai.datagathering.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.marushkai.datagathering.domain.VKUser;

import java.util.List;

@Repository
public interface VKUserRepository extends JpaRepository<VKUser, Long> {
    List<VKUser> findAllByFirstNameAndLastNameLike(String firstName, String lastName);
}
