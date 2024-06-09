package view.managerInterface;

import constants.Constant;
import model.DAOUser;
import model.MIndex;
import model.MUser;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

public class VUserList extends JPanel {
    private final static long serialVersionUID = Constant.VUserList.VERSIONID;
    private LayoutManager layoutManager;
    private JTable userTable;
    private DefaultTableModel model;
    private DAOUser daoUser;
    public VUserList() throws IOException {
        this.layoutManager = new BoxLayout(this, BoxLayout.X_AXIS);
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

        this.model = new DefaultTableModel(null, header);
        this.userTable.setModel(this.model);

        Vector<MUser> users = daoUser.loadUsers();
        for (MUser mUser : users)
        {

            String[] row = new String[9];
            row[0] = mUser.getName();
            row[1] = mUser.getId();
            row[2] = mUser.getPassword();
            row[3] = mUser.getCampus();
            row[4] = mUser.getBirthDate();
            row[5] = mUser.getName();
            row[6] = mUser.getCollege();
            row[7] = mUser.getDepartment();
            row[8] = mUser.getRole();
            if(row[8].equals("student"))
            {
                this.model.addRow(row);
            }

        }

        // Add ListSelectionListener for row selection
        this.userTable.getSelectionModel().addListSelectionListener(new RowSelectionHandler());
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
