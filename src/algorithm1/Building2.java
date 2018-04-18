/*
 * �̳�Building�������Ӷ���courseLeft��redundant��ά��
 */

package algorithm1;

import java.util.Map;

import baseline.Building;
import baseline.CourseRead;
import baseline.Arrange;

public class Building2 extends Building{
	public Building2(String s, int ID)
	{
		super(s, ID);
	}
	
	private void courseLeftMinus(CourseRead cr)	// ��һ�ſη����ѧ¥��ִ��courseLeft[i][j]--
	{
		int i=cr.getTime();						// �����������һ�ڿλ�������ڶ��ڿ�
		int capa = cr.getCapacity();			// ������
		if (capa > Arrange.ROOM_CAPACITY[2])	// >150
		{
			Branch.courseLeft[i][0]--;
		}
		if (capa > Arrange.ROOM_CAPACITY[1])	// >60
		{
			Branch.courseLeft[i][1]--;
		}
		if (capa > Arrange.ROOM_CAPACITY[0])	// >30
		{
			Branch.courseLeft[i][2]--;
		}
	}
	private void redundantMinus(int capa, CourseRead cr)	// ���ұ�ռ�ú�ʣ�����--
	{
		int i = cr.getTime();					// // �����������һ�ڿλ�������ڶ��ڿ�
		if (capa > Arrange.ROOM_CAPACITY[2])	// >150
		{
			Branch.redundant[i][0]--;
		}
		if (capa > Arrange.ROOM_CAPACITY[1])	// >60
		{
			Branch.redundant[i][1]--;
		}
		if (capa > Arrange.ROOM_CAPACITY[0])	// >30
		{
			Branch.redundant[i][2]--;
		}
	}
	private boolean verifyRoomScarce(CourseRead cr, int roomCapa)
	// �Ƿ����˿γ�Խ��ռ��ϡȱ������Դ�����
	// i: ����ռ�ý��ҵĿγ��������һ�ڻ�������ڶ���
	// roomCapa�� �ÿγ���׼�Ľ�������
	{
		int i = cr.getTime();					// i: �Ͽ�ʱ��
		int courseCapa = cr.getCapacity();		// ������
		int j = -1, k = -1;	// j:���ҵ�ϡȱ�ȼ���k: �γ̵�ϡȱ�ȼ�,����ʼ��Ϊ-1��ʾ��ϡȱ
		if (roomCapa>Arrange.ROOM_CAPACITY[2])	// >150
		{
			j = 0;
		}
		else if(roomCapa>Arrange.ROOM_CAPACITY[1])// > 60
		{
			j = 1;
		}
		else if(roomCapa>Arrange.ROOM_CAPACITY[0])// >30
		{
			j = 2;
		}
		if (courseCapa>Arrange.ROOM_CAPACITY[2])	// >150
		{
			k = 0;
		}
		else if(courseCapa>Arrange.ROOM_CAPACITY[1])// > 60
		{
			k = 1;
		}
		else if(courseCapa>Arrange.ROOM_CAPACITY[0])// >30
		{
			k = 2;
		}
		if (j!=-1 && (j<k||k==-1) && Branch.redundant[i][j]<=Branch.courseLeft[i][j])
			// ���Խ��ռ��ϡȱ��Դ
		{
			return true;		// �ý�����Դϡȱ������������
		}
		else
		{
			return false;
		}	
	}
	// ����useRoom�������������courseLeft��redundant�Ĵ���
	public Boolean useRoom(CourseRead cr)// ��һ���γ�ռ�ý���. true: �Ž�ȥ��; false:���˷Ų���
	{
		int oldValue = 0, oldKey = 0;
		if(cr.getTime()==0)									// ���������һ��
		{
			for (Map.Entry<Integer, Integer> entry : roomMap.entrySet())
			{
				oldKey = entry.getKey();					// oldKey: ��������
				oldValue = entry.getValue();				// oldValue: �������ķ������
				if (!verifyRoomScarce(cr,oldKey) && 
						oldKey>=cr.getCapacity() && oldValue>0)	// ���������Ҫ��Ŀշ���
				{
					roomMap.put(oldKey, oldValue-1);		// ���������һ��
					firstIn.put(cr, oldKey);				// ��ѧ¥�Ŀγ��б����һ��
					cr.setBuilding(this);					// �γ����ڵĽ�ѧ¥
					cr.setRoomCapa(oldKey);					// �γ����ڵĽ�������
					courseLeftMinus(cr);					// courseLeft[i][j]--
					redundantMinus(oldKey,cr);				// redundant[i][j]--
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
				if (!verifyRoomScarce(cr,oldKey) && 
						oldKey>=cr.getCapacity() && oldValue>0)
				{
					roomMap2.put(oldKey, oldValue-1);		// ���������һ��
					secondIn.put(cr, oldKey);				// ��ѧ¥�Ŀγ��б����һ��
					cr.setBuilding(this);					// �γ����ڵĽ�ѧ¥
					cr.setRoomCapa(oldKey);					// �γ����ڵĽ�������
					courseLeftMinus(cr);					// courseLeft[i][j]--
					redundantMinus(oldKey,cr);				// redundant[i][j]--
					return true;							// ���ſο��Է����ⶰ¥��
				}
			}
			return false;									// ���ſηŲ����ⶰ¥��
		}
	}
}
