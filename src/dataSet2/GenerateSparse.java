package dataSet2;

public class GenerateSparse {
	public static void main(String args[])
	{
		int n1 = 380-189;	// ��һ�ڿ���������ѡ��ϵͳ�п����ĵ������ݣ�2018�괺��������, ��ȥ����ѧԺ��
		int n2 = 460-189;	// �ڶ��ڿ���������ѡ��ϵͳ�п����ĵ������ݣ�2018�괺��������, ��ȥ����ѧԺ��
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
	
	// �ɹ�package::experiment�е�����õĺ����ӿ�
	public static void generate(int n1, int n2)
	{	// n1:��һ�ڿ�����
		// n2:�ڶ��ڿ�����
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