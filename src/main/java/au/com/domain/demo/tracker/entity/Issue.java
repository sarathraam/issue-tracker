package au.com.domain.demo.tracker.entity;

import au.com.domain.demo.actor.entity.User;
import au.com.domain.demo.converter.IssueStatusConverter;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "Issue")
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Convert(converter = IssueStatusConverter.class)
    private Status status;

    @OneToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "reporter")
    private User reporter;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "assignee")
    private User assignee;

    @Column(name = "created")
    private Date createdOn;

    @Column(name = "completed")
    private Date completedOn;


    public Issue() {}

    public Issue(String title, String description, User reporter) {
        this.title = title;
        this.description = description;
        this.reporter = reporter;

        // set defaults
        this.status = Status.CREATED;
        this.createdOn = new Date();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getReporter() {
        return reporter;
    }

    public void setReporter(User reporter) {
        this.reporter = reporter;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getCompletedOn() {
        return completedOn;
    }

    public void setCompletedOn(Date completedOn) {
        this.completedOn = completedOn;
    }
}
