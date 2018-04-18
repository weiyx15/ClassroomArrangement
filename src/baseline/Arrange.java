/*
 * 从文件"CourseList"和"ConflictMap"中读取课表信息
 * 创建教学楼群信息，定义cost矩阵
 * naive排课方案作为baseline，计算baseline结果
 * 
 * ------------------------------
 * 	classroom capacity | number
 * 	>=200					6
 *  >=150					52
 * 	>=60					164
 * 	>=30					284
 * -------------------------------
 * 
 */

// 20180324 Result: 72351

package baseline;

import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Arrange {

	public static final int[] ROOM_CAPACITY = {30,60,150,200};				// 教室的四种课容量
	protected Vector<CourseRead> firstSet = new Vector<CourseRead>();	// 上午第一节课课表
	protected Vector<CourseRead> secondSet = new Vector<CourseRead>();	// 上午第二节课课表
	protected List<Building> buildings = new ArrayList<Building>();		// 教学楼表
	protected Vector<Conflict> conflictVec = new Vector<Conflict>();	// 课程冲突人数表
	protected Map<HashSet<Building>,Integer> COST = 
		new HashMap<HashSet<Building>,Integer>();							// 教学楼间成本矩阵
	
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		int arrangeCost = 0;					// 这种排课方式下的cost函数
//		setBuildings();
//		readCourseList();
//		readConflictVec();
//		printConflictVec();
//		naiveArrange();
//		printFirstSet();
//		printSecondSet();
//		arrangeCost = computeCost();
//		printFirstSet();
//		printSecondSet();
//		System.out.println("Cost function: "+arrangeCost);
//	}
	public void clearBuildings()	// 清空buildings列表和COST矩阵以供下次重新生成跑新的算法
	{
		buildings.clear();
		COST.clear();
	}
	public void clearAll()		
	// 清空buildings,COST,firstSet,secondSet,ConflictVec以供下一次实验重新生成
	{
		clearBuildings();
		firstSet.clear();
		secondSet.clear();
		conflictVec.clear();
	}
	public void readConflictVec()			// 从文件"ConflictMap"中读入冲突人数表
	{
		String tmpS = null;											// 读入的一行字符串
		String[] str = null;										// 空格分开的3段
		int id1 = -1, id2 = -1, common = 0;							// 字符串转成int
		int line = 0;												// 行号
		try {
            BufferedReader bf = new BufferedReader(new FileReader("ConflictMap"));
            while((tmpS = bf.readLine())!=null)
            {
            	if(line > 0)										// 如果不是首行（汉字）
            	{
            		str = tmpS.split(" ");							// 数字由空格分开
            		try {											// 字符串转int
            		    id1 = Integer.parseInt(str[0]);				
            		} catch (NumberFormatException e) {
            		    e.printStackTrace();						// 异常处理
            		}
            		try {
            		    id2 = Integer.parseInt(str[1]);
            		} catch (NumberFormatException e) {
            		    e.printStackTrace();
            		}
            		try {
            		    common = Integer.parseInt(str[2]);
            		} catch (NumberFormatException e) {
            		    e.printStackTrace();
            		}
            		Conflict cl = new Conflict();
            		cl.setConflict(id1, id2, common);
            		conflictVec.add(cl);
            	}
            	line++;
            }
            bf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}
	public void printConflictVec()
	{
		System.out.println("Conflict Vector");
		for (Conflict cl : conflictVec)
		{
			System.out.println(cl.getCourse1()+" "+cl.getCourse2()+" "+cl.getCommon());
		}
	}
	public void readCourseList()				// 从文件"CourseList"中读入课表
	{
		String tmpS = null;											// 读入的一行字符串
		String[] str = null;										// 空格分开的3段
		int cID = -1, cCapa = 0, cTime = -1;						// 字符串转成int
		int line = 0;												// 行号
		try {
            BufferedReader bf = new BufferedReader(new FileReader("CourseList"));
            while((tmpS = bf.readLine())!=null)
            {
            	if(line > 0)										// 如果不是首行（汉字）
            	{
            		str = tmpS.split("\t\t");						// 数字由2个制表符分开
            		try {											// 字符串转int
            		    cID = Integer.parseInt(str[0]);				
            		} catch (NumberFormatException e) {
            		    e.printStackTrace();						// 异常处理
            		}
            		try {
            		    cCapa = Integer.parseInt(str[1]);
            		} catch (NumberFormatException e) {
            		    e.printStackTrace();
            		}
            		try {
            		    cTime = Integer.parseInt(str[2]);
            		} catch (NumberFormatException e) {
            		    e.printStackTrace();
            		}
            		CourseRead cr = new CourseRead(cID);
            		cr.setTime(cTime);
            		cr.setCapacity(cCapa);
            		if(cTime==0)
            		{
            			firstSet.add(cr);
            		}
            		else
            		{
            			secondSet.add(cr);
            		}
            	}
            	line++;
            }
            bf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}
	public void printFirstSet()									// 打印上午第一节课列表
	{
		System.out.println("First course set");
		for (CourseRead cr : firstSet)
		{
			System.out.println(cr.ID+"\t\t"+cr.getCapacity());
		}
	}
	public void printSecondSet()									// 打印上午第二节课列表
	{
		System.out.println("Second course set");
		for (CourseRead cr : secondSet)
		{
			System.out.println(cr.ID+"\t\t"+cr.getCapacity());
		}
	}
	public void setBuildings()	// 创建教学楼和成本矩阵COST（千万不要修改该方法里的数据！）
	{
		Building B6 = new Building("B6",6);				// 六教
		B6.addRoom(ROOM_CAPACITY[0],120);
		B6.addRoom(ROOM_CAPACITY[2],16);
		B6.addRoom(ROOM_CAPACITY[3], 6);
		buildings.add(B6);
		
		Building B3 = new Building("B3",3);				// 三教
		B3.addRoom(ROOM_CAPACITY[1],80);
		B3.addRoom(ROOM_CAPACITY[2],12);
		buildings.add(B3);
		
		Building B4 = new Building("B4",4);				// 四教
		B4.addRoom(ROOM_CAPACITY[1],32);
		buildings.add(B4);
		
		Building B5 = new Building("B5",5);				// 五教
		B5.addRoom(ROOM_CAPACITY[2],12);
		buildings.add(B5);
		
		Building B1 = new Building("B1",1);				// 一教
		B1.addRoom(ROOM_CAPACITY[2],6);
		buildings.add(B1);
		
		// 成本矩阵
		HashSet<Building> B36 = new HashSet<Building>();
		B36.add(B3); B36.add(B6);
		COST.put(B36, 2);
		
		HashSet<Building> B35 = new HashSet<Building>();
		B35.add(B3); B35.add(B5);
		COST.put(B35, 12);
		
		HashSet<Building> B34 = new HashSet<Building>();
		B34.add(B3); B34.add(B4);
		COST.put(B34, 10);
		
		HashSet<Building> B45 = new HashSet<Building>();
		B45.add(B4); B45.add(B5);
		COST.put(B45, 1);
		
		HashSet<Building> B46 = new HashSet<Building>();
		B46.add(B4); B46.add(B6);
		COST.put(B46, 15);
		
		HashSet<Building> B56 = new HashSet<Building>();
		B56.add(B5); B56.add(B6);
		COST.put(B56, 17);
		
		HashSet<Building> B13 = new HashSet<Building>();
		B13.add(B1); B13.add(B3);
		COST.put(B13, 20);
		
		HashSet<Building> B14 = new HashSet<Building>();
		B14.add(B1); B14.add(B4);
		COST.put(B14, 15);
		
		HashSet<Building> B15 = new HashSet<Building>();
		B15.add(B1); B15.add(B5);
		COST.put(B15, 14);
		
		HashSet<Building> B16 = new HashSet<Building>();
		B16.add(B1); B16.add(B6);
		COST.put(B16, 22);
	}
	public void setBuildings2()	// 创建一个3教学楼，60教室的迷你系统用于暴力枚举法求解
	{
		Building sB0 = new Building("sB0",0);
		sB0.addRoom(ROOM_CAPACITY[3],3);
		buildings.add(sB0);
		
		Building sB1 = new Building("sB1",1);
		sB1.addRoom(ROOM_CAPACITY[3],3);
		buildings.add(sB1);
		
		Building sB2 = new Building("sB2",2);
		sB2.addRoom(ROOM_CAPACITY[3],2);
		buildings.add(sB2);
		
		HashSet<Building> sB01 = new HashSet<Building>();
		sB01.add(sB0); sB01.add(sB1);
		COST.put(sB01, 2);
		
		HashSet<Building> sB02 = new HashSet<Building>();
		sB02.add(sB0); sB02.add(sB2);
		COST.put(sB02, 3);
		
		HashSet<Building> sB12 = new HashSet<Building>();
		sB12.add(sB1); sB12.add(sB2);
		COST.put(sB12, 5);
	}
	public Integer computeCost()			// 对于一种排课方式，计算cost函数
	{
		Integer result = 0, id1=-1,id2=-1,theCost=0,theCom=0;
		HashSet<Building> buils = new HashSet<Building>();
		Comparator<CourseRead> idcr = new IDComparator();
		Collections.sort(firstSet,idcr);		// 将firstSet按ID排序，方便查询
		Collections.sort(secondSet,idcr);		// 将secondSet按ID排序，方便查询
		for (Conflict cl : conflictVec)			// 遍历conflictVec
		{
			id1 = cl.getCourse1();				// 上午第一节
			id2 = cl.getCourse2();				// 上午第二节
			id2 -= firstSet.size();				// 让id2从0开始
			CourseRead cr1 = firstSet.get(id1);	// 从firstSet中按下标查询
			CourseRead cr2 = secondSet.get(id2);// 从secondSet中按下标查询
			Building b1 = cr1.getBuilding();
			Building b2 = cr2.getBuilding();
			if(b1.name!=b2.name)
			{
				buils.clear();						// 每次循环将buils置空
				buils.add(b1);
				buils.add(b2);
				theCost = COST.get(buils);			// 用buils从COST中查询通勤成本
				theCom = cl.getCommon();
				result += theCost*theCom;
			}
		}
		return result;
	}
	public void naiveArrange()// baseline排课算法
	/*
	 * 对firstSet和secondSet单独考虑
	 * 先排大课，先尽着一个教学楼排
	 * 六教最大，先排满六教，再顺着离六教近的顺序排
	 * 教学楼的顺序： B6-B3-B4-B5-B1
	 */
	{
		Comparator<CourseRead> ct = new MyComparator();
		Collections.sort(firstSet, ct);				// firstSet降序排列
		Collections.sort(secondSet, ct);			// secondSet降序排列
		for (CourseRead cr : firstSet)				// 放上午第一节课
		{
			for (Building bd : buildings)
			{
				if(bd.useRoom(cr))					// 该教学楼可以放这个课程
				{
					break;
				}
			}
		}
		for (CourseRead cr : secondSet)				// 放上午第二节课
		{
			for (Building bd : buildings)
			{
				if(bd.useRoom(cr))					// 该教学楼可以放这个课程
				{
					break;
				}
			}
		}
	}
}

class MyComparator implements Comparator<CourseRead> {	// Vector<CourseRead>按课容量降序
	public int compare(CourseRead c1,CourseRead c2){
		CourseRead cr1 = c1;
		CourseRead cr2 = c2;
		return cr2.getCapacity().compareTo(cr1.getCapacity());	// 从大到小排序
	}
}

class IDComparator implements Comparator<CourseRead> {	// Vector<CourseRead>按ID升序
	public int compare(CourseRead c1,CourseRead c2){
		CourseRead cr1 = c1;
		CourseRead cr2 = c2;
		return cr1.ID.compareTo(cr2.ID);	// 从小到大排序
	}
}