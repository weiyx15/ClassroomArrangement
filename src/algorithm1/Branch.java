/* 排课算法1：
 * 贪心法
 * 将ConflictMap中的边权值降序排列
 * 将教学楼按 B6-B3-B4-B5-B1排序
 * 初始化：最大权值的边的两个节点构成标记集M
 * 循环直到所有点都被标记：
 * 计算~M中与M里所有节点相关度最高（权值之和最大）/“评分”最大的节点放入M并安排教学楼
 * 特别注意的是，在大容量教室资源紧张时候要优先安排大容量的课程
 * 因此“评分”由两部分组成:
 * 1. 与M里某教学楼所有已排课程冲突人数之和
 * 2. 根据相应容量教室资源的紧张情况的一个附加给分: IN_SCARCE
 * M中包含多栋教学楼时新加入的节点安排在相关度最高的教学楼
 * 结束.
 */

// 20180325 result: 64702

package algorithm1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import baseline.Arrange;
import baseline.Conflict;
import baseline.ConflictComparator;
import baseline.CourseRead;
import baseline.Building;

public class Branch extends Arrange{
	
	public static final int IN_SCARCE[] = {600,400,200};		
	// 如果剩余符合条件的教室数量=剩余这种课容量的课程，则conns = IN_SCARCE表示优先放置
	// IN_SCARCE要大于最大课容量
	// 两两之间的差距也要在相应等级的最大课容量以上
	// [0],[1],[2]分别对应redundant[i][0],[1],[2]==courseLeft[i][0],[1],[2] (i=0,1)
	protected Map< Set<Integer>, Integer> conflictMap
	= new HashMap< Set<Integer>, Integer>();		// 支持由2门课的id查询冲突人数
	protected static int redundant[][] = {{0,0,0},{0,0,0}};
	// 剩余符合条件的教室数量
	// 上午第一节： [0][0] >150, [0][1] >60, [0][2] >30
	// 上午第二节： [1][0] >150, [1][1] >60, [1][2] >30
	protected static int courseLeft[][] = {{0,0,0},{0,0,0}};
	// 剩余还没有安排的课程数量
	// 上午第一节： [0][0] >150, [0][1] >60, [0][2] >30
	// 上午第二节： [1][0] >150, [1][1] >60, [1][2] >30
	
