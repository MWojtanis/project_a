package company.review.demo.experience;

import company.review.demo.experience.users.PostService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.util.NoSuchElementException;

@SpringBootTest
public class ExperienceApplicationTests {

    @Autowired
    private PostService postService;

    @Before
    public void downloadPosts() {
        postService.getPostsFromExternalWebsite();
    }

    @Test
    public void updateNullId() {
        Assert.assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            postService.updatePost(null, "someTitle", "someBody");
        });
    }

    @Test
    public void updateMinusOneId() {
        Assert.assertThrows(NoSuchElementException.class, () -> {
            postService.updatePost(-1L, "someTitle", "someBody");
        });
    }

    @Test
    public void multipleTest() {
        var posts = postService.getPosts();
        if (!posts.isEmpty()) {
            var post = posts.get(0);
            postService.updatePost(post.id, "ABC", "CBD");
            if (postService.getPosts("ABC").isEmpty()) {
                Assert.fail("Update post not working.");
            }
            Assert.assertEquals(false, postService.updatePost(post.id, "ABC", "CBD"));
            Assert.assertEquals(false, postService.updatePost(post.id, "ABC", "CBD"));
            Assert.assertEquals(true,postService.deletePost(post.id));
            Assert.assertEquals(false,postService.deletePost(post.id));
            if (!postService.getPosts("ABC").isEmpty()) {
                Assert.fail("Delete post not working.");
            }
        }
    }


}
