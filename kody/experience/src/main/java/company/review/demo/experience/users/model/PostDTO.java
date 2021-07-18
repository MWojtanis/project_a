package company.review.demo.experience.users.model;

import company.review.demo.experience.users.database.PostEntity;
import lombok.AllArgsConstructor;


public class PostDTO {
    public Long id;
    public String title;
    public String body;

    public PostDTO(PostEntity postEntity) {
        this.id = postEntity.id;
        this.title = postEntity.title;
        this.body = postEntity.body;
    }
}
