package ru.marushkai.datagathering.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.marushkai.datagathering.domain.Counters;

public interface CountersRepository extends JpaRepository<Counters, Long> {
}
