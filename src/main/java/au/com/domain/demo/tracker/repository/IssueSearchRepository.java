package au.com.domain.demo.tracker.repository;

import au.com.domain.demo.tracker.entity.Issue;
import au.com.domain.demo.tracker.model.QueryCriteria;

import java.util.List;

public interface IssueSearchRepository {

    List<Issue> findAllByCriteria(QueryCriteria queryCriteria);

}
