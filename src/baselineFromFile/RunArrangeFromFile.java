/*
 * ���������࣬�ṩ��̬������GUI����
 * 			 �ṩmain������������
 */

package baselineFromFile;

public class RunArrangeFromFile {
	public static final String[] first = 
	{"11", "13", "21", "23", "31", "33", "41", "43", "51", "53"};// ����/�����һ�ڿ�
	public static final String[] second = 
	{"12", "14", "22", "24", "32", "34", "42", "44", "52", "54"};// ����/����ڶ��ڿ�
	public static void main(String args[])
	{
		int i = 0;
		for (i=0; i<first.length; i++)
		{
			ArrangeFromFile aff = new ArrangeFromFile("data.xls",first[i],second[i]);
			aff.loadCourseExcel(first[i]);
			aff.loadCourseExcel(second[i]);
			aff.loadRoomExcel();
	//		aff.printFirstSet();
	//		aff.printSecondSet();
	//		aff.printBuildingList();
			aff.baselineMethod();
			aff.excelprintResult();
		}
	}
	public static void doArrange(String dataFileName)
	{
		int i = 0;
		for (i=0; i<first.length; i++)
		{
			ArrangeFromFile aff = new ArrangeFromFile(dataFileName,first[i],second[i]);
			aff.loadCourseExcel(first[i]);
			aff.loadCourseExcel(second[i]);
			aff.loadRoomExcel();
			aff.baselineMethod();
			aff.excelprintResult();
		}
	}
}
