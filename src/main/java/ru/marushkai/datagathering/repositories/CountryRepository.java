package ru.marushkai.datagathering.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.marushkai.datagathering.domain.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
