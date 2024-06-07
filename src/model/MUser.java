package model;

public class MUser {
    private String name;
    private String id;
    private String password;
    private String campus;
    private String birthDate;
    private String studentId;
    private String college;
    private String department;

    public MUser(String name, String id, String password, String campus, String birthDate, String studentId, String college, String department) {
        this.name = name;
        this.id = id;
        this.password = password;
        this.campus = campus;
        this.birthDate = birthDate;
        this.studentId = studentId;
        this.college = college;
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    // Getters and setters for all fields
}
