package cn.exam.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * easyexcel工具类 -- Excel解析
 */

public class EasyExcelUtil {
    private static Log log = LogFactory.getLog(EasyExcelUtil.class);
	/**
	 * 	单sheet版本Excel读取
	  * 从Excel中读取文件，读取的文件是一个DTO类
     */
    public static <T> List<T> readExcelOneSheet(InputStream inputStream, final Class<?> clazz) {
        ExcelListener<T> listener = new ExcelListener<>();
        ExcelReader excelReader = EasyExcel.read(inputStream, clazz, listener).build();
        ReadSheet readSheet = EasyExcel.readSheet(0).build();
        excelReader.read(readSheet);
        // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
        excelReader.finish();
        return listener.getDatas();
    }

    /**
     * 	多sheet版本Excel读取
     * @param <T>
     * @param fileName
     * @param clazz
     * @return
     */
    public static <T> List<T> readExcelAllSheet(String fileName, final Class<?> clazz) {
        ExcelListener<T> listener = new ExcelListener<>();
        // 读取全部sheet
        // 这里需要注意 ExcelListener的doAfterAllAnalysed 会在每个sheet读取完毕后调用一次。然后所有sheet都会往同一个DemoDataListener里面写
        EasyExcel.read(fileName, clazz, listener).doReadAll();
        return listener.getDatas();
    }


