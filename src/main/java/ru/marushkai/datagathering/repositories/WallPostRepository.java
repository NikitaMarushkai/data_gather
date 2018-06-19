package ru.marushkai.datagathering.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.marushkai.datagathering.domain.WallPosts;

@Repository
public interface WallPostRepository extends JpaRepository<WallPosts, Long> {
}
