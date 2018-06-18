package ru.marushkai.datagathering.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.marushkai.datagathering.domain.Personal;

public interface PersonalRepositroy extends JpaRepository<Personal, Long> {
}
