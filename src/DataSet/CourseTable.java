/* ���ɿα�
 * CourseTable�ࣺ
 * �����ܿγ�����������ɿγ����ơ�ָ�ɿγ�ʱ�䣨�����һ�ڻ�������ڶ��ڣ���������ɿγ�������30-200��
 */

package DataSet;
import java.util.*;
import java.io.*;
import java.util.Random;

public class CourseTable {
	public CourseTable(int n1, int n2)
	{
		num1 = n1;
		num2 = n2;
	}
	
	public static final int[] CAPACITY = {30,60,120,150,180,200};	// course capacity series
	protected int num1;			// # of first courses
	protected int num2;			// # of second courses
	protected int snum;			// # of students
	protected Map<Integer,Course> courseMap = new HashMap<Integer,Course>();// use MAP to store courses
	protected Map<Integer,Course> firstCourse = new HashMap<Integer,Course>();	// �����һ�ڿ�
	protected Vector<Integer> firstID = new Vector<Integer>();					// �����һ�ڿε�ID����
	protected Map<Integer,Course> secondCourse = new HashMap<Integer,Course>();	// ����ڶ��ڿ�
	protected Vector<Integer> secondID = new Vector<Integer>();					// ����ڶ��ڿε�ID����
	protected Map<TreeSet<Integer>,Integer> conflictMap = 
		new HashMap<TreeSet<Integer>,Integer>();								// ��ͬѡ��ѧ��
	// ��TreeMap��֤course1��IDС��course2��ID��
	// �Ӷ��ļ��е�һ���Ӧ�����һ�ڣ��ڶ����Ӧ����ڶ���
	private Random rand = new Random();								// rand: random seed

