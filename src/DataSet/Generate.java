package DataSet;

public class Generate {
	public static void main(String args[])
	{
		int n1 = 380-189;	// 第一节课门数（从选课系统中看到的典型数据：2018年春周三上午, 除去美术学院）
		int n2 = 460-189;	// 第二节课门数（从选课系统中看到的典型数据：2018年春周三上午, 除去美术学院）
		long startTime = System.currentTimeMillis();	// program starting time
		CourseTable ct = new CourseTable(n1,n2);
		int sMax = ct.generateTable();					// max number of students
		ct.setStudentNumber(sMax);
		ct.students();
		ct.printCourseList();
		ct.fprintCourseList();
		ct.printConflictMap();
		ct.fprintConflictMap();
		long endTime = System.currentTimeMillis();	// program ending time
		System.out.println("Run time: "+(endTime-startTime)/1000+" s");
	}
}
