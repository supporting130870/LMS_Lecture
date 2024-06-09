package view.managerInterface;

import constants.Constant;
import model.DAOUser;
import model.MUser;
import view.loginInterface.VLoginDialog;
import view.userInterface.VMainFrame;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.Vector;

public class VUserList extends JPanel {
    private final static long serialVersionUID = Constant.VUserList.VERSIONID;
    private LayoutManager layoutManager;
    private JTable userTable;
    private DefaultTableModel model;
    private DAOUser daoUser;

    public VUserList(VManagerFrame vManagerFrame) throws IOException {
        this.layoutManager = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layoutManager);

        this.userTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(this.userTable);
        this.add(scrollPane);

        this.daoUser = new DAOUser();
        // Associate the table with a model
        String[] header = {
                Constant.VUserList.EHeader.eName.getTitle(),
                Constant.VUserList.EHeader.eID.getTitle(),
                Constant.VUserList.EHeader.ePW.getTitle(),
                Constant.VUserList.EHeader.eCampus.getTitle(),
                Constant.VUserList.EHeader.eBirthDate.getTitle(),
                Constant.VUserList.EHeader.eNumber.getTitle(),
                Constant.VUserList.EHeader.eCollege.getTitle(),
                Constant.VUserList.EHeader.eDepartment.getTitle(),
                Constant.VUserList.EHeader.eRole.getTitle()
        };

        this.model = new DefaultTableModel(null, header) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true; // make all cells editable
            }
        };
        this.userTable.setModel(this.model);

        Vector<MUser> users = daoUser.loadUsers();
        for (MUser mUser : users) {
            String[] row = new String[9];
            row[0] = mUser.getName();
            row[1] = mUser.getId();
            row[2] = mUser.getPassword();
            row[3] = mUser.getCampus();
            row[4] = mUser.getBirthDate();
            row[5] = mUser.getStudentId();
            row[6] = mUser.getCollege();
            row[7] = mUser.getDepartment();
            row[8] = mUser.getRole();

            this.model.addRow(row);

        }

        // Add ListSelectionListener for row selection
        this.userTable.getSelectionModel().addListSelectionListener(new RowSelectionHandler());

        // Add save button
        JButton saveButton = new JButton("변경사항저장");
        saveButton.addActionListener(e -> {
            try {
                saveChanges();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        this.add(saveButton);

    JButton logOutButton = new JButton("로그아웃");
        logOutButton.addActionListener(e -> {
            vManagerFrame.dispose();

            VMainFrame vMainFrame = new VMainFrame();
            vMainFrame.initialize();
            VLoginDialog vLoginDialog = new VLoginDialog(vMainFrame);
            vLoginDialog.setVisible(true);
        });
        this.add(logOutButton);
}

    private void saveChanges() throws IOException {
        int rowCount = model.getRowCount();
        Vector<MUser> updatedUsers = new Vector<>();

        for (int i = 0; i < rowCount; i++) {
            String name = (String) model.getValueAt(i, 0);
            String id = (String) model.getValueAt(i, 1);
            String password = (String) model.getValueAt(i, 2);
            String campus = (String) model.getValueAt(i, 3);
            String birthDate = (String) model.getValueAt(i, 4);
            String studentId = (String) model.getValueAt(i, 5);
            String college = (String) model.getValueAt(i, 6);
            String department = (String) model.getValueAt(i, 7);
            String role = (String) model.getValueAt(i, 8);

            MUser user = new MUser(name, id, password, campus, birthDate, studentId, college, department, role);
            updatedUsers.add(user);
        }

        daoUser.saveAllUsers(updatedUsers);
        JOptionPane.showMessageDialog(this, "Changes saved successfully!");
    }

    public class RowSelectionHandler implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                int selectedIndex = userTable.getSelectedRow();
                if (selectedIndex >= 0) {
                    System.out.println(selectedIndex); // 혹은 선택된 행에 따라 원하는 작업 수행
                }
            }
        }
    }
}
