package pl.com.bottega.documentmanagement.api;

/**
 * Created by Wojciech Winiarski on 18.06.2016.
 */
public class SignupResultDto {
    private String failuer;
    private boolean success;


    public SignupResultDto(){
        success = true;

    }

    public SignupResultDto(String failure){
       success = false;
        this.failuer = failure;

    }

    public String getFailuer() {
        return failuer;
    }

    public void setFailuer(String failuer) {
        this.failuer = failuer;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "SignupResultDto{" +
                "failuer='" + failuer + '\'' +
                ", success=" + success +
                '}';
    }
}
