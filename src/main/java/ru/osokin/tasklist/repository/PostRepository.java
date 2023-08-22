package ru.osokin.tasklist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.osokin.tasklist.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {


}
