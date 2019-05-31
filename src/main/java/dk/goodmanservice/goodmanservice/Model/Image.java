package dk.goodmanservice.goodmanservice.Model;

/**
 * Lavet af Joachim, Nick
 */

public class Image {

    private int fileId;
    private String fileUrl;

    public Image() {
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
