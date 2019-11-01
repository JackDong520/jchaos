package Nmap.common;

public class XmlFile {
    private String FileName;
    private String FilePath;
    private String date;


    public XmlFile() {
    }

    public XmlFile(String fileName, String filePath, String date) {
        FileName = fileName;
        FilePath = filePath;
        this.date = date;
    }

    public XmlFile(String fileName, String filePath) {
        FileName = fileName;
        FilePath = filePath;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public String getFilePath() {
        return FilePath;
    }

    public void setFilePath(String filePath) {
        FilePath = filePath;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
