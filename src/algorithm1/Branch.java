/* �ſ��㷨1��
 * ̰�ķ�
 * ��ConflictMap�еı�Ȩֵ��������
 * ����ѧ¥�� B6-B3-B4-B5-B1����
 * ��ʼ�������Ȩֵ�ıߵ������ڵ㹹�ɱ�Ǽ�M
 * ѭ��ֱ�����е㶼����ǣ�
 * ����~M����M�����нڵ���ض���ߣ�Ȩֵ֮�����/�����֡����Ľڵ����M�����Ž�ѧ¥
 * �ر�ע����ǣ��ڴ�����������Դ����ʱ��Ҫ���Ȱ��Ŵ������Ŀγ�
 * ��ˡ����֡������������:
 * 1. ��M��ĳ��ѧ¥�������ſγ̳�ͻ����֮��
 * 2. ������Ӧ����������Դ�Ľ��������һ�����Ӹ���: IN_SCARCE
 * M�а����ධ��ѧ¥ʱ�¼���Ľڵ㰲������ض���ߵĽ�ѧ¥
 * ����.
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
	// ���ʣ����������Ľ�������=ʣ�����ֿ������Ŀγ̣���conns = IN_SCARCE��ʾ���ȷ���
	// IN_SCARCEҪ������������
	// ����֮��Ĳ��ҲҪ����Ӧ�ȼ���������������
	// [0],[1],[2]�ֱ��Ӧredundant[i][0],[1],[2]==courseLeft[i][0],[1],[2] (i=0,1)
	protected Map< Set<Integer>, Integer> conflictMap
	= new HashMap< Set<Integer>, Integer>();		// ֧����2�ſε�id��ѯ��ͻ����
	protected static int redundant[][] = {{0,0,0},{0,0,0}};
	// ʣ����������Ľ�������
	// �����һ�ڣ� [0][0] >150, [0][1] >60, [0][2] >30
	// ����ڶ��ڣ� [1][0] >150, [1][1] >60, [1][2] >30
	protected static int courseLeft[][] = {{0,0,0},{0,0,0}};
	// ʣ�໹û�а��ŵĿγ�����
	// �����һ�ڣ� [0][0] >150, [0][1] >60, [0][2] >30
	// ����ڶ��ڣ� [1][0] >150, [1][1] >60, [1][2] >30
	
	// ����clearAll����������redundant�����courseLeft��������
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
				redundant[i][j] = 0;// ÿ�γ�ʼ��redundant����
				courseLeft[i][j] = 0;// courseLeftҲҪ����
			}
		}
	}
	public void initCourseLeft()	// �ÿα��ʼ��courseLeft����
	{
		int capa = 0 ,i = 0;
		Vector< Vector<CourseRead> > twoSets = new Vector< Vector<CourseRead> >();
		twoSets.add(firstSet);			// ��twoSets�ϲ�firstSet
		twoSets.add(secondSet);			// ��secondSet, ʵ��ѭ������, ���ٴ�����
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
	/* ����setBuildings��������ʼ��redudant����
	* �ɶ�̬�ԣ�Ϊ��ʹ��Building2�����Building�ֻ࣬Ҫ��setBuildings()������
	* ��Building�滻��Building2�������ط�������Building������
	*/
	public void setBuildings()	// ������ѧ¥�ͳɱ�����COST��ǧ��Ҫ�޸ĸ÷���������ݣ���
	{
		Building2 B6 = new Building2("B6",6);				// ����
		B6.addRoom(ROOM_CAPACITY[0],120);
		B6.addRoom(ROOM_CAPACITY[2],16); redundant[0][1] += 16; redundant[0][2] +=16;
		B6.addRoom(ROOM_CAPACITY[3], 6); redundant[0][0] += 6; redundant[0][1] += 6; redundant[0][2] += 6;
		buildings.add(B6);
		
		Building2 B3 = new Building2("B3",3);				// ����
		B3.addRoom(ROOM_CAPACITY[1],80); redundant[0][2] += 80;
		B3.addRoom(ROOM_CAPACITY[2],12); redundant[0][1] += 12; redundant[0][2] += 12;
		buildings.add(B3);
		
		Building2 B4 = new Building2("B4",4);				// �Ľ�
		B4.addRoom(ROOM_CAPACITY[1],32); redundant[0][2] += 32;
		buildings.add(B4);
		
		Building2 B5 = new Building2("B5",5);				// ���
		B5.addRoom(ROOM_CAPACITY[2],12); redundant[0][1] += 12; redundant[0][2] += 12;
		buildings.add(B5);
		
		Building2 B1 = new Building2("B1",1);				// һ��
		B1.addRoom(ROOM_CAPACITY[2],6); redundant[0][1] += 6; redundant[0][2] += 6;
		buildings.add(B1);
		
		// redundant��ά����
		for (int i=0; i<3; i++)
		{
			redundant[1][i] = redundant[0][i];	// û�ſ�ǰ�ڶ�ά�͵�һά��һ����
		}
		
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
	// ����readConflictVec������conflictMap�ĳ�ʼ�� 
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
		int ind = -1;						// -1: ����+IN_SCARCE; i(i>=0): +IN_SCARCE[i]
		int i = cr.getTime();				// �����һ�ڿλ�������ڶ��ڿ�
		int j = -1;							// ��������ʲô�ȼ�
		int capa = cr.getCapacity();		// cr�Ŀ�����
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
		if (j!=-1 && redundant[i][j]<=courseLeft[i][j])	// ���ڸÿ������Ľ�����Դ�Ѿ��þ�
		{
			ind = j;									// ����j��ʾconnsҪ����IN_SCARCE[j]
		}
		return ind;
	}
	private Building arrangeThem(CourseRead cr, Building bd)
	// ��cr�����ܷŽ�����bd����Ľ�ѧ¥������bd����
	// ��������ܷŽ�ȥ����false, ���򷵻�true
	{
		if(cr==null)				// ����տγ�
		{
			return null;			// ˵��ǰ���Ѿ�������
		}
		Vector<BuildingLen> bdLen = new Vector<BuildingLen>();
		BuildingLen bl0 = new BuildingLen(bd, 0);
		bdLen.add(bl0);								// ���ȼ���bd
		// ���վ���bdԶ���������е�����
		Integer len = 0;							// ����ѧ¥�����
		for (Building myBuilding : buildings)
		{
			if (myBuilding.name != bd.name)
			{
				HashSet<Building> twoBuilding = new HashSet<Building>();
				twoBuilding.add(myBuilding);
				twoBuilding.add(bd);				// ����HashSet���ڲ�ѯcost
				len = COST.get(twoBuilding);		// ��������ѧ¥��ѯcost
				BuildingLen bl = new BuildingLen(myBuilding, len);
				bdLen.add(bl);						// ��bdLen�м���һ���ڵ�
			}
		}
		Comparator<BuildingLen> blcr = new LenComparator();
		Collections.sort(bdLen, blcr);				// ����bd�ľ�����������
		for (BuildingLen bdl : bdLen)
		{
			if (bdl.build.useRoom(cr))				// ������Է���
			{
//				System.out.println(bdl.build.name+": "+cr.getName());
				return bdl.build;					// �еط����˷��ؽ�ѧ¥
			}
			else
			{
//				System.out.println(cr.getName()+" cannot fit in "+bdl.build.name);
			}
		}
		return null;								// ���Ų����򷵻�null
	}
	public void arrange1()
	/* �ſ��㷨1��
	 * ̰�ķ�
	 * ��ConflictMap�еı�Ȩֵ��������
	 * ����ѧ¥�� B6-B3-B4-B5-B1����
	 * ��ʼ�������Ȩֵ�ıߵ������ڵ㹹�ɱ�Ǽ�M
	 * ѭ��ֱ�����е㶼����ǣ�
	 * ����~M����M�����нڵ���ض���ߣ�Ȩֵ֮����󣩵Ľڵ����M�����Ž�ѧ¥
	 * M�а����ධ��ѧ¥ʱ�¼���Ľڵ㰲������ض���ߵĽ�ѧ¥
	 * ����.
	 */
	{
		Comparator<CourseRead> idcr = new IDComparator();
		Collections.sort(firstSet,idcr);		// ��firstSet��ID���򣬷����ѯ
		Collections.sort(secondSet,idcr);		// ��secondSet��ID���򣬷����ѯ
		// ��ʼ��
		Comparator<Conflict> concr = new ConflictComparator();
		Collections.sort(conflictVec, concr);
		List<CourseRead> M1 = new ArrayList<CourseRead>();	// �Ѱ��ŵ������һ�ڿ�
		List<CourseRead> M2 = new ArrayList<CourseRead>();	// �Ѱ��ŵ�����ڶ��ڿ�
		Integer c1,c2;
		CourseRead cr1,cr2;
		Conflict con = conflictVec.firstElement();			// �ظ������������ſ�
		c1 = con.getCourse1();								// �ҵ���һ�ڿοκ�
		c2 = con.getCourse2();								// �ҵ��ڶ��ڿοκ�
		c2 -= firstSet.size();								// ת��Ϊ�ڶ��ڿε�index
		cr1 = firstSet.get(c1);								// ��firstSet�а�index�����һ�ڿ�
		cr2 = secondSet.get(c2);							// ��secondSet�а�index��ڶ��ڿ�
		for (Building buil : buildings)//���ſηֱ�ѡ����ʼ��ѧ¥,��ֹ������һ��¥����ͬʱ�������ſ�					
		{
			if (buil.useRoom(cr1))
			{
//				System.out.println("Start from "+cr1.getName()+" : "+buil.name);
				break;
			}
		}
		for (Building buil : buildings)//���ſηֱ�ѡ����ʼ��ѧ¥,��ֹ������һ��¥����ͬʱ�������ſ�					
		{
			if (buil.useRoom(cr2))
			{
//				System.out.println("Start from "+cr2.getName()+" : "+buil.name);
				break;
			}
		}
		M1.add(cr1);
		M2.add(cr2);
		
		// ����
		Building b1,b2;									// M�еĿγ������Ľ�ѧ¥
		Integer conns = 0;								// ~M�е�һ�ſ���M�еĿι�ͬѧ������
		Integer appInd = 0;								// ����������Դ����ʱ��conns�������±�
		Integer max_conn = 0;							// ~M�пγ���һ��¥�ڿγ̵����������
		Integer cnt_noConflict = 0;						// ����~M�еĿγ���M�и���ѧ¥�еĿγ�
		// ��û�����ӵ�������������10��ѭ���˳�
		CourseRead max_course = null;					// �������������~M�еĿγ�
		Set<Integer> hasAdded = new HashSet<Integer>();
		// ��M�г������Ѿ�������¿γ̵Ľ�ѧ¥���´η��ʵ���ʱ��Ͳ�������¿γ���
		// ע�⣺hasAdded��ֻ�ܴ�γ�ID����γ̶����γ����ַ����Ļ����Ƕ��Ƕ��󣬲���ʵ��Ψһ����
		while(cnt_noConflict<2*buildings.size() && 
				(M1.size()<firstSet.size() || M2.size()<secondSet.size()))
			// M1,M2����ѭ������
		{					
			cnt_noConflict = 0;					// ÿ�ֿ�ʼ֮ǰ��noConflict����������
			if (M2.size() < secondSet.size())	// firstSet, secondSet��С���ȣ��赥���ж�
			{
				max_conn = 0;
				max_course = null;
				hasAdded.clear();
				for (CourseRead it1 : M1)					// ������ڶ��ڿΣ�ѭ��M1��������
				{
					b1 = it1.getBuilding();					// M1�е�һ�ſ����ڵĽ�ѧ¥
					if(hasAdded.contains(b1.id))			// ���b1�Ѿ���ӹ��γ���
					{
						continue;							// ������ӿγ�
					}
					max_conn = 0;							// �������������
					max_course = null;						// ������ӿγ���Ϊ��
					Map<CourseRead,Integer> myFirstIn = b1.getFirstIn();// �ý�ѧ¥���еĵ�һ�ڿ�
					for (CourseRead cIt0 : secondSet)		// ����~M2����
					{
						conns = 0;							// ��ǰ����������
						if (!M2.contains(cIt0))				// ���cIt0�����Ѿ����źõļ�����
						{
							appInd = verifyCapacity(cIt0);	// �ÿ������Ƿ���ҽ���
							if (appInd!=-1)					// ȷ�н��ҽ���
							{
								conns += IN_SCARCE[appInd];	// ���ϸ��������Ȱ���
							}
							for (CourseRead cIt1 : myFirstIn.keySet())// ��b1�ڵĵ�һ�ڿ�cIt1
							{
								Set<Integer> twoCourse = new TreeSet<Integer>();
								twoCourse.add(cIt0.ID);
								twoCourse.add(cIt1.ID);
								if (conflictMap.get(twoCourse)!=null)// �����ͻ������Ϊ0
								{
									conns += conflictMap.get(twoCourse);	
									// conns���ϲ�ѯ���ſεĳ�ͻ����
								}
							}
						}
						if (conns>max_conn)	// ������>, =������Ϊ����conns=0�Ŀγ̿����ɻ����
						{
							max_conn = conns;	// ά��max_conn
							max_course = cIt0;	// ά��max_course
						}
					}
					if (max_course==null)		// ~M2�в�������b1�γ�����ϵ�Ŀγ�
					{
						for (CourseRead cIt2 : secondSet)
						{
							if (!M2.contains(cIt2))
							{
								max_course = cIt2;	// ��~M2�������һ�ſ���Ϊmax_course
								break;
							}
						}
					}
					Building bAdded = arrangeThem(max_course, b1);	// �����ſ�
					if(bAdded==null)								// ÿ��¥���Ų���
					{
						System.out.println
						(max_course.getName()+" course not fit in ERROR");
						return;										// �Ų������˳�
					}
					else
					{
						M2.add(max_course);			// ��max_course����M2
						hasAdded.add(bAdded.id);	// ��bAdded��Ϊ�Ѿ��Ź��γ̵Ľ�ѧ¥
						break;						// ÿ��ֻ��һ��ʱ��ε�һ�ſ�
//						if (M2.size()==secondSet.size())
//						{
//							break;				// ��M2������ǰbreak��������ѭ����course not fit in ERROR
//						}
					}
				}
			}
			if (M1.size() < firstSet.size())// firstSet, secondSet��С���ȣ��赥���ж�
			{
				max_conn = 0;
				max_course = null;
				hasAdded.clear();
				for (CourseRead it2 : M2)					// �������һ�ڿΣ�ѭ��M2��������
				{
					b2 = it2.getBuilding();					// M2�е�һ�ſ����ڵĽ�ѧ¥
					if(hasAdded.contains(b2.id))			// ���b2�Ѿ���ӹ��γ���
					{
						continue;							// ������ӿγ�
					}
					max_conn = 0;							// �������������
					max_course = null;						// ������ӿγ���Ϊ��
					Map<CourseRead,Integer> mySecondIn = b2.getSecondIn();
					// �ý�ѧ¥���еĵڶ��ڿ�
					for (CourseRead cIt0 : firstSet)		// ����~M1����
					{
						conns = 0;							// ��ǰ����������
						if (!M1.contains(cIt0))				// ���cIt0�����Ѿ����źõļ�����
						{
							appInd = verifyCapacity(cIt0);	// �ÿ������Ƿ���ҽ���
							if (appInd!=-1)					// ȷ�н��ҽ���
							{
								conns += IN_SCARCE[appInd];	// ���ϸ��������Ȱ���
							}
							for (CourseRead cIt1 : mySecondIn.keySet())// ��b1�ڵĵ�һ�ڿ�cIt1
							{
								Set<Integer> twoCourse = new TreeSet<Integer>();
								twoCourse.add(cIt0.ID);
								twoCourse.add(cIt1.ID);
								if(conflictMap.get(twoCourse)!=null)// �����ͻ������Ϊ0
								{
									conns += conflictMap.get(twoCourse);
									// conns�������ſεĳ�ͻ����
								}
							}
						}
						if (conns>max_conn)	// ������>, =������Ϊ����conns=0�Ŀγ̿����ɻ����
						{
							max_conn = conns;	// ά��max_conn
							max_course = cIt0;	// ά��max_course
						}
					}
					if (max_course==null)		// ~M1�в�������b2�γ�����ϵ�Ŀγ�
					{
						for (CourseRead cIt2 : firstSet)
						{
							if (!M1.contains(cIt2))
							{
								max_course = cIt2;	// ��~M1�������һ�ſ���Ϊmax_course
								break;
							}
						}
					}
					Building bAdded = arrangeThem(max_course, b2);	// �����ſ�
					if(bAdded==null)								// ÿ��¥���Ų���
					{
						System.out.println
						(max_course.getName()+" course not fit in ERROR");
						return;										// �Ų������˳�
					}
					else
					{
						M1.add(max_course);			// ��max_course����M1
						hasAdded.add(bAdded.id);	// ��bAdded��Ϊ�Ѿ��Ź��γ̵Ľ�ѧ¥
						break;						// ÿ��ֻ��һ��ʱ��ε�һ�ſ�
//						if (M1.size()==firstSet.size())
//						{
//							break;				// ��M1������ǰbreak��������ѭ����course not fit in ERROR
//						}
					}
				}
			}
		}
		// �������ͼ���С��µ���,����һЩ�γ��������γ̶�û����ϵ
//		if (M1.size()<firstSet.size() || M2.size()<secondSet.size())
//		{
//			Vector< Vector<CourseRead> > twoSets = new Vector< Vector<CourseRead> >();
//			twoSets.add(firstSet);				// ��firstSet��secondSetͳһ��ͬһ��������
//			twoSets.add(secondSet);
//			int i_twoSets = 0;					// ��������
//			for (i_twoSets=0; i_twoSets<2; i_twoSets++)
//			{
//				for (CourseRead cr : twoSets.get(i_twoSets))// �ֱ��firstSet��secondSet�еĿ�
//				{
//					if (M1.contains(cr) || M2.contains(cr))	// ���cr�Ѿ������Ź���
//					{
//						continue;							// �Ͳ�������
//					}
//					for (Building buil : buildings)			// ѭ����¥
//					{
//						if (buil.useRoom(cr))				// �ҵ����԰��ŵ�¥
//						{
//							if (i_twoSets==0)
//							{
//								M1.add(cr);
//							}
//							else
//							{
//								M2.add(cr);
//							}
//							break;							// ���˳�ѭ����¥
//						}
//					}
//				}
//			}
//		}
	}
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		int arrangeCost = 0;					// �����ſη�ʽ�µ�cost����
//		setBuildings();
//		readCourseList();
//		readConflictVec();
//		initCourseLeft();
//		arrange1();
//		arrangeCost = computeCost();
//		System.out.println("Cost function: "+arrangeCost);
//	}

}

class BuildingLen {								// ��ѧ¥����ǰ��ѧ¥bd�ľ���
	public Building build;						// ���Ľ�ѧ¥
	public Integer len2bd;						// ������ѧ¥֮��ľ���
	
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
		return bd1.len2bd.compareTo(bd2.len2bd);	// ��С��������
	}
}

class IDComparator implements Comparator<CourseRead> {	// Vector<CourseRead>��ID����
	public int compare(CourseRead c1,CourseRead c2){
		CourseRead cr1 = c1;
		CourseRead cr2 = c2;
		return cr1.ID.compareTo(cr2.ID);	// ��С��������
	}
}