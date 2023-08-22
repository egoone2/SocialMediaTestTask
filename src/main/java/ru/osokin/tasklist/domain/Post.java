package ru.osokin.tasklist.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.osokin.tasklist.domain.user.User;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Post")
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "header")
    private String header;
    @Column(name = "text_content")
    private String content;
    @Column(name = "filename")
    private String filename;

    @ManyToOne()
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User author;

    @Column(name = "when_posted")
    @Temporal(value = TemporalType.TIMESTAMP)
    private LocalDateTime whenPosted;


}
