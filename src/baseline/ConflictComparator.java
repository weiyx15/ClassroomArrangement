package baseline;

import java.util.Comparator;

public class ConflictComparator implements Comparator<Conflict> {	// Vector<Conflict>����ͻ��������
	public int compare(Conflict c1,Conflict c2){
		Conflict cl1 = c1;
		Conflict cl2 = c2;
		return cl2.getCommon().compareTo(cl1.getCommon());		// �Ӵ�С����
	}
}