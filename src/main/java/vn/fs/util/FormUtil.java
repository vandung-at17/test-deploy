package vn.fs.util;

import org.springframework.beans.BeanUtils;

public class FormUtil {

	// để mapping dữ liệu đầu vào giữa api và thằng model
	public static <T> T toModel (Class<T> clazz, Class<T> classs) {
		T objectdest = null;
		T objectorig = null;
		try {
			objectdest = clazz.newInstance();
			objectorig = classs.newInstance();
			BeanUtils.copyProperties(objectdest, objectorig);
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO: handle exception
		}
		return objectdest;
	}
}
