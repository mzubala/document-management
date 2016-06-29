package pl.com.bottega.documentmanagement.api;

/**
 * Created by Dell on 2016-06-18.
 */
public class SignupResultDto {

    private boolean success;
    private String failureReason;

    public SignupResultDto() {
        success = true;
    }

    public SignupResultDto(String failureReason) {
        this.failureReason = failureReason;
        success = false;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }
}
