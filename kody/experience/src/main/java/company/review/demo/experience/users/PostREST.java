package company.review.demo.experience.users;

import company.review.demo.experience.users.model.PostDTO;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class PostREST {

    private final PostService postService;

    public PostREST(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/api/comments/get")
    public List<PostDTO> getPosts(@RequestParam(required = false) String title) {
        if (StringUtils.isNotEmpty(title)) {
            return postService.getPosts(title);
        }
        return postService.getPosts();
    }


    @GetMapping("/api/comments/update")
    public String updateAllPosts() {
        postService.getPostsFromExternalWebsite();
        return "Update database started.";
    }

    @GetMapping("/api/comments/edit/{id}")
    public String editOnePost(@PathVariable(value = "id") String id, @RequestParam String title, @RequestParam String body) {
        var postId = Long.valueOf(id);
        if(postService.updatePost(postId, title, body)){
            return "Post updated";
        }
        return "Post not requeue updating";

    }

    @GetMapping("/api/comments/delete/{id}")
    public String deleteOnePost(@PathVariable(value = "id") String id) {
        var postId = Long.valueOf(id);
        if(postService.deletePost(postId)){
            return "Post deleted.";
        }
        return "Post already removed.";

    }

    @ExceptionHandler({NoSuchElementException.class, NumberFormatException.class})
    public String handleException() {
        return "That post doesn't exist.";
    }

    @Scheduled(cron = "0 1 1 ? * *")
    public void getPostsFromExternalWebsite() {
        postService.getPostsFromExternalWebsite();
    }
}
