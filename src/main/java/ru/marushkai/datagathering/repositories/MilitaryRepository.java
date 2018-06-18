package ru.marushkai.datagathering.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.marushkai.datagathering.domain.Military;

public interface MilitaryRepository extends JpaRepository<Military, Long> {
}
