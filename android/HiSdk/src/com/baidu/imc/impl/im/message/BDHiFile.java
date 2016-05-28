package com.baidu.imc.impl.im.message;

public class BDHiFile {
    private String fileName;
    private long fileSize;
    private String ID;
    private String MD5;
    private String URL;
    private String fid;
    private String localeFilePath;
    private String fileType;
    private String fileID;
    private String oldfilePath;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getID() {
        return ID;
    }

    public void setID(String iD) {
        ID = iD;
    }

    public String getMD5() {
        return MD5;
    }

    public void setMD5(String mD5) {
        MD5 = mD5;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String uRL) {
        URL = uRL;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getLocaleFilePath() {
        return localeFilePath;
    }

    public void setLocaleFilePath(String localeFilePath) {
        this.localeFilePath = localeFilePath;
    }

    public void setoldLocaleFilePath(String localeFilePath) {
    	this.oldfilePath = localeFilePath;
    }

    
    public String getOldLocaleFilePath() {
        return oldfilePath;
    }
    
    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileID() {
        return fileID;
    }

    public void setFileID(String fileID) {
        this.fileID = fileID;
    }

    @Override
    public String toString() {
        return "BDHiFile [fileName=" + fileName + ", fileSize=" + fileSize + ", ID=" + ID + ", MD5=" + MD5 + ", URL="
                + URL + ", fid=" + fid + ", localeFilePath=" + localeFilePath + ", fileType=" + fileType + ", fileID="
                + fileID + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((MD5 == null) ? 0 : MD5.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        BDHiFile other = (BDHiFile) obj;
        if (MD5 == null) {
            if (other.MD5 != null) {
                return false;
            }
        } else if (!MD5.equals(other.MD5)) {
            return false;
        }
        return true;
    }

}
