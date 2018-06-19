package ru.marushkai.datagathering.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.marushkai.datagathering.domain.Subscriptions;

@Repository
public interface SubscriptionsRepository extends JpaRepository<Subscriptions, Long> {
}
