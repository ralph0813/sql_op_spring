package me.stephenj.sqlope.Exception;

public class ForeignKeyExistException extends Exception{
    private String tbName;
    private String dtName;

    public ForeignKeyExistException() {
    }

    public ForeignKeyExistException(String tbName, String dtName) {
        this.tbName = tbName;
        this.dtName = dtName;
    }

    public String getTbName() {
        return tbName;
    }

    public void setTbName(String tbName) {
        this.tbName = tbName;
    }

    public String getDtName() {
        return dtName;
    }

    public void setDtName(String dtName) {
        this.dtName = dtName;
    }
}