	// 重载clearAll方法，加入redundant数组和courseLeft数组的清空
	public void clearAll()
	{
		clearBuildings();
		firstSet.clear();
		secondSet.clear();
		conflictVec.clear();
		int i = 0, j = 0;
		for (i=0; i<redundant.length; i++)
		{
			for (j=0; j<redundant[i].length; j++)
			{
				redundant[i][j] = 0;// 每次初始化redundant归零
				courseLeft[i][j] = 0;// courseLeft也要归零
			}
		}
	}
	public void initCourseLeft()	// 用课表初始化courseLeft数组
	{
		int capa = 0 ,i = 0;
		Vector< Vector<CourseRead> > twoSets = new Vector< Vector<CourseRead> >();
		twoSets.add(firstSet);			// 用twoSets合并firstSet
		twoSets.add(secondSet);			// 和secondSet, 实现循环处理, 减少代码量
		for (i=0; i<2; i++)
		{
			for (CourseRead cr : twoSets.get(i))
			{
				capa = cr.getCapacity();
				if (capa > ROOM_CAPACITY[2])	// >150
				{
					courseLeft[i][0]++;
				}
				if (capa > ROOM_CAPACITY[1])	// >60
				{
					courseLeft[i][1]++;
				}
				if (capa > ROOM_CAPACITY[0])	// >30
				{
					courseLeft[i][2]++;
				}
			}
		}
	}
	/* 重载setBuildings函数，初始化redudant数组
	* 由多态性，为了使用Building2类替代Building类，只要在setBuildings()方法中
	* 将Building替换成Building2，其他地方还是用Building就行了
	*/
	public void setBuildings()	// 创建教学楼和成本矩阵COST（千万不要修改该方法里的数据！）
	{
		Building2 B6 = new Building2("B6",6);				// 六教
		B6.addRoom(ROOM_CAPACITY[0],120);
		B6.addRoom(ROOM_CAPACITY[2],16); redundant[0][1] += 16; redundant[0][2] +=16;
		B6.addRoom(ROOM_CAPACITY[3], 6); redundant[0][0] += 6; redundant[0][1] += 6; redundant[0][2] += 6;
		buildings.add(B6);
		
		Building2 B3 = new Building2("B3",3);				// 三教
		B3.addRoom(ROOM_CAPACITY[1],80); redundant[0][2] += 80;
		B3.addRoom(ROOM_CAPACITY[2],12); redundant[0][1] += 12; redundant[0][2] += 12;
		buildings.add(B3);
		
		Building2 B4 = new Building2("B4",4);				// 四教
		B4.addRoom(ROOM_CAPACITY[1],32); redundant[0][2] += 32;
		buildings.add(B4);
		
		Building2 B5 = new Building2("B5",5);				// 五教
		B5.addRoom(ROOM_CAPACITY[2],12); redundant[0][1] += 12; redundant[0][2] += 12;
		buildings.add(B5);
		
		Building2 B1 = new Building2("B1",1);				// 一教
		B1.addRoom(ROOM_CAPACITY[2],6); redundant[0][1] += 6; redundant[0][2] += 6;
		buildings.add(B1);
		
		// redundant二维数组
		for (int i=0; i<3; i++)
		{
			redundant[1][i] = redundant[0][i];	// 没放课前第二维和第一维是一样的
		}
		
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
	// 重载readConflictVec，加入conflictMap的初始化 
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
            		Set<Integer> twoCourse = new TreeSet<Integer>();
            		twoCourse.add(id1);
            		twoCourse.add(id2);
            		conflictMap.put(twoCourse,common);
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
	private int verifyCapacity(CourseRead cr)
	{
		int ind = -1;						// -1: 不用+IN_SCARCE; i(i>=0): +IN_SCARCE[i]
		int i = cr.getTime();				// 上午第一节课还是上午第二节课
		int j = -1;							// 课容量是什么等级
		int capa = cr.getCapacity();		// cr的课容量
		if (capa>Arrange.ROOM_CAPACITY[2])	// >150
		{
			j = 0;
		}
		else if(capa>Arrange.ROOM_CAPACITY[1])// > 60
		{
			j = 1;
		}
		else if(capa>Arrange.ROOM_CAPACITY[0])// >30
		{
			j = 2;
		}
		if (j!=-1 && redundant[i][j]<=courseLeft[i][j])	// 大于该课容量的教室资源已经用尽
		{
			ind = j;									// 返回j表示conns要加上IN_SCARCE[j]
		}
		return ind;
	}
	private Building arrangeThem(CourseRead cr, Building bd)
	// 将cr放入能放进的离bd最近的教学楼（包括bd本身）
	// 如果都不能放进去返回false, 否则返回true
	{
		if(cr==null)				// 输入空课程
		{
			return null;			// 说明前面已经出错了
		}
		Vector<BuildingLen> bdLen = new Vector<BuildingLen>();
		BuildingLen bl0 = new BuildingLen(bd, 0);
		bdLen.add(bl0);								// 首先加入bd
		// 按照距离bd远近升序排列的数组
		Integer len = 0;							// 两教学楼间距离
		for (Building myBuilding : buildings)
		{
			if (myBuilding.name != bd.name)
			{
				HashSet<Building> twoBuilding = new HashSet<Building>();
				twoBuilding.add(myBuilding);
				twoBuilding.add(bd);				// 构造HashSet用于查询cost
				len = COST.get(twoBuilding);		// 用两个教学楼查询cost
				BuildingLen bl = new BuildingLen(myBuilding, len);
				bdLen.add(bl);						// 向bdLen中加入一个节点
			}
		}
		Comparator<BuildingLen> blcr = new LenComparator();
		Collections.sort(bdLen, blcr);				// 按到bd的距离升序排列
		for (BuildingLen bdl : bdLen)
		{
			if (bdl.build.useRoom(cr))				// 如果可以放下
			{
//				System.out.println(bdl.build.name+": "+cr.getName());
				return bdl.build;					// 有地方放了返回教学楼
			}
			else
			{
//				System.out.println(cr.getName()+" cannot fit in "+bdl.build.name);
			}
		}
		return null;								// 都放不下则返回null
	}
	public void arrange1()
	/* 排课算法1：
	 * 贪心法
	 * 将ConflictMap中的边权值降序排列
	 * 将教学楼按 B6-B3-B4-B5-B1排序
	 * 初始化：最大权值的边的两个节点构成标记集M
	 * 循环直到所有点都被标记：
	 * 计算~M中与M里所有节点相关度最高（权值之和最大）的节点放入M并安排教学楼
	 * M中包含多栋教学楼时新加入的节点安排在相关度最高的教学楼
	 * 结束.
	 */
	{
		Comparator<CourseRead> idcr = new IDComparator();
		Collections.sort(firstSet,idcr);		// 将firstSet按ID排序，方便查询
		Collections.sort(secondSet,idcr);		// 将secondSet按ID排序，方便查询
		// 初始化
		Comparator<Conflict> concr = new ConflictComparator();
		Collections.sort(conflictVec, concr);
		List<CourseRead> M1 = new ArrayList<CourseRead>();	// 已安排的上午第一节课
		List<CourseRead> M2 = new ArrayList<CourseRead>();	// 已安排的上午第二节课
		Integer c1,c2;
		CourseRead cr1,cr2;
		Conflict con = conflictVec.firstElement();			// 重复人数最多的两门课
		c1 = con.getCourse1();								// 找到第一节课课号
		c2 = con.getCourse2();								// 找到第二节课课号
		c2 -= firstSet.size();								// 转化为第二节课的index
		cr1 = firstSet.get(c1);								// 从firstSet中按index查出第一节课
		cr2 = secondSet.get(c2);							// 从secondSet中按index查第二节课
		for (Building buil : buildings)//两门课分别选择起始教学楼,防止不存在一栋楼可以同时放下两门课					
		{
			if (buil.useRoom(cr1))
			{
//				System.out.println("Start from "+cr1.getName()+" : "+buil.name);
				break;
			}
		}
		for (Building buil : buildings)//两门课分别选择起始教学楼,防止不存在一栋楼可以同时放下两门课					
		{
			if (buil.useRoom(cr2))
			{
//				System.out.println("Start from "+cr2.getName()+" : "+buil.name);
				break;
			}
		}
		M1.add(cr1);
		M2.add(cr2);
		
		// 迭代
		Building b1,b2;									// M中的课程所属的教学楼
		Integer conns = 0;								// ~M中的一门课与M中的课共同学生总数
		Integer appInd = 0;								// 产生教室资源不足时的conns附加项下标
		Integer max_conn = 0;							// ~M中课程与一栋楼内课程的最大连接数
		Integer cnt_noConflict = 0;						// 计数~M中的课程与M中各教学楼中的课程
		// 均没有连接的数量，计数到10主循环退出
		CourseRead max_course = null;					// 最大连接所属的~M中的课程
		Set<Integer> hasAdded = new HashSet<Integer>();
		// 在M中出现且已经加入过新课程的教学楼，下次访问到的时候就不再添加新课程了
		// 注意：hasAdded中只能存课程ID，存课程对象或课程名字符串的话它们都是对象，不能实现唯一索引
		while(cnt_noConflict<2*buildings.size() && 
				(M1.size()<firstSet.size() || M2.size()<secondSet.size()))
			// M1,M2满则循环结束
		{					
			cnt_noConflict = 0;					// 每轮开始之前把noConflict计数器清零
			if (M2.size() < secondSet.size())	// firstSet, secondSet大小不等，需单独判断
			{
				max_conn = 0;
				max_course = null;
				hasAdded.clear();
				for (CourseRead it1 : M1)					// 放上午第二节课，循环M1找最大相关
				{
					b1 = it1.getBuilding();					// M1中的一门课所在的教学楼
					if(hasAdded.contains(b1.id))			// 如果b1已经添加过课程了
					{
						continue;							// 则不再添加课程
					}
					max_conn = 0;							// 最大连接数清零
					max_course = null;						// 最大连接课程置为空
					Map<CourseRead,Integer> myFirstIn = b1.getFirstIn();// 该教学楼现有的第一节课
					for (CourseRead cIt0 : secondSet)		// 遍历~M2集合
					{
						conns = 0;							// 当前连接数清零
						if (!M2.contains(cIt0))				// 如果cIt0不在已经安排好的集合中
						{
							appInd = verifyCapacity(cIt0);	// 该课容量是否教室紧张
							if (appInd!=-1)					// 确有教室紧张
							{
								conns += IN_SCARCE[appInd];	// 加上附加项优先安排
							}
							for (CourseRead cIt1 : myFirstIn.keySet())// 对b1内的第一节课cIt1
							{
								Set<Integer> twoCourse = new TreeSet<Integer>();
								twoCourse.add(cIt0.ID);
								twoCourse.add(cIt1.ID);
								if (conflictMap.get(twoCourse)!=null)// 如果冲突人数不为0
								{
									conns += conflictMap.get(twoCourse);	
									// conns加上查询两门课的冲突人数
								}
							}
						}
						if (conns>max_conn)	// 必须是>, =不行因为那样conns=0的课程可以蒙混过关
						{
							max_conn = conns;	// 维护max_conn
							max_course = cIt0;	// 维护max_course
						}
					}
					if (max_course==null)		// ~M2中不存在与b1课程有联系的课程
					{
						for (CourseRead cIt2 : secondSet)
						{
							if (!M2.contains(cIt2))
							{
								max_course = cIt2;	// 从~M2中随便挑一门课作为max_course
								break;
							}
						}
					}
					Building bAdded = arrangeThem(max_course, b1);	// 放这门课
					if(bAdded==null)								// 每栋楼都放不进
					{
						System.out.println
						(max_course.getName()+" course not fit in ERROR");
						return;										// 放不进则退出
					}
					else
					{
						M2.add(max_course);			// 将max_course加入M2
						hasAdded.add(bAdded.id);	// 将bAdded置为已经放过课程的教学楼
						break;						// 每次只放一个时间段的一门课
//						if (M2.size()==secondSet.size())
//						{
//							break;				// 若M2已满提前break，否则再循环会course not fit in ERROR
//						}
					}
				}
			}
			if (M1.size() < firstSet.size())// firstSet, secondSet大小不等，需单独判断
			{
				max_conn = 0;
				max_course = null;
				hasAdded.clear();
				for (CourseRead it2 : M2)					// 放上午第一节课，循环M2找最大相关
				{
					b2 = it2.getBuilding();					// M2中的一门课所在的教学楼
					if(hasAdded.contains(b2.id))			// 如果b2已经添加过课程了
					{
						continue;							// 则不再添加课程
					}
					max_conn = 0;							// 最大连接数清零
					max_course = null;						// 最大连接课程置为空
					Map<CourseRead,Integer> mySecondIn = b2.getSecondIn();
					// 该教学楼现有的第二节课
					for (CourseRead cIt0 : firstSet)		// 遍历~M1集合
					{
						conns = 0;							// 当前连接数清零
						if (!M1.contains(cIt0))				// 如果cIt0不在已经安排好的集合中
						{
							appInd = verifyCapacity(cIt0);	// 该课容量是否教室紧张
							if (appInd!=-1)					// 确有教室紧张
							{
								conns += IN_SCARCE[appInd];	// 加上附加项优先安排
							}
							for (CourseRead cIt1 : mySecondIn.keySet())// 对b1内的第一节课cIt1
							{
								Set<Integer> twoCourse = new TreeSet<Integer>();
								twoCourse.add(cIt0.ID);
								twoCourse.add(cIt1.ID);
								if(conflictMap.get(twoCourse)!=null)// 如果冲突人数不为0
								{
									conns += conflictMap.get(twoCourse);
									// conns加入两门课的冲突人数
								}
							}
						}
						if (conns>max_conn)	// 必须是>, =不行因为那样conns=0的课程可以蒙混过关
						{
							max_conn = conns;	// 维护max_conn
							max_course = cIt0;	// 维护max_course
						}
					}
					if (max_course==null)		// ~M1中不存在与b2课程有联系的课程
					{
						for (CourseRead cIt2 : firstSet)
						{
							if (!M1.contains(cIt2))
							{
								max_course = cIt2;	// 从~M1中随便挑一门课作为max_course
								break;
							}
						}
					}
					Building bAdded = arrangeThem(max_course, b2);	// 放这门课
					if(bAdded==null)								// 每栋楼都放不进
					{
						System.out.println
						(max_course.getName()+" course not fit in ERROR");
						return;										// 放不进则退出
					}
					else
					{
						M1.add(max_course);			// 将max_course加入M1
						hasAdded.add(bAdded.id);	// 将bAdded置为已经放过课程的教学楼
						break;						// 每次只放一个时间段的一门课
//						if (M1.size()==firstSet.size())
//						{
//							break;				// 若M1已满提前break，否则再循环会course not fit in ERROR
//						}
					}
				}
			}
		}
		// 如果二分图中有“孤岛”,即有一些课程与其他课程都没有联系
//		if (M1.size()<firstSet.size() || M2.size()<secondSet.size())
//		{
//			Vector< Vector<CourseRead> > twoSets = new Vector< Vector<CourseRead> >();
//			twoSets.add(firstSet);				// 把firstSet和secondSet统一到同一个数组里
//			twoSets.add(secondSet);
//			int i_twoSets = 0;					// 迭代变量
//			for (i_twoSets=0; i_twoSets<2; i_twoSets++)
//			{
//				for (CourseRead cr : twoSets.get(i_twoSets))// 分别对firstSet和secondSet中的课
//				{
//					if (M1.contains(cr) || M2.contains(cr))	// 如果cr已经被安排过了
//					{
//						continue;							// 就不安排了
//					}
//					for (Building buil : buildings)			// 循环找楼
//					{
//						if (buil.useRoom(cr))				// 找到可以安放的楼
//						{
//							if (i_twoSets==0)
//							{
//								M1.add(cr);
//							}
//							else
//							{
//								M2.add(cr);
//							}
//							break;							// 就退出循环找楼
//						}
//					}
//				}
//			}
//		}
	}
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		int arrangeCost = 0;					// 这种排课方式下的cost函数
//		setBuildings();
//		readCourseList();
//		readConflictVec();
//		initCourseLeft();
//		arrange1();
//		arrangeCost = computeCost();
//		System.out.println("Cost function: "+arrangeCost);
//	}

}

class BuildingLen {								// 教学楼到当前教学楼bd的距离
	public Building build;						// 测距的教学楼
	public Integer len2bd;						// 两个教学楼之间的距离
	
	public BuildingLen(Building b, Integer len)
	{
		build = b;
		len2bd = len;
	}
}

class LenComparator implements Comparator<BuildingLen> {
	public int compare(BuildingLen b1,BuildingLen b2){
		BuildingLen bd1 = b1;
		BuildingLen bd2 = b2;
		return bd1.len2bd.compareTo(bd2.len2bd);	// 从小到大排序
	}
}

class IDComparator implements Comparator<CourseRead> {	// Vector<CourseRead>按ID升序
	public int compare(CourseRead c1,CourseRead c2){
		CourseRead cr1 = c1;
		CourseRead cr2 = c2;
		return cr1.ID.compareTo(cr2.ID);	// 从小到大排序
	}
}