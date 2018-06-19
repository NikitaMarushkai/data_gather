package ru.marushkai.datagathering.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.marushkai.datagathering.domain.Counters;

@Repository
public interface CountersRepository extends JpaRepository<Counters, Long> {
}
