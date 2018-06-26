package au.com.domain.demo.tracker.model;

import java.util.Date;

public class QueryCriteria {

    private String assignee;
    private String reporter;
    private String status;

    private Date fromDate;
    private Date toDate;

    private SortBy createdSortBy = SortBy.NONE;

    private int startPage = 0;
    private int pageSize = 10;


    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public SortBy getCreatedSortBy() {
        return createdSortBy;
    }

    public void setCreatedSortBy(SortBy createdSortBy) {
        this.createdSortBy = createdSortBy;
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
