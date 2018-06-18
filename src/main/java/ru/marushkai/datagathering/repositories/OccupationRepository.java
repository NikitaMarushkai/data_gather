package ru.marushkai.datagathering.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.marushkai.datagathering.domain.Occupation;

public interface OccupationRepository extends JpaRepository<Occupation, Long> {
}
