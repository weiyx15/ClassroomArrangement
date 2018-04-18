package baseline;

import java.util.HashSet;

public class Conflict {// Conflict�࣬���ڹ���Vector<Conflict>�����"ConflictMap"�����ݲ���common����
	private HashSet<Integer> twoCourses = new HashSet<Integer>();	
	// ������ͻ��2�ſγ̵�id���ɵ�HashSet
	private Integer common;							// ��ͬѡ�ε�ѧ������
	private Integer ID1, ID2;						// ���ڼ��ط��ؿγ�ID
	
	public void setConflict(Integer id1, Integer id2, Integer com)
	{
		twoCourses.add(id1);
		twoCourses.add(id2);
		common = com;
		ID1 = id1;
		ID2 = id2;
	}
	public Integer getCourse1()
	{
		return ID1;
	}
	public Integer getCourse2()
	{
		return ID2;
	}
	public Integer getCommon()
	{
		return common;
	}
}