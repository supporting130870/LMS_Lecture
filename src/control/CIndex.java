package control;

import java.util.Vector;

import model.DAOIndex;
import model.MIndex;

public class CIndex {

	private DAOIndex daoindex;
	public Vector<MIndex> getList(String fileName) {
		this.daoindex = new DAOIndex();
		Vector<MIndex> mindexList = this.daoindex.getList(fileName);
		return mindexList;
	}

}
