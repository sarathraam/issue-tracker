package au.com.domain.demo.tracker.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IssueModel {

    private String title;
    private String description;

    private String status;

    private String reporter;
    private String assignee;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date createdOn;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date completedOn;

    private final List<String> validationErrors  = new ArrayList<>();


    public IssueModel() {}

    public IssueModel(String title, String description, String status, String reporter, String assignee, Date createdOn, Date completedOn) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.reporter = reporter;
        this.assignee = assignee;
        this.createdOn = createdOn;
        this.completedOn = completedOn;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
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

    public List<String> getValidationErrors() {
        return validationErrors;
    }
}
