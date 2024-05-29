package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

public class DAOLecture {
	

	public DAOLecture() {
	}

	
	public Vector<MLecture> getList(String fileName) {
		Vector<MLecture> LectureList = new Vector<MLecture>();
		
		try {
			File file = new File("src/data/"+fileName+".txt");
			Scanner scanner = new Scanner(file);
			String line = null; //라인 읽기
			while (scanner.hasNext()) {
				line = scanner.nextLine();
				 
				MLecture mLecture = new MLecture();
				String[] wordList = line.split(" ");
				
				mLecture.setId(Integer.parseInt(wordList[0])); //파일에 아스키코드로 입력되어 있기 떄문에 인테저로 바꿔줌(parseInt)
				mLecture.setName(wordList[1]);
				mLecture.setLecturer(wordList[2]); //라인 1개가 세개(Id, Name, FileName)로 나눠짐
				mLecture.setCredit(wordList[3]);
				mLecture.setTime(wordList[4]);

				LectureList.add(mLecture);
			}
			scanner.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return LectureList;
	}

}