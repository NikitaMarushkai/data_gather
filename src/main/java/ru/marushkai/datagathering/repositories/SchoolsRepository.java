package ru.marushkai.datagathering.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.marushkai.datagathering.domain.Schools;

@Repository
public interface SchoolsRepository extends JpaRepository<Schools, Long> {
}
