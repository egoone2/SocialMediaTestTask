package ru.osokin.tasklist.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ru.osokin.tasklist.domain.user.User;
import ru.osokin.tasklist.web.dto.validation.OnCreate;
import ru.osokin.tasklist.web.dto.validation.OnUpdate;

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
    @Length(max = 50, message = "header length must be shorter than 50", groups = {OnCreate.class, OnUpdate.class})
    private String header;
    @Column(name = "text_content")
    @Length(max = 255, message = "content length must be shorter than 255", groups = {OnCreate.class, OnUpdate.class})
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