	public int getFirstNumber()								// query first course number
	{
		return num1;
	}
	public int getSecondNumber()							// query second course number
	{
		return num2;
	}
	public void setStudentNumber(int sN)
	{
		snum = sN;
	}
	public int getStudentNumber()								// query student number
	{
		return snum;
	}
	public int generateTable()// randomly create course and add to list��������������ѡ��ѧ��
	{
		int i=0;
		int larger150 = 0;										// >150�˵Ŀ�
		int larger60 = 0;										// >60�˵Ŀ�
		int larger30 = 0;										// >30�˵Ŀ�
		int getRand = 0;	// ���ݿ����������������ƴ������Ŀη�ֹ���ҷŲ���,���Ҫ��¼ÿ�����ֵ
		int totalFirst = 0;			// �����һ�ڵ�������
		int totalSecond = 0;		// ����ڶ��ڵ�������
		for (i=0; i<num1; i++)
		{
			Course c = new Course(i);
			if(larger30==164)									// >30�˵Ľ�������
			{
				getRand = 0;
			}
			else if(larger60==52)								// >60�˵Ľ�������
			{
				getRand = rand.nextInt(2);
			}
			else if(larger150==6)								// >150�˵Ľ�������
			{
				getRand = rand.nextInt(4);
			}
			else
			{
				getRand = rand.nextInt(6);
			}
			switch(getRand)
			{
			case 4:
			case 5:
				larger150++;
			case 2:
			case 3:
				larger60++;
			case 1:
				larger30++; break;
			default: break;
			}
			c.setCapacity(CAPACITY[getRand]);			// 6: length of const array CAPACITY
			c.setTime(0);
			firstCourse.put(i,c);							// 0: �����һ��
			firstID.add(i);
			totalFirst += c.getCapacity();
			courseMap.put(i,c);	
		}
		larger30 = 0;
		larger60 = 0;
		larger150 = 0;
		for (i=num1; i<num2+num1; i++)
		{
			Course c = new Course(i);
			if(larger30==164)									// >30�˵Ľ�������
			{
				getRand = 0;
			}
			else if(larger60==52)								// >60�˵Ľ�������
			{
				getRand = rand.nextInt(2);
			}
			else if(larger150==6)								// >150�˵Ľ�������
			{
				getRand = rand.nextInt(4);
			}
			else
			{
				getRand = rand.nextInt(6);
			}
			switch(getRand)
			{
			case 4:
			case 5:
				larger150++;
			case 2:
			case 3:
				larger60++;
			case 1:
				larger30++; break;
			default: break;
			}
			c.setCapacity(CAPACITY[getRand]);			// 6: length of const array CAPACITY
			c.setTime(1);
			secondCourse.put(i,c);							// 0: �����һ��
			secondID.add(i);
			totalSecond += c.getCapacity();
			courseMap.put(i,c);	
		}
		return totalFirst<totalSecond? totalFirst:totalSecond;	// ��������֮���С��
	}
	public void printCourseList()								// Print course list
	{
		for (Course aCourse:courseMap.values())
		{
			System.out.println(aCourse.getName()+" "+aCourse.getCapacity()+" "+aCourse.getTime());
		}
	}
	public void fprintCourseList()// output course list to FILE "CourseList"
	{
		try {
            FileOutputStream fs = new FileOutputStream(new File("CourseList"), false);	// false: д�븲��
            fs.write("course ID | course capacity | time period\r\n".getBytes());
            for (Course aCourse:courseMap.values())
            {
            	String aLine = 
            		aCourse.getName()+"\t\t"+aCourse.getCapacity()+"\t\t"+aCourse.getTime()+"\r\n";
            	fs.write(aLine.getBytes());
            }
            fs.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}
	public void printConflictMap()								// print conflict map
	{	
		for (Map.Entry<TreeSet<Integer>,Integer> entry : conflictMap.entrySet())
		{
			int wc1=-1, wc2=-1, i=0;
			TreeSet<Integer> con = entry.getKey();
			for (Integer it : con)
			{
				if(i==0)
				{
					wc1 = it;
				}
				else
				{
					wc2 = it;
				}
				i++;
			}
			System.out.println(wc1+" "+wc2+" "+entry.getValue());	
		}
	}
	public void fprintConflictMap()// output course list to FILE "ConflictMap"
	{
		try {
            FileOutputStream fs = new FileOutputStream(new File("ConflictMap"), false);	//false: д�븲��
            fs.write("course1 | course2 | common students\r\n".getBytes());
            for (Map.Entry<TreeSet<Integer>,Integer> entry : conflictMap.entrySet())
    		{
    			int wc1 = -1, wc2 = -1, i=0;
    			TreeSet<Integer> con = entry.getKey();
    			for (Integer it : con)
    			{
    				if(i==0)
    				{
    					wc1 = it;
    				}
    				else
    				{
    					wc2 = it;
    				}
    				i++;
    			}
    			String aLine = wc1+" "+wc2+" "+entry.getValue()+"\r\n";
    			fs.write(aLine.getBytes());
    		}
            fs.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}
	public Course queryCourse(Integer ID)						// enter courseID, return Course class
	{
		return courseMap.get(ID);
	}
	public void students()										// ѧ����ѡ��
	{
		int i=0;
		int c1,c2;
		int oldValue = 0;
		for (i=0; i<snum; i++)
		{
			Student s = new Student(i);
			s.registCourse(firstCourse, secondCourse, firstID, secondID);
			c1 = s.getCourse1();
			c2 = s.getCourse2();
			TreeSet<Integer> con = new TreeSet<Integer>();
			con.add(c1);
			con.add(c2);
			if(conflictMap.get(con)==null)						// ��ѯ������ֵ�����½�һ����Ŀ
			{
				conflictMap.put(con,1);
			}
			else												// ��ֵ������value+1
			{
				oldValue = conflictMap.get(con);
				conflictMap.put(con,oldValue+1);
			}
		}	
	}
}

// A course with course ID and capacity
//class Course {
//	public Course(int key)
//	{
//		courseKey = key;
//		courseID = String.format("%05d",key);	// ��λ��0
//	}
//	public int courseKey;			// courseID in integer
//	private String courseID;		// course name
//	private int capacity;			// ������
//	private int time;				// 0: �����һ��; 1������ڶ���
//	private int residual;			// ������ 
//	
//	public String getName()
//	{
//		return courseID;
//	}
//	public void setCapacity(int cap)
//	{
//		capacity = cap;
//		residual = cap;
//	}
//	public int getCapacity()
//	{
//		return capacity;
//	}
//	public void setTime(int t)
//	{
//		time = t;
//	}
//	public int getTime()
//	{
//		return time;
//	}
//	public Boolean vetifyFull()						// 0: δ����ѡ; 1: ��������ѡ
//	{
//		return (residual==0? true:false);
//	}
//	public void beChosen()							// ��һ��ѧ��ѡ��
//	{
//		residual --;
//	}
//}

// the common students between two courses, ��courseKey����course
//class Conflict {
//	public Conflict(int c1, int c2)
//	{
//		course1 = c1;
//		course2 = c2;
//	}
//	
//	private int course1,course2;
//	
//	public int getCourse1()
//	{
//		return course1;
//	}
//	public int getCourse2()
//	{
//		return course2;
//	}
//}

// һ��ѧ�������ѡ����ſΣ��ȵ��ȵ�
//class Student {
//	public Student(Integer ID)
//	{
//		sID = ID;
//	}
//	
//	public Integer sID;											// ѧ��ID
//	private Integer course1,course2;							// ��ѧ��ѡ��2�ſ�
// 
//	public int getCourse1()
//	{
//		return course1;
//	}
//	public int getCourse2()
//	{
//		return course2;
//	}
//	public void registCourse(Map<Integer,Course> firstCourse, Map<Integer,Course> secondCourse, 
//			Vector<Integer> firstID, Vector<Integer> secondID)	// һ��ѧ��ѡ2�ſ�
//	{
//		Random rand = new Random();								// ��������ӣ�������ɿ����
//		int rand1,rand2,key1,key2;
//		rand1 = rand.nextInt(firstID.size());					// ��������һ�ڿε��±�
//		rand2 = rand.nextInt(secondID.size());					// �������ڶ��ڿε��±�
//		key1 = firstID.get(rand1);								// �����һ�ڿεļ�ֵ
//		key2 = secondID.get(rand2);								// ����ڶ��ڿεļ�ֵ
//		Course c1 = firstCourse.get(key1);						// �ӿα����ҳ���������һ�ڿ�
//		Course c2 = secondCourse.get(key2);						// �ӿα����ҳ��������ڶ��ڿ�
//		while(c1.vetifyFull())									// ���������ѡ������һֱѭ�����
//		{
//			rand1 = rand.nextInt(firstID.size());					// ��������һ�ڿε��±�
//			key1 = firstID.get(rand1);								// �����һ�ڿεļ�ֵ
//			c1 = firstCourse.get(key1);								// �ӿα����ҳ���������һ�ڿ�
//		}
//		c1.beChosen();
//		course1 = key1;
//		while(c2.vetifyFull())										// ���������ѡ������һֱѭ�����
//		{
//			rand2 = rand.nextInt(secondID.size());					// ��������һ�ڿε��±�
//			key2 = secondID.get(rand2);								// �����һ�ڿεļ�ֵ
//			c2 = secondCourse.get(key2);								// �ӿα����ҳ���������һ�ڿ�
//		}
//		c2.beChosen();
//		course2 = key2;
//	}
//}