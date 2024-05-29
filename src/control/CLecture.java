package control;

import java.util.Vector;

import model.DAOLecture;
import model.MLecture;

public class CLecture {

	private DAOLecture daoLecture;
	public Vector<MLecture> getList(String fileName) {
		this.daoLecture = new DAOLecture();
		Vector<MLecture> mLectureList = this.daoLecture.getList(fileName);
		return mLectureList;
	}

}
