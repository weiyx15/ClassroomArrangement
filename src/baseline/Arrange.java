/*
 * ���ļ�"CourseList"��"ConflictMap"�ж�ȡ�α���Ϣ
 * ������ѧ¥Ⱥ��Ϣ������cost����
 * naive�ſη�����Ϊbaseline������baseline���
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

	public static final int[] ROOM_CAPACITY = {30,60,150,200};				// ���ҵ����ֿ�����
	protected Vector<CourseRead> firstSet = new Vector<CourseRead>();	// �����һ�ڿοα�
	protected Vector<CourseRead> secondSet = new Vector<CourseRead>();	// ����ڶ��ڿοα�
	protected List<Building> buildings = new ArrayList<Building>();		// ��ѧ¥��
	protected Vector<Conflict> conflictVec = new Vector<Conflict>();	// �γ̳�ͻ������
	protected Map<HashSet<Building>,Integer> COST = 
		new HashMap<HashSet<Building>,Integer>();							// ��ѧ¥��ɱ�����
	
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		int arrangeCost = 0;					// �����ſη�ʽ�µ�cost����
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
	public void clearBuildings()	// ���buildings�б��COST�����Թ��´������������µ��㷨
	{
		buildings.clear();
		COST.clear();
	}
	public void clearAll()		
	// ���buildings,COST,firstSet,secondSet,ConflictVec�Թ���һ��ʵ����������
	{
		clearBuildings();
		firstSet.clear();
		secondSet.clear();
		conflictVec.clear();
	}
	public void readConflictVec()			// ���ļ�"ConflictMap"�ж����ͻ������
	{
		String tmpS = null;											// �����һ���ַ���
		String[] str = null;										// �ո�ֿ���3��
		int id1 = -1, id2 = -1, common = 0;							// �ַ���ת��int
		int line = 0;												// �к�
		try {
            BufferedReader bf = new BufferedReader(new FileReader("ConflictMap"));
            while((tmpS = bf.readLine())!=null)
            {
            	if(line > 0)										// ����������У����֣�
            	{
            		str = tmpS.split(" ");							// �����ɿո�ֿ�
            		try {											// �ַ���תint
            		    id1 = Integer.parseInt(str[0]);				
            		} catch (NumberFormatException e) {
            		    e.printStackTrace();						// �쳣����
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
	public void readCourseList()				// ���ļ�"CourseList"�ж���α�
	{
		String tmpS = null;											// �����һ���ַ���
		String[] str = null;										// �ո�ֿ���3��
		int cID = -1, cCapa = 0, cTime = -1;						// �ַ���ת��int
		int line = 0;												// �к�
		try {
            BufferedReader bf = new BufferedReader(new FileReader("CourseList"));
            while((tmpS = bf.readLine())!=null)
            {
            	if(line > 0)										// ����������У����֣�
            	{
            		str = tmpS.split("\t\t");						// ������2���Ʊ���ֿ�
            		try {											// �ַ���תint
            		    cID = Integer.parseInt(str[0]);				
            		} catch (NumberFormatException e) {
            		    e.printStackTrace();						// �쳣����
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
	public void printFirstSet()									// ��ӡ�����һ�ڿ��б�
	{
		System.out.println("First course set");
		for (CourseRead cr : firstSet)
		{
			System.out.println(cr.ID+"\t\t"+cr.getCapacity());
		}
	}
	public void printSecondSet()									// ��ӡ����ڶ��ڿ��б�
	{
		System.out.println("Second course set");
		for (CourseRead cr : secondSet)
		{
			System.out.println(cr.ID+"\t\t"+cr.getCapacity());
		}
	}
	public void setBuildings()	// ������ѧ¥�ͳɱ�����COST��ǧ��Ҫ�޸ĸ÷���������ݣ���
	{
		Building B6 = new Building("B6",6);				// ����
		B6.addRoom(ROOM_CAPACITY[0],120);
		B6.addRoom(ROOM_CAPACITY[2],16);
		B6.addRoom(ROOM_CAPACITY[3], 6);
		buildings.add(B6);
		
		Building B3 = new Building("B3",3);				// ����
		B3.addRoom(ROOM_CAPACITY[1],80);
		B3.addRoom(ROOM_CAPACITY[2],12);
		buildings.add(B3);
		
		Building B4 = new Building("B4",4);				// �Ľ�
		B4.addRoom(ROOM_CAPACITY[1],32);
		buildings.add(B4);
		
		Building B5 = new Building("B5",5);				// ���
		B5.addRoom(ROOM_CAPACITY[2],12);
		buildings.add(B5);
		
		Building B1 = new Building("B1",1);				// һ��
		B1.addRoom(ROOM_CAPACITY[2],6);
		buildings.add(B1);
		
		// �ɱ�����
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
	public void setBuildings2()	// ����һ��3��ѧ¥��60���ҵ�����ϵͳ���ڱ���ö�ٷ����
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
	public Integer computeCost()			// ����һ���ſη�ʽ������cost����
	{
		Integer result = 0, id1=-1,id2=-1,theCost=0,theCom=0;
		HashSet<Building> buils = new HashSet<Building>();
		Comparator<CourseRead> idcr = new IDComparator();
		Collections.sort(firstSet,idcr);		// ��firstSet��ID���򣬷����ѯ
		Collections.sort(secondSet,idcr);		// ��secondSet��ID���򣬷����ѯ
		for (Conflict cl : conflictVec)			// ����conflictVec
		{
			id1 = cl.getCourse1();				// �����һ��
			id2 = cl.getCourse2();				// ����ڶ���
			id2 -= firstSet.size();				// ��id2��0��ʼ
			CourseRead cr1 = firstSet.get(id1);	// ��firstSet�а��±��ѯ
			CourseRead cr2 = secondSet.get(id2);// ��secondSet�а��±��ѯ
			Building b1 = cr1.getBuilding();
			Building b2 = cr2.getBuilding();
			if(b1.name!=b2.name)
			{
				buils.clear();						// ÿ��ѭ����buils�ÿ�
				buils.add(b1);
				buils.add(b2);
				theCost = COST.get(buils);			// ��buils��COST�в�ѯͨ�ڳɱ�
				theCom = cl.getCommon();
				result += theCost*theCom;
			}
		}
		return result;
	}
	public void naiveArrange()// baseline�ſ��㷨
	/*
	 * ��firstSet��secondSet��������
	 * ���Ŵ�Σ��Ⱦ���һ����ѧ¥��
	 * ����������������̣���˳�������̽���˳����
	 * ��ѧ¥��˳�� B6-B3-B4-B5-B1
	 */
	{
		Comparator<CourseRead> ct = new MyComparator();
		Collections.sort(firstSet, ct);				// firstSet��������
		Collections.sort(secondSet, ct);			// secondSet��������
		for (CourseRead cr : firstSet)				// �������һ�ڿ�
		{
			for (Building bd : buildings)
			{
				if(bd.useRoom(cr))					// �ý�ѧ¥���Է�����γ�
				{
					break;
				}
			}
		}
		for (CourseRead cr : secondSet)				// ������ڶ��ڿ�
		{
			for (Building bd : buildings)
			{
				if(bd.useRoom(cr))					// �ý�ѧ¥���Է�����γ�
				{
					break;
				}
			}
		}
	}
}

class MyComparator implements Comparator<CourseRead> {	// Vector<CourseRead>������������
	public int compare(CourseRead c1,CourseRead c2){
		CourseRead cr1 = c1;
		CourseRead cr2 = c2;
		return cr2.getCapacity().compareTo(cr1.getCapacity());	// �Ӵ�С����
	}
}

class IDComparator implements Comparator<CourseRead> {	// Vector<CourseRead>��ID����
	public int compare(CourseRead c1,CourseRead c2){
		CourseRead cr1 = c1;
		CourseRead cr2 = c2;
		return cr1.ID.compareTo(cr2.ID);	// ��С��������
	}
}