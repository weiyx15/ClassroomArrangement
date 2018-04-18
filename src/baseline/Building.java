/* Building��̻���ѧ¥����Ϣ�����ң��������� */

package baseline;

import java.util.*;

public class Building {
	public String name;	// ���֣� String�࣬������Ϊ��ʶ������
	public int id;		// id: ��������ѧ¥��Ψһ��ʶ��
	protected Map<Integer,Integer> roomMap = new TreeMap<Integer,Integer>();
	// �����б���һ���Ƿ����������ڶ��������������ķ������
	// ����Map������ֵ����������������������������ľ���С�Ľ���
	protected Map<Integer,Integer> roomMap2 = new TreeMap<Integer,Integer>();
	// ������ڶ��ڣ���Ҫͬ��һ���µĽ�����Դ
	protected Map<CourseRead,Integer> firstIn= new HashMap<CourseRead,Integer>();
	// �ö���ѧ¥�������һ�ڿγ��б�, value�����ڽ��ҵ�����
	protected Map<CourseRead,Integer> secondIn= new HashMap<CourseRead,Integer>();
	// �ö���ѧ¥������ڶ��ڿγ��б�, value�����ڽ��ҵ�����
	
	public Building(String s, int ID)
	{
		name = s;
		id = ID;
	}
	public Map<Integer,Integer> getRoomMap()				// ���ط����б�
	{
		return roomMap;
	}
	public Map<CourseRead,Integer> getFirstIn()
	{
		return firstIn;
	}
	public Map<CourseRead,Integer> getSecondIn()
	{
		return secondIn;
	}
	public void addRoom(Integer capacity, Integer num)		// ������ѧ¥ʱ��ƽ��������͸���
	{
		roomMap.put(capacity, num);
		roomMap2.put(capacity, num);
	}
	public Boolean useRoom(CourseRead cr)// ��һ���γ�ռ�ý���. true: �Ž�ȥ��; false:���˷Ų���
	{
		int oldValue = 0, oldKey = 0;
		if(cr.getTime()==0)									// ���������һ��
		{
			for (Map.Entry<Integer, Integer> entry : roomMap.entrySet())
			{
				oldKey = entry.getKey();					// oldKey: ��������
				oldValue = entry.getValue();				// oldValue: �������ķ������
				if (oldKey>=cr.getCapacity() && oldValue>0)	// ���������Ҫ��Ŀշ���
				{
					roomMap.put(oldKey, oldValue-1);		// ���������һ��
					firstIn.put(cr, oldKey);				// ��ѧ¥�Ŀγ��б����һ��
					cr.setBuilding(this);					// �γ����ڵĽ�ѧ¥
					cr.setRoomCapa(oldKey);					// �γ����ڵĽ�������
					return true;							// ���ſο��Է����ⶰ¥��
				}
			}
			return false;									// ���ſηŲ����ⶰ¥��
		}
		else												// ��������ڶ���
		{
			for (Map.Entry<Integer, Integer> entry : roomMap2.entrySet())
			{
				oldKey = entry.getKey();
				oldValue = entry.getValue();
				if (oldKey>=cr.getCapacity() && oldValue>0)
				{
					roomMap2.put(oldKey, oldValue-1);
					secondIn.put(cr, oldKey);
					cr.setBuilding(this);
					cr.setRoomCapa(oldKey);
					return true;							// ���ſο��Է����ⶰ¥��
				}
			}
			return false;									// ���ſηŲ����ⶰ¥��
		}
	}
}