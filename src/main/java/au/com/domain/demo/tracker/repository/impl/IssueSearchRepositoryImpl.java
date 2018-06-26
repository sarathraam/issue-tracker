package au.com.domain.demo.tracker.repository.impl;

import au.com.domain.demo.actor.entity.User;
import au.com.domain.demo.tracker.entity.Issue;
import au.com.domain.demo.tracker.entity.Status;
import au.com.domain.demo.tracker.model.QueryCriteria;
import au.com.domain.demo.tracker.repository.IssueSearchRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class IssueSearchRepositoryImpl implements IssueSearchRepository {

    @PersistenceContext
    private EntityManager em;


    @Override
    public List<Issue> findAllByCriteria(QueryCriteria queryCriteria) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Issue> criteriaQuery = cb.createQuery(Issue.class);

        Root i = criteriaQuery.from(Issue.class);
        Join<Issue, User> iur = i.join("reporter", JoinType.LEFT);
        Join<Issue, User> iua = i.join("assignee", JoinType.LEFT);

        Predicate predicate = cb.conjunction();

        // TODO: Use static meta model for get?!
        if (!StringUtils.isEmpty(queryCriteria.getStatus())) {
            predicate = cb.and(predicate, cb.equal(
                    i.get("status"),
                    Status.valueOf(queryCriteria.getStatus())
            ));
        }

        if (!StringUtils.isEmpty(queryCriteria.getReporter())) {
            predicate = cb.and(predicate, cb.equal(
                    iur.get("name"),
                    queryCriteria.getReporter()
            ));
        }

        if (!StringUtils.isEmpty(queryCriteria.getAssignee())) {
            predicate = cb.and(predicate, cb.equal(
                    iua.get("name"),
                    queryCriteria.getAssignee()
            ));
        }

        if (!Objects.isNull(queryCriteria.getFromDate())) {
            predicate = cb.and(predicate, cb.greaterThanOrEqualTo(
                    i.<Date>get("createdOn"),
                    queryCriteria.getFromDate()
            ));
        }

        if (!Objects.isNull(queryCriteria.getToDate())) {
            predicate = cb.and(predicate, cb.lessThanOrEqualTo(
                    i.<Date>get("createdOn"),
                    queryCriteria.getToDate()
            ));
        }

        switch (queryCriteria.getCreatedSortBy()) {
            case ASC:
                criteriaQuery.orderBy(cb.asc(i.get("createdOn")));
                break;
            case DESC:
                criteriaQuery.orderBy(cb.desc(i.get("createdOn")));
                break;
            default:
                break;

        }


        criteriaQuery.where(predicate);

        TypedQuery<Issue> query = em.createQuery(criteriaQuery);

        query.setFirstResult(queryCriteria.getStartPage() * queryCriteria.getPageSize());
        query.setMaxResults(queryCriteria.getPageSize());


        return query.getResultList();

    }
}
