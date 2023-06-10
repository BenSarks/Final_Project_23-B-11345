package com.example.final_project_23b11345.Model;

public class SupportRequest {
    private String parcelTrackingNumber, cause, description;
    private boolean inProgress;
    private RequestStatus requestStatus;

    public SupportRequest(String parcelTrackingNumber, String cause, String description) {
        this.parcelTrackingNumber = parcelTrackingNumber;
        this.cause = cause;
        this.description = description;
    }

    public SupportRequest() {

    }

    public boolean isInProgress() {
        return inProgress;
    }

    public void setInProgress(boolean inProgress) {
        this.inProgress = inProgress;

    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getParcelTrackingNumber() {
        return parcelTrackingNumber;
    }

    public void setParcelTrackingNumber(String parcelTrackingNumber) {
        this.parcelTrackingNumber = parcelTrackingNumber;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public enum RequestStatus {
        SENT(0),
        REVIVED(1),
        WERE_CHEcKING(2),
        FINISHED(3);

        private final int value;

        RequestStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public int getIndicatorValue() {
            if (value > 2) {
                return 100;
            } else if (value == 2) {
                return 80;
            } else if (value == 1) {
                return 60;
            } else {
                return 40;
            }
        }

        public String getName() {
            return name().replace("_", " ");
        }
    }
}
