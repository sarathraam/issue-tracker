package au.com.domain.demo.tracker.controller;


import au.com.domain.demo.actor.entity.User;
import au.com.domain.demo.tracker.entity.Issue;
import au.com.domain.demo.tracker.entity.Status;
import au.com.domain.demo.tracker.model.IssueModel;
import au.com.domain.demo.tracker.model.QueryCriteria;
import au.com.domain.demo.tracker.repository.IssueRepository;
import au.com.domain.demo.tracker.repository.IssueSearchRepository;
import au.com.domain.demo.tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("issues")
public class IssueController {

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IssueSearchRepository issueSearchRepository;


    @GetMapping
    public ResponseEntity<List<IssueModel>> getIssues(QueryCriteria queryCriteria) {

        List<IssueModel> issues = new ArrayList<>();

        issueSearchRepository.findAllByCriteria(queryCriteria)
                .forEach(issue -> issues.add(asModel(issue)));


        return new ResponseEntity<>(issues, HttpStatus.OK);

    }

    private IssueModel asModel(Issue issue) {

        return new IssueModel(
                issue.getTitle(),
                issue.getDescription(),
                issue.getStatus().name(),
                issue.getReporter().getName(),
                issue.getAssignee() == null ? null : issue.getAssignee().getName(),
                issue.getCreatedOn(),
                issue.getCompletedOn()
        );

    }


    @GetMapping("/{id}")
    public ResponseEntity<IssueModel> getIssue(@PathVariable Long id) {

        Issue issue = issueRepository.findById(id).orElse(null);

        if (issue == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(asModel(issue), HttpStatus.OK);

    }


    @PostMapping
    public ResponseEntity<IssueModel> createIssue(@RequestBody IssueModel issueModel) {

        if (!hasMandatoryValues(issueModel)) {
            return new ResponseEntity<>(issueModel, HttpStatus.BAD_REQUEST);
        }

        Issue issue = issueRepository.save(asEntity(issueModel));
        return new ResponseEntity<>(asModel(issue), HttpStatus.CREATED);

    }

    private boolean hasMandatoryValues(IssueModel issueModel) {

        if (StringUtils.isEmpty(issueModel.getTitle())) {
            issueModel.getValidationErrors().add("Title is a must");
        }

        if (StringUtils.isEmpty(issueModel.getDescription())) {
            issueModel.getValidationErrors().add("Add some description");
        }

        if (StringUtils.isEmpty(issueModel.getReporter())) {
            issueModel.getValidationErrors().add("Reporter name needed");
        }

        return issueModel.getValidationErrors().size() == 0;
    }

    private Issue asEntity(IssueModel issueModel) {

        User user = userRepository.findByName(issueModel.getReporter());

        Issue issue = new Issue(
                issueModel.getTitle(),
                issueModel.getDescription(),
                user != null ?
                        user :
                        new User(issueModel.getReporter())
        );

        // optionally set - so not part of constructor
        issue.setAssignee(
                StringUtils.isEmpty(issueModel.getAssignee()) ?
                        null :
                        new User(issueModel.getAssignee())
        );

        return issue;

    }

    @PutMapping("/{id}")
    public ResponseEntity<IssueModel> updateIssue(@PathVariable Long id,
                                                  @Valid @RequestBody IssueModel issueModel) {

        Issue issue = issueRepository.findById(id).orElse(null);

        if (issue == null) {
            return new ResponseEntity<>(issueModel, HttpStatus.NOT_FOUND);
        }
        else if (!validRequest(issue, issueModel)) {
            return new ResponseEntity<>(issueModel, HttpStatus.BAD_REQUEST);
        }

        copy(issue, issueModel);    // copies information available partially

        issue = issueRepository.save(issue);

        return new ResponseEntity<>(asModel(issue), HttpStatus.ACCEPTED);

    }

    private boolean validRequest(Issue issue, IssueModel issueModel) {

        if (issueModel.getCompletedOn() != null) {
            if (issue.getCreatedOn().after(issueModel.getCompletedOn())) {
                issueModel.getValidationErrors().add("Completed date cannot be before Created date");
            }

            if (issueModel.getCompletedOn().after(new Date())) {
                issueModel.getValidationErrors().add("Completed date is set to future");
            }
        }

        return issueModel.getValidationErrors().size() == 0;

    }

    private void copy(Issue issue, IssueModel issueModel) {

        if (!StringUtils.isEmpty(issueModel.getTitle())) {
            issue.setTitle(issueModel.getTitle());
        }

        if (!StringUtils.isEmpty(issueModel.getDescription())) {
            issue.setDescription(issueModel.getDescription());
        }

        if (!StringUtils.isEmpty(issueModel.getStatus())) {
            issue.setStatus(Status.valueOf(issueModel.getStatus()));
        }

        if (!StringUtils.isEmpty(issueModel.getAssignee())) {

            User user = userRepository.findByName(issueModel.getAssignee());
            issue.setAssignee(
                    user != null ?
                            user :
                            new User(issueModel.getAssignee())
            );

        }

        if (!Objects.isNull(issueModel.getCompletedOn())) {
            issue.setCompletedOn(issueModel.getCompletedOn());
        }

    }


    @DeleteMapping("/{id}")
    public ResponseEntity deleteIssue(@PathVariable Long id) {

        Issue issue = issueRepository.findById(id).orElse(null);

        if (issue == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        issueRepository.delete(issue);
        return new ResponseEntity(HttpStatus.OK);

    }

}
