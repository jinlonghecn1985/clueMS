package com.hnjing.utils.file.office;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.hnjing.utils.DateUtil;

/**
 * @ClassName: ExcelUtil
 * @Description: 读取csv\xls\xlsx文件内容
 * @author: Jinlong He
 * @date: 2017年12月29日 上午10:13:05
 */
public class ExcelUtil {  
//	public static void main(String[] arg){
//			List<List<String>> xls = null;
//			int i=1;
//			try {
//				xls = readExcel("D:\\02话术配置20190212-demo.xls", true);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			if(xls!=null && xls.size()>0){
//				for(List<String> row : xls){
//					if(row!=null && row.size()>0){
//						int j=0;
//						for(String obj : row){
//							System.out.print(j+++"c "+obj+" ");							
//						}
//						System.out.println(" row***********"+(i++));
//					}
//				}
//			}		
//	}
	
	
	/** 
	* @Title: readCSV 
	* @Description: 读取CSV
    * @param filePath 文件路径
    * @param header 是否包括头行
	* @return
	* @throws IOException  List<List<String>>    返回类型 
	* @throws 
	*/
	public static List<List<String>> readCSV(String filePath, boolean header) throws IOException{
		String charset = "GBK";
		String delimiter = ",";
		BufferedReader readTxt = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), charset));
		List<List<String>> list = new LinkedList<List<String>>();  		
		String inStr = null;
		while ((inStr = readTxt.readLine())!=null) {
			if(inStr.length()>0){
				String[] str = inStr.split(delimiter);
				List<String> linked = new LinkedList<String>();  
				for (int i = 0; i < str.length; i++) {
					linked.add(str[i]);
				}
				list.add(linked);
			}
		}
		readTxt.close();
		return list;
	}
	
	
	/** 
	* @Title: readExcel 
	* @Description: 读取excel
    * @param filePath 文件路径
    * @param header 是否包括头行
	* @return
	* @throws Exception  List<List<String>>    返回类型 
	* @throws 
	*/
	public static List<List<String>> readExcel(String filePath, boolean header) throws Exception{
		if(filePath.toLowerCase().endsWith("xls")){
			return readExcel03Down(filePath, header);
		}else{
			return readExcel07Up(filePath, header);
		}
	}
	
	public static List<List<String>> readDocFile(String filePath, boolean header) throws Exception{
		if(filePath.toLowerCase().endsWith(".xlsx")){
			try {
				return readExcel07Up(filePath, header);
			} catch (Exception e) {
				try {
					return readExcel03Down(filePath, header);
				} catch (Exception e1) {
					return readCSV(filePath, header);					
				}
			}
		}else if(filePath.toLowerCase().endsWith(".xls")){
			try {
				return readExcel03Down(filePath, header);
			} catch (Exception e) {
				try {
					return readExcel07Up(filePath, header);
				} catch (Exception e1) {					
					return readCSV(filePath, header);
				}
			}
		}else{
			try {
				return readCSV(filePath, header);
			} catch (Exception e) {
				try {
					return readExcel03Down(filePath, header);
				} catch (Exception e1) {					
					return readExcel07Up(filePath, header);
				}
			}
		}
	}

	public static List<List<String>> readDocFile(File file, boolean header) throws Exception {
		try {
			return readExcel07Up(file, header);
		} catch (Exception e) {
			return readExcel03Down(file, header);
		}

	}
	
	/** 
	* @Title: readExcel03Down 
	* @Description: 读取excel  要求excel版本在2003及以下  且只读取第一张工作薄
    * @param filePath 文件路径
    * @param header 是否包括头行
	* @return
	* @throws Exception  List<List<String>>    返回类型 
	* @throws 
	*/
	public static List<List<String>> readExcel03Down(String filePath, boolean header) throws Exception{
		File file = new File(filePath);
        if(!file.exists()){
            throw new Exception("找不到文件");  
        }  
        return readExcel03Down(file, header);
	}
	
	public static List<List<String>> readExcel03Down(File file, boolean header) throws Exception{
		List<List<String>> list = new LinkedList<List<String>>();  
		POIFSFileSystem poifsFileSystem = new POIFSFileSystem(new FileInputStream(file));  		
        Workbook workbook = new HSSFWorkbook(poifsFileSystem);            
        Sheet sheet = workbook.getSheetAt(0);            
        for (int i = (sheet.getFirstRowNum() +(header?0:1) ); i <= (sheet.getPhysicalNumberOfRows() - 1); i++) {  
        	Row row = sheet.getRow(i);
        	if (row == null) {  
                continue;  //空行，跳过
            }              
            List<String> linked = new LinkedList<String>();  
            for (int j = row.getFirstCellNum(); j <= row.getLastCellNum()-1; j++) { 
            	 String value = null;              	
                 Cell cell = row.getCell(j); 
                 if(cell!=null) {
	                 CellType ct = cell.getCellTypeEnum();
	                 if(ct.equals(CellType.STRING)){
	                 	//STRING                	
	                 	value = cell.getStringCellValue();
	                 }else if(ct.equals(CellType.NUMERIC)){
	                 	//NUMERIC  
	//                 	System.out.println(cell.toString());
	//                 	System.out.println(cell.getCellStyle().getDataFormatString());
	                 	if (!"@".equals(cell.getCellStyle().getDataFormatString()) && !"General".equals(cell.getCellStyle().getDataFormatString())) {
	                 		value = DateUtil.DateToString(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));  
	                 	}else{
	                 		DecimalFormat df = new DecimalFormat("0");  
	                     	value = df.format(cell.getNumericCellValue());
	                 	}
	                 }else if(ct.equals(CellType.BOOLEAN)){
	                 	//ERROR                	
	                 	value = cell.getBooleanCellValue()==true?"true":"false";
	                 }else if(ct.equals(CellType.BLANK)){
	                 	//BLANK 空格
	                 	value = " ";
	                 }else if(ct.equals(CellType._NONE)){
	                 	//_NONE
	                 	value = null;
	                 }else{
	                 	value = cell.toString();
	                 }
                 }
                 linked.add(value==null?"":value);           
            }
            if (linked.size()!= 0) {  
                list.add(linked);  
            }
        }
        workbook.close();  
        return list;
	}
	
	
    /** 
    * @Title: readExcel07Up 
    * @Description: 读取excel  要求excel版本在2007及以上  且只读取第一张工作薄
    * @param filePath 文件路径
    * @param header 是否包括头行
    * @return
    * @throws Exception  List<List<Object>>    返回类型 
    * @throws 
    */
    public static List<List<String>> readExcel07Up(String filePath, boolean header) throws Exception {  
    	File file = new File(filePath);
        if(!file.exists()){
            throw new Exception("找不到文件");  
        }  
        return readExcel07Up(file, header);
    }  
    
    public static List<List<String>> readExcel07Up(File file, boolean header) throws Exception{
    	List<List<String>> list = new LinkedList<List<String>>();  
        XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(file));  
        // 只读取第一张表格内容  
        XSSFSheet sheet = xwb.getSheetAt(0);         
        for (int i = (sheet.getFirstRowNum() +(header?0:1) ); i <= (sheet.getPhysicalNumberOfRows() - 1); i++) {  
        	XSSFRow row = sheet.getRow(i);
            if (row == null) {  
                continue;  //空行，跳过
            }              
            List<String> linked = new LinkedList<String>();  
            for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {             	
                String value = null;  
                //System.out.println(j);
                XSSFCell cell = row.getCell(j);  
                if (cell != null) {                     
	                CellType ct = cell.getCellTypeEnum();
	                if(ct.equals(CellType.STRING)){
	                	//STRING                	
	                	value = cell.getStringCellValue();
	                }else if(ct.equals(CellType.NUMERIC)){
	                	//NUMERIC  
	//                	System.out.println(cell.toString());
	//                	System.out.println(cell.getCellStyle().getDataFormatString());
	                	if (!"@".equals(cell.getCellStyle().getDataFormatString()) && !"General".equals(cell.getCellStyle().getDataFormatString())) {
	                		value = DateUtil.DateToString(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));  
	                	}else{
	                		DecimalFormat df = new DecimalFormat("0");  
	                    	value = df.format(cell.getNumericCellValue());
	                	}
	                }else if(ct.equals(CellType.ERROR)){
	                	//ERROR                	
	                	value = cell.getErrorCellString();
	                }else if(ct.equals(CellType.BOOLEAN)){
	                	//ERROR                	
	                	value = cell.getBooleanCellValue()==true?"true":"false";
	                }else if(ct.equals(CellType.BLANK)){
	                	//BLANK 空格
	                	value = " ";
	                }else if(ct.equals(CellType._NONE)){
	                	//_NONE
	                	value = null;
	                }else{
	                	value = cell.toString();
	                }
                }
                linked.add(value==null?"":value);
            }
            if (linked.size()!= 0) {  
                list.add(linked);  
            }
        }
        xwb.close();
        return list;  
    }
    
 
}  
