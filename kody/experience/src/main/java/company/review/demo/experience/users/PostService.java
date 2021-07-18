package company.review.demo.experience.users;

import company.review.demo.experience.users.model.PostDTO;
import company.review.demo.experience.users.database.PostEntity;
import company.review.demo.experience.users.database.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@Service
public class PostService {

    private String url;
    private final PostRepository postRepository;
    private final WebClient.Builder webClientBuilder;
    private Logger logger = LoggerFactory.getLogger(PostService.class);

    public PostService(@Value("${endpoint.url}") String url, PostRepository postRepository, WebClient.Builder webClientBuilder) {
        this.url = url;
        this.postRepository = postRepository;
        this.webClientBuilder = webClientBuilder;
    }

    public List<PostDTO> getPosts() {
        var postsDB = postRepository.findByDeletedFalse()
                .stream()
                .map(PostDTO::new)
                .collect(Collectors.toList());
        return postsDB;
    }

    public List<PostDTO> getPosts(String title) {
        var postsDB = postRepository.findByTitleContainingAndDeletedFalse(title)
                .stream()
                .map(PostDTO::new)
                .collect(Collectors.toList());
        return postsDB;
    }

    public boolean updatePost(Long postId, String title, String body) {
        var postDB = postRepository.findById(postId);
        if (postDB.isEmpty()) {
            throw new NoSuchElementException();
        }
        var post = postDB.get();
        if (post.body.equalsIgnoreCase(body) &&
                post.title.equalsIgnoreCase(title)) {
            return false;
        }
        if (title != null) {
            post.title = title;
        }
        if (body != null) {
            post.body = body;
        }
        post.edited = true;
        postRepository.save(post);
        return true;
    }

    public boolean deletePost(Long postId) {
        var postDB = postRepository.findById(postId);
        if (postDB.isEmpty()) {
            throw new NoSuchElementException();
        }
        var post = postDB.get();
        if (post.deleted) {
            return false;
        }
        post.deleted = true;
        postRepository.save(post);
        return true;
    }

    public void getPostsFromExternalWebsite() {
        logger.info("START 0001: Updating DB with posts from https://jsonplaceholder.typicode.com/comments....");
        Flux<PostEntity> postFlux = WebClient.create()
                .get()
                .uri(url)
                .retrieve()
                .bodyToFlux(PostEntity.class);

        postFlux.subscribe(this::updatePostsInDatabase);
        postFlux.doFinally(signalType -> logger.info("END 0001: Updated ended. "));
    }


    private void updatePostsInDatabase(PostEntity postFromUrl) {
        var postDB = postRepository.findById(postFromUrl.id);
        if (postDB.isPresent()) {
            var post = postDB.get();
            if (post.deleted || post.edited) {
                return;
            }
        }
        postRepository.save(postFromUrl);
    }
}