package au.com.domain.demo.tracker.controller;

import au.com.domain.demo.actor.entity.User;
import au.com.domain.demo.tracker.entity.Comment;
import au.com.domain.demo.tracker.entity.Issue;
import au.com.domain.demo.tracker.model.CommentModel;
import au.com.domain.demo.tracker.repository.CommentRepository;
import au.com.domain.demo.tracker.repository.IssueRepository;
import au.com.domain.demo.tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("issues/{issueId}/comments")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IssueRepository issueRepository;


    @GetMapping
    public ResponseEntity<List<CommentModel>> getAllComments(@PathVariable Long issueId) {

        List<CommentModel> comments = commentRepository.findAllByIssueId(issueId).stream()
                .map(comment -> asModel(comment))
                .collect(toList());

        System.out.println(issueId + ", " + comments.size());

        return new ResponseEntity<>(comments, HttpStatus.OK);

    }

    private CommentModel asModel(Comment comment) {

        return new CommentModel(
                comment.getUser().getName(),
                comment.getIssue().getId(),
                comment.getContent()
        );

    }


    @PostMapping
    public ResponseEntity<CommentModel> createComment(@Valid @RequestBody CommentModel commentModel) {

        Comment comment = asEntity(commentModel);
        if (comment == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        comment = commentRepository.save(comment);
        return new ResponseEntity<>(asModel(comment), HttpStatus.CREATED);

    }

    private Comment asEntity(CommentModel commentModel) {

        User user = userRepository.findByName(commentModel.getUser());
        if (user == null) {
            return null;
        }

        Issue issue = issueRepository.findById(commentModel.getIssueId()).orElse(null);
        if (issue == null) {
            return null;
        }

        return new Comment(
                user,
                issue,
                commentModel.getContent()
        );

    }


}
