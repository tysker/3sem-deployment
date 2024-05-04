package dk.lyngby.dto;

import dk.lyngby.model.Post;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostDto {

    private final int id;
    private final String header;
    private final String content;

    public PostDto(Post post) {
        this.id = post.getId();
        this.header = post.getHeader();
        this.content = post.getContent();
    }

    public static List<PostDto> toPostDtoList(List<Post> posts) {
        return posts.stream().map(PostDto::new).collect(Collectors.toList());
    }
}
