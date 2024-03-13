
package cn.exam.util;

import java.util.List;

// CallBack 接口：主要用于 PageUtil 中的

public interface CallBack<T> {
	/**
	 * 回调函数
	 */
    List<T> callBack();
}
