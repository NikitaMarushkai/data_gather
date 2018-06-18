package ru.marushkai.datagathering.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.marushkai.datagathering.domain.Education;

public interface EducationRepositroy extends JpaRepository<Education, Long> {
}
