package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

public class DAOIndex {

	public DAOIndex() {
	}

	public Vector<MIndex> getList(String fileName) {
		Vector<MIndex> IndexList = new Vector<MIndex>();
		try {
			File file = new File("src/data/" + fileName + ".txt");
			Scanner scanner = new Scanner(file);
			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				MIndex mIndex = new MIndex();
				String[] wordList = line.split(" ");
				mIndex.setId(Integer.parseInt(wordList[0]));
				mIndex.setName(wordList[1]);
				mIndex.setFileName(wordList[2]);
				IndexList.add(mIndex);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return IndexList;
	}

	public Vector<MIndex> getColleges(String campusFileName) {
		return getList(campusFileName);
	}

	public Vector<MIndex> getDepartments(String collegeFileName) {
		return getList(collegeFileName);
	}
}
