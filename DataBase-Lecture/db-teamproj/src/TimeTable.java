import java.sql.SQLException;

import javax.swing.JTable;

public class TimeTable extends JTable {
	static String header[] = {"교시","월요일","화요일","수요일","목요일","금요일"};
	static String constents[][]= {{"1교시","","","","",""},{"2교시","","","","",""},{"3교시","","","","",""},
			{"4교시","","","","",""},{"5교시","","","","",""},{"6교시","","","","",""},
			{"7교시","","","","",""},{"8교시","","","","",""},{"9교시","","","","",""}};
	public TimeTable(String type, String id) {
		super(constents, header);
		String sql = String.format(
				"select l.name,l.day, l.period, l.hour "
				+ "from lecture l where l.lectureId "
				+ "in (select ls.lectureid "
				+ "from lecture_has_student ls "
				+ "where ls.%sId = %s "
				+ "and ls.year = 2021 and ls.semester =1)" + 
    			"order by l.day desc, l.period ", type, id);

    	try {
    		String[] lectureInfos = MainFrame.getDB().executeQuery(sql);
    		for (int i=0; i<lectureInfos.length; i++) {
    			String[] lectureInfo = lectureInfos[i].split(" ");
    			// 4 test
//    			for (int j=0; j<lectureInfo.length; j++) {
//    				System.out.print(lectureInfo[j] + " ");
//    			}
//    			System.out.println();
    			// test end
    			String name = lectureInfo[1];
    			String day = lectureInfo[2];
    			int period = Integer.parseInt(lectureInfo[3]);
    			int hour = Integer.parseInt(lectureInfo[4]);
    			
    			if(day.equals("월요일")) {
    				while(hour>0) {
    					setValueAt(name, period++, 1);
             		   	hour--;
             	   	}
                }
                else if(day.equals("화요일")) {
             	    while(hour>0) {
             	    	setValueAt(name, period++, 2);
             	    	hour--;
                    }
                }
                else if(day.equals("수요일")) {
             	    while(hour>0) {
             	    	setValueAt(name, period++, 3);
             	    	hour--;
                    }
                }
                else if(day.equals("목요일")) {
             	    while(hour>0) {
             	    	setValueAt(name, period++, 4);
             		   	hour--;
                    }
                }
                else if(day.equals("금요일")) {
             	    while(hour>0) {
             	    	setValueAt(name, period++, 5);
             	    	hour--;
                    }
                }
    		}
    	}
    	catch (SQLException err) {
    		err.printStackTrace();
    	}
    	
	}
}
