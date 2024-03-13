package cn.exam.util;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * excel通用读取监听类
 */

@Slf4j
public class ExcelListener<T> extends AnalysisEventListener<T> {

	/**
	 * 自定义用于暂时存储data 可以通过实例获取该值
	 */
	private List<T> datas = new ArrayList<>();

	/**
	 * 每解析一行都会回调invoke()方法
	 *
	 * @param data 每一行的数据
	 */
	@Override
	public void invoke(T data, AnalysisContext context) {
		datas.add(data);
	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext context) {
		log.info("{}条数据，解析完成", datas.size());
	}

	public List<T> getDatas() {
		return datas;
	}
}

