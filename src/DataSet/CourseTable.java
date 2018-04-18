/* 生成课表
 * CourseTable类：
 * 给定总课程数，随机生成课程名称、指派课程时间（上午第一节还是上午第二节）、随机生成课程人数（30-200）
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
	protected Map<Integer,Course> firstCourse = new HashMap<Integer,Course>();	// 上午第一节课
	protected Vector<Integer> firstID = new Vector<Integer>();					// 上午第一节课的ID数组
	protected Map<Integer,Course> secondCourse = new HashMap<Integer,Course>();	// 上午第二节课
	protected Vector<Integer> secondID = new Vector<Integer>();					// 上午第二节课的ID数组
	protected Map<TreeSet<Integer>,Integer> conflictMap = 
		new HashMap<TreeSet<Integer>,Integer>();								// 共同选课学生
	// 用TreeMap保证course1的ID小于course2的ID，
	// 从而文件中第一项对应上午第一节，第二项对应上午第二节
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
	public int generateTable()// randomly create course and add to list，返回最多容许的选课学生
	{
		int i=0;
		int larger150 = 0;										// >150人的课
		int larger60 = 0;										// >60人的课
		int larger30 = 0;										// >30人的课
		int getRand = 0;	// 根据课容量的随机结果限制大容量的课防止教室放不下,因此要记录每次随机值
		int totalFirst = 0;			// 上午第一节的总人数
		int totalSecond = 0;		// 上午第二节的总人数
		for (i=0; i<num1; i++)
		{
			Course c = new Course(i);
			if(larger30==164)									// >30人的教室满了
			{
				getRand = 0;
			}
			else if(larger60==52)								// >60人的教室满了
			{
				getRand = rand.nextInt(2);
			}
			else if(larger150==6)								// >150人的教室满了
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
			firstCourse.put(i,c);							// 0: 上午第一节
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
			if(larger30==164)									// >30人的教室满了
			{
				getRand = 0;
			}
			else if(larger60==52)								// >60人的教室满了
			{
				getRand = rand.nextInt(2);
			}
			else if(larger150==6)								// >150人的教室满了
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
			secondCourse.put(i,c);							// 0: 上午第一节
			secondID.add(i);
			totalSecond += c.getCapacity();
			courseMap.put(i,c);	
		}
		return totalFirst<totalSecond? totalFirst:totalSecond;	// 返回两者之间的小者
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
            FileOutputStream fs = new FileOutputStream(new File("CourseList"), false);	// false: 写入覆盖
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
            FileOutputStream fs = new FileOutputStream(new File("ConflictMap"), false);	//false: 写入覆盖
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
	public void students()										// 学生们选课
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
			if(conflictMap.get(con)==null)						// 查询不到键值，就新建一个条目
			{
				conflictMap.put(con,1);
			}
			else												// 键值存在则value+1
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
//		courseID = String.format("%05d",key);	// 高位补0
//	}
//	public int courseKey;			// courseID in integer
//	private String courseID;		// course name
//	private int capacity;			// 课容量
//	private int time;				// 0: 上午第一节; 1：上午第二节
//	private int residual;			// 课余量 
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
//	public Boolean vetifyFull()						// 0: 未满可选; 1: 已满不可选
//	{
//		return (residual==0? true:false);
//	}
//	public void beChosen()							// 被一个学生选上
//	{
//		residual --;
//	}
//}

// the common students between two courses, 用courseKey代表course
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

// 一个学生，随机选随机门课，先到先得
//class Student {
//	public Student(Integer ID)
//	{
//		sID = ID;
//	}
//	
//	public Integer sID;											// 学生ID
//	private Integer course1,course2;							// 该学生选的2门课
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
//			Vector<Integer> firstID, Vector<Integer> secondID)	// 一个学生选2门课
//	{
//		Random rand = new Random();								// 随机数种子：随机生成课序号
//		int rand1,rand2,key1,key2;
//		rand1 = rand.nextInt(firstID.size());					// 随机上午第一节课的下标
//		rand2 = rand.nextInt(secondID.size());					// 随机上午第二节课的下标
//		key1 = firstID.get(rand1);								// 上午第一节课的键值
//		key2 = secondID.get(rand2);								// 上午第二节课的键值
//		Course c1 = firstCourse.get(key1);						// 从课表里找出这节上午第一节课
//		Course c2 = secondCourse.get(key2);						// 从课表里找出这节上午第二节课
//		while(c1.vetifyFull())									// 如果课满了选不上则一直循环随机
//		{
//			rand1 = rand.nextInt(firstID.size());					// 随机上午第一节课的下标
//			key1 = firstID.get(rand1);								// 上午第一节课的键值
//			c1 = firstCourse.get(key1);								// 从课表里找出这节上午第一节课
//		}
//		c1.beChosen();
//		course1 = key1;
//		while(c2.vetifyFull())										// 如果课满了选不上则一直循环随机
//		{
//			rand2 = rand.nextInt(secondID.size());					// 随机上午第一节课的下标
//			key2 = secondID.get(rand2);								// 上午第一节课的键值
//			c2 = secondCourse.get(key2);								// 从课表里找出这节上午第一节课
//		}
//		c2.beChosen();
//		course2 = key2;
//	}
//}