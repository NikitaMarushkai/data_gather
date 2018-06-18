package ru.marushkai.datagathering.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.marushkai.datagathering.domain.Schools;

public interface SchoolsRepository extends JpaRepository<Schools, Long> {
}
