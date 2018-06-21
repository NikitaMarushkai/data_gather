package ru.marushkai.datagathering.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.marushkai.datagathering.domain.Personal;
import ru.marushkai.datagathering.domain.VKUser;

@Repository
public interface PersonalRepositroy extends JpaRepository<Personal, Long> {

    Personal findByVkUser(VKUser vkUser);

}
