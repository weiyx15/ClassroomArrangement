package dataSet3;

public class GenerateSlack {
	public static void generate(int n1, int n2)
	{	// n1:��һ�ڿ�����
		// n2:�ڶ��ڿ�����
		CourseTableSlack ct = new CourseTableSlack(n1,n2);
		int sMax = ct.generateTable();					// max number of students
		ct.setStudentNumber(sMax);
		ct.students();
//		ct.printCourseList();
		ct.fprintCourseList();
//		ct.printConflictMap();
		ct.fprintConflictMap();
	}
}