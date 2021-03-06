package ru.marushkai.datagathering.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.marushkai.datagathering.domain.Relatives;

@Repository
public interface RelativesRepository extends JpaRepository<Relatives, Long> {
}
