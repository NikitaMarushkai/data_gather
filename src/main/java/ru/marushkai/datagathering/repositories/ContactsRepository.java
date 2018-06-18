package ru.marushkai.datagathering.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.marushkai.datagathering.domain.Contacts;

@Repository
public interface ContactsRepository extends JpaRepository<Contacts, Long> {
}
