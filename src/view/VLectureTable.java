package view;

import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import constants.Constant;
import control.CLecture;
import model.MLecture;

public class VLectureTable extends JScrollPane implements IIndex {
	// attributes
	private static final long serialVersionUID = Constant.VLectureTable.VERSIONID;

	// components
	JTable table;
	private DefaultTableModel model;
	private int selectedIndex;
	// associations
	private Vector<MLecture> mLectureList;

	// methods
	public VLectureTable() {
		// generate table as a component
		this.table = new JTable();
		this.setViewportView(this.table);

		// associate the table with a model
		String[] header = {
				Constant.VLectureTable.EHeader.eID.getTitle(),
				Constant.VLectureTable.EHeader.eName.getTitle(),
				Constant.VLectureTable.EHeader.eLecturer.getTitle(),
				Constant.VLectureTable.EHeader.eCredit.getTitle(),
				Constant.VLectureTable.EHeader.eTime.getTitle()
		};
		this.model = new DefaultTableModel(null, header);
		this.table.setModel(this.model);

		// Add ListSelectionListener for row selection
		this.table.getSelectionModel().addListSelectionListener(new RowSelectionHandler());
		this.mLectureList = new Vector<MLecture>();

	}


	public Vector<MLecture> getSelectedLecture() {
		int[] selectedIndices = this.table.getSelectedRows();
		Vector<MLecture> vSelectedLectureList = new Vector<MLecture>();

		for (int index : selectedIndices) {
			vSelectedLectureList.add(this.mLectureList.get(index));
		}
		return vSelectedLectureList;
	}

	public void addSelectedLectureList(Vector<MLecture> selectedLectureList) {
		for (MLecture mLecture : selectedLectureList) {
			if (!this.mLectureList.contains(mLecture)) { // 중복 확인
				this.mLectureList.add(mLecture);
				String[] row = new String[5];
				row[0] = String.valueOf(mLecture.getId());
				row[1] = mLecture.getName();
				row[2] = mLecture.getLecturer();
				row[3] = mLecture.getCredit();
				row[4] = mLecture.getTime();

				this.model.addRow(row);
			}
		}
		this.updateUI();
		updateTotalCredits(); // 학점 합계 갱신
	}

	public void removeSelectedLectureList(Vector<MLecture> selectedLectureList) {
		for (MLecture mLecture : selectedLectureList) {
			if (this.mLectureList.contains(mLecture)) {
				this.mLectureList.remove(mLecture);
				// 테이블에서 해당 항목 삭제
				for (int i = 0; i < this.model.getRowCount(); i++) {
					if (this.model.getValueAt(i, 0).equals(String.valueOf(mLecture.getId()))) {
						this.model.removeRow(i);
						break;
					}
				}
			}
		}
		this.updateUI();
		updateTotalCredits(); // 학점 합계 갱신
	}

	// working temporary variables
	public void show(String fileName) {
		// get data from CCampus
		CLecture cLecture = new CLecture();
		this.mLectureList = cLecture.getList(fileName);
		this.model.setRowCount(0);

		// set data to Model
		for (MLecture mLecture : this.mLectureList) {
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

	// 학점 합계를 계산하고 라벨을 갱신하는 메서드
	public int updateTotalCredits() {
		int totalCredits = 0;
		for (MLecture lecture : mLectureList) {
			try {
				totalCredits += Integer.parseInt(lecture.getCredit());
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return totalCredits;
	}

	private void showNext(int selectedIndex) {

	}

	public void initialize() {
		// TODO Auto-generated method stub

	}


	private class RowSelectionHandler implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (!e.getValueIsAdjusting()) {
				selectedIndex = table.getSelectedRow();
				if (selectedIndex >= 0) {
					System.out.println(selectedIndex); // 혹은 선택된 행에 따라 원하는 작업 수행
					showNext(selectedIndex);
				}
			}
		}
	}
}