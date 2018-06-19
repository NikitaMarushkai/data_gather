package ru.marushkai.datagathering.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.marushkai.datagathering.domain.Military;

@Repository
public interface MilitaryRepository extends JpaRepository<Military, Long> {
}
