package company.review.demo.experience.users.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long>, JpaSpecificationExecutor<PostEntity> {
    List<PostEntity> findByTitleContainingAndDeletedFalse(String title);

    List<PostEntity> findByDeletedFalse();
}