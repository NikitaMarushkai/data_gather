package ru.marushkai.datagathering.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.marushkai.datagathering.domain.Personal;

@Repository
public interface PersonalRepositroy extends JpaRepository<Personal, Long> {
}
