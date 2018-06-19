package ru.marushkai.datagathering.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.marushkai.datagathering.domain.Education;

@Repository
public interface EducationRepositroy extends JpaRepository<Education, Long> {
}
