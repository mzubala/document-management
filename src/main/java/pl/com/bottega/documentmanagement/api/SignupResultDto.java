package pl.com.bottega.documentmanagement.api;

/**
 * Created by bartosz.paszkowski on 18.06.2016.
 */
public class SignupResultDto {

    private String failureReason;
    private boolean success;


    public SignupResultDto(){
        success = true;
    }
    public SignupResultDto(String failureReason){
        this.failureReason = failureReason;
        this.success = false;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
