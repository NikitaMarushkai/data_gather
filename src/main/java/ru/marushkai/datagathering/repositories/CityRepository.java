package ru.marushkai.datagathering.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.marushkai.datagathering.domain.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
}
