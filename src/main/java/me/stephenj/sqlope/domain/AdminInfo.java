package me.stephenj.sqlope.domain;

/**
 * @ClassName AdminInfo.java
 * @Description 
 * @author 张润天
 * @Time 2020/10/25 15:10
 * @Field : 
 */
public class AdminInfo {
    private Integer id;

    private String username;

    private String role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
