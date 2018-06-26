package au.com.domain.demo.tracker.repository;

import au.com.domain.demo.tracker.entity.Issue;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IssueRepository extends CrudRepository<Issue, Long> {
}
