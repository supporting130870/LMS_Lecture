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
    private final String role;  // 역할 필드를 final로 설정

    public MUser(String name, String id, String password, String campus, String birthDate, String studentId, String college, String department, String role) {
        this.name = name;
        this.id = id;
        this.password = password;
        this.campus = campus;
        this.birthDate = birthDate;
        this.studentId = studentId;
        this.college = college;
        this.department = department;
        this.role = role; // 역할 필드 초기화
    }

    // Getter와 Setter
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getCampus() { return campus; }
    public void setCampus(String campus) { this.campus = campus; }
    public String getBirthDate() { return birthDate; }
    public void setBirthDate(String birthDate) { this.birthDate = birthDate; }
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public String getCollege() { return college; }
    public void setCollege(String college) { this.college = college; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getRole() { return role; }
}
