package au.com.domain.demo.tracker.repository;

import au.com.domain.demo.tracker.entity.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    List<Comment> findAllByIssueId(Long issueId);

}
