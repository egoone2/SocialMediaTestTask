package ru.osokin.tasklist.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.osokin.tasklist.domain.Post;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findPostsByAuthorId(Long id);


    @Query(value = "SELECT p.* FROM Post p INNER JOIN Users u ON u.id = p.author_id INNER JOIN User_subscriptions us ON U.id = us.channel_id  WHERE us.subscriber_id = ?1",
            countQuery = "SELECT count(*) from Users u INNER JOIN User_subscriptions us ON U.id = us.channel_id INNER JOIN Post p ON u.id = p.author_id WHERE us.subscriber_id = ?1",
            nativeQuery = true)
    Page<Post> findAllSubscriptionsPosts(Long userId, Pageable pageable);

}
