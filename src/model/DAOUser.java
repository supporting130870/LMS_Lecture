package model;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(user.getName() + "," + user.getId() + "," + user.getPassword() + "," +
                    user.getCampus() + "," + user.getBirthDate() + "," + user.getStudentId() + "," +
                    user.getCollege() + "," + user.getDepartment());
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error saving user: " + e.getMessage());
            throw e;
        }
    }

    public List<MUser> loadUsers() throws IOException {
        List<MUser> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 8) {
                    MUser user = new MUser(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], parts[7]);
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
        List<MUser> users = loadUsers();
        for (MUser user : users) {
            if (user.getId().equals(id) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public boolean isUserIdExist(String id) throws IOException {
        List<MUser> users = loadUsers();
        for (MUser user : users) {
            if (user.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public String findIdByNameBirthDateAndStudentId(String name, String birthDate, String studentId) throws IOException {
        List<MUser> users = loadUsers();
        for (MUser user : users) {
            if (user.getName().equals(name) && user.getBirthDate().equals(birthDate) && user.getStudentId().equals(studentId)) {
                return user.getId();
            }
        }
        return null;
    }

}
