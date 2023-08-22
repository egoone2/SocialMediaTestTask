package ru.osokin.tasklist.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostAddedResponse {

    private String header;
    private String content;
    private String authorUsername;
    private String fileName;
    private long size;
    private LocalDateTime whenAdded;

}
