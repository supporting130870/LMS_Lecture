package model;

import view.userInterface.VLectureTable;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.*;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Vector;

import static java.lang.Integer.parseInt;

public class DAOUser {
    private static final String FILE_PATH = Paths.get(System.getProperty("user.dir"), "data", "users.txt").toString();

    public DAOUser() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveUser(MUser user) throws IOException {
        Vector<MUser> users = loadUsers();
        users.add(user);
        saveAllUsers(users);
    }

    public Vector<MUser> loadUsers() throws IOException {
        Vector<MUser> users = new Vector<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 9) { // 역할 필드 포함
                    MUser user = new MUser(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], parts[7], parts[8]);
                    users.add(user);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading users: " + e.getMessage());
            throw e;
        }
        return users;
    }

    public MUser findUserByIdAndPassword(String id, String password) throws IOException {
        Vector<MUser> users = loadUsers();
        for (MUser user : users) {
            if (user.getId().equals(id) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public boolean isUserIdExist(String id) throws IOException {
        Vector<MUser> users = loadUsers();
        for (MUser user : users) {
            if (user.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public String findIdByNameBirthDateAndStudentId(String name, String birthDate, String studentId) throws IOException {
        Vector<MUser> users = loadUsers();
        for (MUser user : users) {
            if (user.getName().equals(name) && user.getBirthDate().equals(birthDate) && user.getStudentId().equals(studentId)) {
                return user.getId();
            }
        }
        return null;
    }

    public void saveDataTable(String userID, VLectureTable vMiridamgi, VLectureTable vSincheong) throws IOException {
        JTable temp1 = vMiridamgi.getTable();
        JTable temp2 = vSincheong.getTable();
        saveTableData(userID, "miridamgi", temp1);
        saveTableData(userID, "sincheong", temp2);
    }

    private void saveTableData(String userID, String tableName, JTable table) throws IOException {
        String filePath = Paths.get(System.getProperty("user.dir"), "data", tableName + "_" + userID + ".txt").toString();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            TableModel model = table.getModel();
            for (int row = 0; row < model.getRowCount(); row++) {
                for (int col = 0; col < model.getColumnCount(); col++) {
                    writer.write(model.getValueAt(row, col).toString());
                    if (col < model.getColumnCount() - 1) {
                        writer.write(" ");
                    }
                }
                writer.newLine();
            }
        }
    }

    public void loadDataTable(String userID, VLectureTable vMiridamgi, VLectureTable vSincheong) throws IOException {
        Vector<MLecture> miridamgiLectures = loadTableData(userID, "miridamgi");
        Vector<MLecture> sincheongLectures = loadTableData(userID, "sincheong");

        vMiridamgi.loadData(miridamgiLectures);
        vSincheong.loadData(sincheongLectures);
    }

    private Vector<MLecture> loadTableData(String userID, String tableName) throws IOException {
        String filePath = Paths.get(System.getProperty("user.dir"), "data", tableName + "_" + userID + ".txt").toString();
        File file = new File(filePath);
        Vector<MLecture> lectureList = new Vector<>();

        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] values = line.split(" ");
                    if (values.length == 5) { // 열의 수가 5인 경우에만 처리
                        MLecture lecture = new MLecture();
                        lecture.setId(parseInt(values[0])); // ID
                        lecture.setName(values[1]); // Name
                        lecture.setLecturer(values[2]); // Lecturer
                        lecture.setCredit(values[3]); // Credit
                        lecture.setTime(values[4]);  // Time

                        lectureList.add(lecture);
                    } else {
                        System.err.println("Data format error: " + Arrays.toString(values));
                    }
                }
            }
        } else {
            System.err.println("File not found: " + filePath);
        }
        return lectureList;
    }

    // 비밀번호 변경 메서드
    public void changePassword(MUser user, String oldPassword, String newPassword) throws IOException {
        Vector<MUser> users = loadUsers();
        for (MUser u : users) {
            if (u.getId().equals(user.getId()) && u.getPassword().equals(oldPassword)) {
                u.setPassword(newPassword);
                saveAllUsers(users);
                return;
            }
        }
        throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
    }

    // 개인 정보 수정 메서드
    public MUser updateInfo(MUser user, String name, String campus, String birthDate, String studentId, String college, String department) throws IOException {
        Vector<MUser> users = loadUsers();
        for (MUser u : users) {
            if (u.getId().equals(user.getId())) {
                u.setName(name);
                u.setCampus(campus);
                u.setBirthDate(birthDate);
                u.setStudentId(studentId);
                u.setCollege(college);
                u.setDepartment(department);
                saveAllUsers(users);
                return u;
            }
        }
        return null;
    }

    public void saveAllUsers(Vector<MUser> users) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (MUser user : users) {
                writer.write(user.getName() + "," + user.getId() + "," + user.getPassword() + "," +
                        user.getCampus() + "," + user.getBirthDate() + "," + user.getStudentId() + "," +
                        user.getCollege() + "," + user.getDepartment() + "," + user.getRole());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving all users: " + e.getMessage());
            throw e;
        }
    }
}
