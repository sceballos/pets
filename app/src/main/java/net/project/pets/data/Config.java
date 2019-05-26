package net.project.pets.data;

public class Config {

    private boolean isChatEnabled;
    private boolean isCallEnabled;
    private String workingHours;

    public Config(boolean isChatEnabled, boolean isCallEnabled, String workingHours) {
        this.isChatEnabled = isChatEnabled;
        this.isCallEnabled = isCallEnabled;
        this.workingHours = workingHours;
    }

    public boolean isChatEnabled() {
        return isChatEnabled;
    }

    public boolean isCallEnabled() {
        return isCallEnabled;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public void setChatEnabled(boolean chatEnabled) {
        isChatEnabled = chatEnabled;
    }

    public void setCallEnabled(boolean callEnabled) {
        isCallEnabled = callEnabled;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }
}
