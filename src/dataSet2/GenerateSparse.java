package dataSet2;

public class GenerateSparse {
	public static void main(String args[])
	{
		int n1 = 380-189;	// 第一节课门数（从选课系统中看到的典型数据：2018年春周三上午, 除去美术学院）
		int n2 = 460-189;	// 第二节课门数（从选课系统中看到的典型数据：2018年春周三上午, 除去美术学院）
		long startTime = System.currentTimeMillis();	// program starting time
		CourseTableSparse ct = new CourseTableSparse(n1,n2);
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
	
	// 可供package::experiment中的类调用的函数接口
	public static void generate(int n1, int n2)
	{	// n1:第一节课门数
		// n2:第二节课门数
		CourseTableSparse ct = new CourseTableSparse(n1,n2);
		int sMax = ct.generateTable();					// max number of students
		ct.setStudentNumber(sMax);
		ct.students();
//		ct.printCourseList();
		ct.fprintCourseList();
//		ct.printConflictMap();
		ct.fprintConflictMap();
	}
}