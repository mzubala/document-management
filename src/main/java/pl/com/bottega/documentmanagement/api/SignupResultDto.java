package pl.com.bottega.documentmanagement.api;

/**
 * Created by anna on 18.06.2016.
 */
public class SignupResultDto {

    private boolean success;
    private String failureReason;

    public SignupResultDto() {
        success = true;
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

    public SignupResultDto(String failureReason) {
        this.failureReason = failureReason;
        success = false;
    }
}
