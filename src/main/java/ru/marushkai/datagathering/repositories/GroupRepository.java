package ru.marushkai.datagathering.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.marushkai.datagathering.domain.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
}
