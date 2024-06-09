package view.managerInterface;

import model.DAOLecture;
import model.DAOUser;

import javax.swing.*;

public class appliedLecture extends JDialog
{
    private DAOUser daoUser;
    public appliedLecture()
    {
        this.daoUser = new DAOUser();
    }
}
