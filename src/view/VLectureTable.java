package view;

import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import control.CLecture;
import model.MLecture;

public class VLectureTable extends JScrollPane implements IIndex
{
	// attributes
	private static final long serialVersionUID = 1L;
	
	// components
	private JTable table;
	private DefaultTableModel model;
	private int selectedIndex;
	//associations
	private Vector<MLecture> mLectureList;
	
	public Vector<MLecture> getSelectedLecture() {
		// TODO Auto-generated method stub
		int[] selectedIndices= this.table.getSelectedRows();
		Vector<MLecture> vSelectedLectureList = new Vector<MLecture>();
		
		for(int index : selectedIndices)
		{
			vSelectedLectureList.add(this.mLectureList.get(index));
		}
		return vSelectedLectureList;
	}
	
	public void addSelectedLectureList(Vector<MLecture> selectedLectureList) {
		for(MLecture mLecture : selectedLectureList)
		{
			this.mLectureList.add(mLecture);
			String[] row = new String[5];
	        row[0] = String.valueOf(mLecture.getId());
	        row[1] = mLecture.getName();
	        row[2] = mLecture.getLecturer();
	        row[3] = mLecture.getCredit();
	        row[4] = mLecture.getTime();

	        this.model.addRow(row);
		}
		this.updateUI();
		
	}
	
	
	// methods
	public VLectureTable() 
	{
		//generate table as a component
		this.table = new JTable();
		this.setViewportView(this.table);
		
		//associate the table with a model
		String header[] = {	"아이디", "강좌명","강사","학점","시간"};
		this.model = new DefaultTableModel(null, header);
		this.table.setModel(this.model);		
	    // Add ListSelectionListener for row selection
	    this.table.getSelectionModel().addListSelectionListener(new RowSelectionHandler());
	    this.mLectureList = new Vector<MLecture>();
	}
	
	//working temporary variables
    public void show(String fileName) 
    {
        // get data from CCampus
        CLecture cLecture = new CLecture();
        this.mLectureList = cLecture.getList(fileName);
        this.model.setRowCount(0);
        
        // set data to Model
        for (MLecture mLecture : this.mLectureList) 
        {
            String[] row = new String[5];
            row[0] = String.valueOf(mLecture.getId());
            row[1] = mLecture.getName();
            row[2] = mLecture.getLecturer();
            row[3] = mLecture.getCredit();
            row[4] = mLecture.getTime();

            this.model.addRow(row);
        }
        
        this.updateUI();
        
           }
	//수정해야하는 곳

    private void showNext(int selectedIndex) 
    {
        
    }
    
	

    private class RowSelectionHandler implements ListSelectionListener 
    {
        @Override
        public void valueChanged(ListSelectionEvent e) 
        {
          if (!e.getValueIsAdjusting()) 
          {
            selectedIndex = table.getSelectedRow();
            if (selectedIndex >= 0) 
            {
              System.out.println(selectedIndex); // 혹은 선택된 행에 따라 원하는 작업 수행
              showNext(selectedIndex);
            }
          }
        }
    }


	public void initialize() {
		// TODO Auto-generated method stub
		
	}
	

	
}
    