    /**
     * 	网页上的下载导出
     * @param fileName 文件名
     * @param clazz  类的字节码文件
     * @param datas 导出的数据
     * @throws IOException 异常对象
     */
    public static void writeWeb(String fileName, final Class<?> clazz,List<?> datas,HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
    	OutputStream outputStream = response.getOutputStream();
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName );
        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).build();
        //这里 需要指定写用哪个class去写
    	WriteSheet writeSheet = EasyExcel.writerSheet(0, "sheet1").head(clazz).build();
    	excelWriter.write(datas, writeSheet);

        //千万别忘记finish 会帮忙关闭流
        outputStream.flush();
		excelWriter.finish();
	}


	public static void writeSheelWeb(String fileName, final Class<?> clazz,List<?> datas,String sheelName,HttpServletResponse response) throws IOException {
		// 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
		OutputStream outputStream = response.getOutputStream();
		response.setContentType("application/vnd.ms-excel");
		response.setCharacterEncoding("utf-8");
		// 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
		fileName = URLEncoder.encode(fileName, "UTF-8");
		response.setHeader("Content-disposition", "attachment;filename=" + fileName );
		ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).build();
		//这里 写入多sheel
		for (int i =0 ; i<datas.size();i++ ){
			WriteSheet writeSheet = EasyExcel.writerSheet(i, sheelName).head(clazz).build();
			excelWriter.write(datas, writeSheet);
		}
		//千万别忘记finish 会帮忙关闭流
		excelWriter.finish();
		outputStream.flush();
	}

	public static<T> void writeExcelList(HttpServletResponse response, List<List<T>> data, String fileName, Class clazz,String sheetName) throws Exception {
		long exportStartTime = System.currentTimeMillis();
		log.info("报表导出Size: "+data.size()+"条。");

//		List<List<T>> lists = SplitList.splitList(data,MAXROWS); // 分割的集合

		OutputStream out = getOutputStream(fileName, response);
		ExcelWriterBuilder excelWriterBuilder = EasyExcel.write(out, clazz).excelType(ExcelTypeEnum.XLSX).registerWriteHandler(getDefaultHorizontalCellStyleStrategy());
		ExcelWriter excelWriter = excelWriterBuilder.build();
		ExcelWriterSheetBuilder excelWriterSheetBuilder;
		WriteSheet writeSheet;
		for (int i =1;i<=data.size();i++){
			excelWriterSheetBuilder = new ExcelWriterSheetBuilder(excelWriter);
			excelWriterSheetBuilder.sheetNo(i);
			excelWriterSheetBuilder.sheetName(sheetName+i);
			writeSheet = excelWriterSheetBuilder.build();
			excelWriter.write(data.get(i-1),writeSheet);
		}
		out.flush();
		excelWriter.finish();
		out.close();
		System.out.println("报表导出结束时间:"+ new Date()+";写入耗时: "+(System.currentTimeMillis()-exportStartTime)+"ms" );
	}

	private static OutputStream getOutputStream(String fileName, HttpServletResponse response) throws Exception {
		fileName = URLEncoder.encode(fileName, "UTF-8");
		//  response.setContentType("application/vnd.ms-excel"); // .xls
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); // .xlsx
		response.setCharacterEncoding("utf8");
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
		return response.getOutputStream();
	}
	/**
	 * 获取默认表头内容的样式
	 * @return
	 */
	private static HorizontalCellStyleStrategy getDefaultHorizontalCellStyleStrategy(){
		/** 表头样式 **/
		WriteCellStyle headWriteCellStyle = new WriteCellStyle();
		// 背景色（浅灰色）
		// 可以参考：https://www.cnblogs.com/vofill/p/11230387.html
		headWriteCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		// 字体大小
		WriteFont headWriteFont = new WriteFont();
		headWriteFont.setFontHeightInPoints((short) 10);
		headWriteCellStyle.setWriteFont(headWriteFont);
		//设置表头居中对齐
		headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
		/** 内容样式 **/
		WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
		// 内容字体样式（名称、大小）
		WriteFont contentWriteFont = new WriteFont();
		contentWriteFont.setFontName("宋体");
		contentWriteFont.setFontHeightInPoints((short) 10);
		contentWriteCellStyle.setWriteFont(contentWriteFont);
//		//设置内容垂直居中对齐
//		contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//		//设置内容水平居中对齐
//		contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
		//设置边框样式
		contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
		contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
		contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
		contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);
		// 头样式与内容样式合并
		return new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
	}

    /**
 	* 导出 Excel到指定目录 ：单个 sheet，带表头,
	 *
	 * @param tableData
	 * @param fileName  导出的路径+文件名  例如：   file/test.xlsx
	 * @param sheetName 导入文件的 sheet 名
	 * @throws Exception
	 */
	public static void writeExcelAutoColumnWidth(String fileName,List<?> tableData,String sheetName,Class<?> Object) throws Exception {
		// 根据用户传入字段 假设我们要忽略 date
		EasyExcel.write(fileName,Object)
			.sheet(sheetName)
			.registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
			.doWrite(tableData);
	}


	/**
	 * 导出 Excel到指定目录 ：单个 sheet，带表头,
	 *  @param fileName  导出的路径+文件名  例如：   file/test.xlsx
	 * @param tableData
	 */
	public static void writeExcelWithOneSheet1(String fileName, List<?> tableData, String sheetName, Class<?> Object, Set<String> excludeColumnFiledNames) {
		// 根据用户传入字段 假设我们要忽略 date
		EasyExcel.write(fileName,Object)
				.excludeColumnFiledNames(excludeColumnFiledNames)
				.sheet(sheetName)
				.registerWriteHandler(styleWrite(false))
				.doWrite(tableData);
	}

	 public static HorizontalCellStyleStrategy styleWrite(boolean isWrapped) {
	        // 头的策略
	        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
	        // 背景设置为红色
	        // headWriteCellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
	        WriteFont headWriteFont = new WriteFont();
	        headWriteFont.setFontHeightInPoints((short)18);
	        headWriteCellStyle.setWriteFont(headWriteFont);
	        // 内容的策略
	        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
	        // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色.头默认了 FillPatternType所以可以不指定
	        //contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
	        // 背景绿色
	        //contentWriteCellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
	        WriteFont contentWriteFont = new WriteFont();
	        // 字体大小
	        contentWriteFont.setFontHeightInPoints((short)11);
	        //设置 自动换行
	        contentWriteCellStyle.setWrapped(isWrapped);
	      //设置 垂直居中
	        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
	        contentWriteCellStyle.setWriteFont(contentWriteFont);
	        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
		 return new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);

	        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
	        //EasyExcel.write(fileName, DemoData.class).registerWriteHandler(horizontalCellStyleStrategy).sheet("模板")
	        //    .doWrite(data());
	    }
}
