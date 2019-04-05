/**  
 * Copyright © 2019公司名字. All rights reserved.
 * @Title: ExcelWriteUtil.java
 * @Prject: SiteMonitor
 * @Package: com.hnjing.utils.file.office
 * @Description: TODO
 * @author: Jinlong He
 * @mail: hejinlong01@hnjing.com
 * @date: 2019年1月11日 上午9:10:30
 * @version: V1.0  
 */
package com.hnjing.utils.file.office;

import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

/**
 * @ClassName: ExcelWriteUtil
 * @Description: TODO
 * @author: Jinlong He
 * @date: 2019年1月11日 上午9:10:30
 */
public class ExcelWriteUtil {
	
	   public static void recordXls(byte[] b, String filename) {
		   FileOutputStream os;
			try {
				os = new FileOutputStream(filename + ".xls");
				// 写入输出流
				os.write(b);
				// 关闭输出流
				os.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}         
	   }
	
	   public static HSSFWorkbook getHSSFWorkbook(String sheetName, String[] title,String[][] values, HSSFWorkbook wb){
	        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
	        if(wb == null){
	            wb = new HSSFWorkbook();
	        }
	        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
	        HSSFSheet sheet = wb.createSheet(sheetName);

	        //声明单元行对象
	        HSSFRow row = null;
	        //声明单元对象
	        HSSFCell cell = null;

	        int rowshifting = 0; //行位移，如包括标题
	        //处理标题
	        if(title!=null && title.length>0) {
	        	//标题格式
	        	HSSFCellStyle titleStyle = wb.createCellStyle();	        
		        titleStyle.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式
		        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);         	
		        //在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
	        	row = sheet.createRow(0);
	        	rowshifting++;
		        //创建标题
		        for(int i=0;i<title.length;i++){
		        	//创建单元格，并设置值表头 设置表头居中
		            cell = row.createCell(i);  
		            cell.setCellValue(title[i]);
		            cell.setCellStyle(titleStyle);
		        }
	        }	        
	        
	        //处理内容
	        if(values!=null && values.length>0) {
		        for(int i=0;i<values.length;i++){
		            row = sheet.createRow(i + rowshifting);
		            for(int j=0;j<values[i].length;j++){
		                //将内容按顺序赋给对应的列对象
		                row.createCell(j).setCellValue(values[i][j]);
		            }
		        }
	        }
	        return wb;
	    }
}
