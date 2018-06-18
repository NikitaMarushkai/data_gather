package ru.marushkai.datagathering.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.marushkai.datagathering.domain.Relatives;

public interface RelativesRepository extends JpaRepository<Relatives, Long> {
}
