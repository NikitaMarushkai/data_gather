package ru.marushkai.datagathering.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.marushkai.datagathering.domain.Career;

import java.util.Set;

@Repository
public interface CareerRepository extends JpaRepository<Career, Long> {
}
