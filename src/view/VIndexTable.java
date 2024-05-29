package view;

import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import control.CIndex;
import model.MIndex;

public class VIndexTable extends JScrollPane implements IIndex
{
	// attributes
	private static final long serialVersionUID = 1L;
	
	// components
	private JTable table;
	private DefaultTableModel model;
	
	//associations
	private IIndex next;
	private Vector<MIndex> mIndexList;
	
	public void setNext(IIndex next)
	{
		this.next = next;
	}
	
	// methods
	public VIndexTable() 
	{
		//generate table as a component
		this.table = new JTable();
		this.setViewportView(this.table);
		
		//associate the table with a model
		String header[] = {	"아이디", "캠퍼스" };
		this.model = new DefaultTableModel(null, header);
		this.table.setModel(this.model);		
	    // Add ListSelectionListener for row selection
	    this.table.getSelectionModel().addListSelectionListener(new RowSelectionHandler());
	    
	}
	
	//working temporary variables
    public void show(String fileName) 
    {
        // get data from CCampus
        CIndex cIndex = new CIndex();
        this.mIndexList = cIndex.getList(fileName);
        this.model.setRowCount(0);
        
        // set data to Model
        for (MIndex mIndex : this.mIndexList) 
        {
            String[] row = new String[2];
            row[0] = String.valueOf(mIndex.getId());
            row[1] = mIndex.getName();
            this.model.addRow(row);
        }
        this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.table.setRowSelectionInterval(0, 0);
        this.updateUI();
        
        if (this.next != null) 
        {
            this.next.show(this.mIndexList.get(0).getFileName());
        }
    }
	//수정해야하는 곳

    private void showNext(int selectedIndex) 
    {
        if (this.next != null) 
        {
            String selectedFileName = this.mIndexList.get(selectedIndex).getFileName();
            this.next.show(selectedFileName);
        }
        
    }
	

    private class RowSelectionHandler implements ListSelectionListener 
    {
        @Override
        public void valueChanged(ListSelectionEvent e) 
        {
          if (!e.getValueIsAdjusting()) 
          {
            int selectedIndex = table.getSelectedRow();
            if (selectedIndex >= 0) 
            {
              System.out.println(selectedIndex); // 혹은 선택된 행에 따라 원하는 작업 수행
              showNext(selectedIndex);
            }
          }
        }
    }
}
    

