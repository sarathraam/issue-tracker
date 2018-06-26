package au.com.domain.demo.tracker.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class CommentModel {

    @NotBlank
    private String user;

    @NotNull
    private Long issueId;

    @NotBlank
    private String content;

    private final List<String> validationErrors = new ArrayList<String>();


    public CommentModel(String user, Long issueId, String content) {
        this.user = user;
        this.issueId = issueId;
        this.content = content;
    }


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getValidationErrors() {
        return validationErrors;
    }
}
