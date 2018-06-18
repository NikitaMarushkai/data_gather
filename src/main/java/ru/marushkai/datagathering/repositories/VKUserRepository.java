package ru.marushkai.datagathering.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.marushkai.datagathering.domain.VKUser;

@Repository
public interface VKUserRepository extends JpaRepository<VKUser, Long> {
}
