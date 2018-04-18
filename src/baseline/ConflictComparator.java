package baseline;

import java.util.Comparator;

public class ConflictComparator implements Comparator<Conflict> {	// Vector<Conflict>按冲突人数降序
	public int compare(Conflict c1,Conflict c2){
		Conflict cl1 = c1;
		Conflict cl2 = c2;
		return cl2.getCommon().compareTo(cl1.getCommon());		// 从大到小排序
	}
}