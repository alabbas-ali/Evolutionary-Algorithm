package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import module.Individual;

public class ExcelOperation {
	
	static final String EXCL_FILE = "e:/result.xlsx";
	
	private File file;
	private XSSFWorkbook myWorkBook;
	private XSSFSheet mySheet;
	private FileInputStream myInput;
	
	public ExcelOperation() throws IOException 
	{
		file = new File(EXCL_FILE);
		myInput = new FileInputStream(file);
		myWorkBook = new XSSFWorkbook(myInput);
		mySheet = myWorkBook.getSheetAt(0);
	}
	
	public void creatHeadLine()
	{
		XSSFRow row1 = mySheet.createRow(0);
		XSSFCell r1c1 = row1.createCell(0);
		r1c1.setCellValue("Generation Number");
		XSSFCell r1c2 = row1.createCell(1);
		r1c2.setCellValue("Monitors");
		XSSFCell r1c3 = row1.createCell(2);
		r1c3.setCellValue("Penalty");
		XSSFCell r1c4 = row1.createCell(3);
		r1c4.setCellValue("Fitness");
		XSSFCell r1c5 = row1.createCell(4);
		r1c5.setCellValue("Covered Edges");
		XSSFCell r1c6 = row1.createCell(5);
		r1c6.setCellValue("Uncovered Edges");
	}
	
	public void addIndividualInfo(Individual<Integer> ind , int lineNumber , int totalEdgeCount)
	{
		XSSFRow row = mySheet.createRow(lineNumber);
		XSSFCell r12c1 = row.createCell(0);
		r12c1.setCellValue(lineNumber);
		XSSFCell r12c2 = row.createCell(1);
		r12c2.setCellValue(ind.getMonitors());
		XSSFCell r12c3 = row.createCell(2);
		r12c3.setCellValue(ind.getPenalty());
		XSSFCell r12c4 = row.createCell(3);
		r12c4.setCellValue(ind.getFitness());
		XSSFCell r12c5 = row.createCell(4);
		r12c5.setCellValue((totalEdgeCount - (ind.getPenalty() / 2)));
		XSSFCell r12c6 = row.createCell(5);
		r12c6.setCellValue(ind.getPenalty() / 2);
	}
	
	public void save() throws IOException
	{
		myInput.close();
		FileOutputStream myOutput = new FileOutputStream(file);
		myWorkBook.write(myOutput);
		myOutput.close();
		System.out.println("Done");
	}

}
