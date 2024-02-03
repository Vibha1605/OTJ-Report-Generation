package com.maantt.otj.otjservice.service;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.example.otj.OtjApplication;
import com.maantt.otj.otjservice.model.AssessmentReport;
import com.maantt.otj.otjservice.repository.AssessmentReportRepository;
@Service
 
public class AssessmentReportService {
	
	private final AssessmentReportRepository repository;
	private static AssessmentReport assessmentReport = new AssessmentReport();
	

	private static final Logger log = LoggerFactory.getLogger(AssessmentReportRepository.class);
	private static final String FILE_NAME = "C://Users//vibha srinivasan//Downloads//excel.xlsx";
	
	@Autowired
	public AssessmentReportService(AssessmentReportRepository repository) {
        this.repository = repository;
        this.assessmentReport = new AssessmentReport();
    }
	public List<AssessmentReport> listAllReport(){
		return repository.findAll();
	}

	public void save(AssessmentReport assessmentReport) {
		repository.save(assessmentReport);
	}
	
	public void ExcelVerification() {
		
		try (FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
	             Workbook workbook = new XSSFWorkbook(excelFile)) {

	            AssessmentReport report = new AssessmentReport();
	            Sheet reportSheet = workbook.getSheetAt(0);
	            validateReportSheet(reportSheet);

	            
	            Sheet skillClusterSheet = workbook.getSheetAt(2);
	            validateSkillClusterSheet(skillClusterSheet);
	            repository.save(assessmentReport);

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    
	    private static void validateReportSheet(Sheet sheet) {
	        System.out.println("Validating On the Job Training Assessment Report sheet...");
	  
	        validateCellValue(sheet, "A2", "On the Job Training Assessment Report");

	        validateCellValue(sheet, "A4", "Employee ID");
	        validateEmployeeId(sheet, "A6");

	        validateCellValue(sheet, "E4", "Employee Name");

	        validateCellValue(sheet, "J4", "Assessment Date");

	        validateCellValue(sheet, "A7", "OJT Name");

	        validateEmail(sheet, "E6");

	        validateDate(sheet, "J6");

	        validateCellValue(sheet, "A13", "Areas of Excellence");
	        validateAreas(sheet, "A14");

	        validateCellValue(sheet, "A18", "Areas of Improvement");
	        validateAreas(sheet, "A19");

	        validateCellValue(sheet, "A24", "Areas of Focus");
	        validateAreas(sheet, "A24");
	    }

	    private static void validateSkillClusterSheet(Sheet sheet) {
	        System.out.println("Validating Skill Cluster sheet...");

	        validateCellValue(sheet, "A2", "Features");

	        validateCellValue(sheet, "B2", "Status");

	        validateCellValue(sheet, "C2", "Weightage");

	        validateCellValue(sheet, "D2", "Score");

	        validateCellValue(sheet, "E2", "Topicwise %");

	        validateScoreColumn(sheet, "D");
	        validateTopicwisePercentageColumn(sheet, "E");
	    }
	    
	



	    private static void validateCellValue(Sheet sheet, String cellAddress, String expectedValue) {
	        CellReference cellRef = new CellReference(cellAddress);
	        Row row = sheet.getRow(cellRef.getRow());
	        Cell cell = row.getCell(cellRef.getCol());
	        String cellValue = getCellValueAsString(cell);

	        if (!cellValue.equalsIgnoreCase(expectedValue)) {
	            System.out.println("Invalid value for " + cellAddress + ": " + cellValue);
	        }
	    }

	    private static void validateEmployeeId(Sheet sheet, String cellAddress) {
	        CellReference cellRef = new CellReference(cellAddress);
	        Row row = sheet.getRow(cellRef.getRow());
	        Cell cell = row.getCell(cellRef.getCol());

	        if (cell.getCellType() == CellType.NUMERIC) {
	            // Additional validation for Employee ID (e.g., numeric format)
	            double numericValue = cell.getNumericCellValue();
	            int employeeId = (int) numericValue;

	            if (String.valueOf(employeeId).length() != 7) {
	            	JOptionPane.showMessageDialog(null, "Valid Employee ID format: " + employeeId);
//	                System.out.println("Invalid Employee ID format: " + employeeId);
	            } else {
	            	log.info("{}",employeeId);
	            	 //AssessmentReport assessmentReport = new AssessmentReport();
	            	 log.info("{}",assessmentReport);
	                 assessmentReport.setAssociateId(employeeId);
	                 log.info("{}",assessmentReport);
	                 //repository.save(assessmentReport);
	            }
	        } else {
	            System.out.println("Invalid Employee ID cell format.");
	        }
	    }


	    private static void validateEmail(Sheet sheet, String cellAddress) {
	        CellReference cellRef = new CellReference(cellAddress);
	        Row row = sheet.getRow(cellRef.getRow());
	        Cell cell = row.getCell(cellRef.getCol());

	        if (cell.getCellType() == CellType.STRING) {
	            // Additional validation for Email format
	            String email = cell.getStringCellValue().trim();
	            if (!isValidEmail(email)) {
	            	log.info("InValid Email format: {}", email);
//	            	
	            }
	         else {
	        	assessmentReport.setAssociateEmail(cell.getStringCellValue());
	        	log.info("{}",assessmentReport);
	        	
	            //System.out.println("Invalid Email cell format.");
	        }}
	    }
	        
	        
	   
	    private static void validateDate(Sheet sheet, String cellAddress) {
	        CellReference cellRef = new CellReference(cellAddress);
	        Row row = sheet.getRow(cellRef.getRow());
	        Cell cell = row.getCell(cellRef.getCol());

	        if (cell.getCellType() == CellType.STRING) {
	            String dateString = cell.getStringCellValue().trim();
	            if (isValidDate(dateString)) {
	                try {
	                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	                    Date utilDate = dateFormat.parse(dateString);
	                    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	                    log.info("Valid Date format: {}", sqlDate);
	                    assessmentReport.setOtjReceivedDate(sqlDate);
	                    log.info("{}", assessmentReport);
	                } catch (ParseException e) {
	                    System.out.println("Error parsing date: " + dateString);
	                }
	            }
	        }
	    }


	    private static void validateAreas(Sheet sheet, String cellAddress) {
	        
	        CellReference cellRef = new CellReference(cellAddress);
	        Row row = sheet.getRow(cellRef.getRow());
	        Cell cell = row.getCell(cellRef.getCol());

	        if (cell.getCellType() == CellType.STRING) {

	            log.info("{}", cell.getCellType());
	            log.info("Valid Areas cell format");
//	            
//	            String associateName = cell.getStringCellValue().trim();
//	            assessmentReport.setAssociateName(associateName);
	            
	            log.info("{}", assessmentReport);
	     } else {
	            System.out.println("Invalid Areas cell format.");
	        }
	    }
	    private static void validateOjtName(Sheet sheet, String cellAddress) {
	        CellReference cellRef = new CellReference(cellAddress);
	        Row row = sheet.getRow(cellRef.getRow());
	        Cell cell = row.getCell(cellRef.getCol());

	        if (cell.getCellType() == CellType.STRING) {
	            String ojtName = cell.getStringCellValue().trim();
	            assessmentReport.setOtjName(ojtName);
	            log.info("{}", assessmentReport);
	        } else {
	            System.out.println("Invalid OJT Name cell format.");
	        }
	    }
	    

	    private static void validateScoreColumn(Sheet sheet, String columnLetter) {
	        
	        int columnIndex = CellReference.convertColStringToIndex(columnLetter);

	        for (Row row : sheet) {
	            Cell cell = row.getCell(columnIndex);
	            if (cell != null && cell.getCellType() == CellType.NUMERIC) {
	                double score = cell.getNumericCellValue();
	                if (score < 0 || score > 100) {
	                    System.out.println("Invalid Score value: " + score);
	                }
	            }
	        }
	    }

	    private static void validateTopicwisePercentageColumn(Sheet sheet, String columnLetter) {
	        
	        int columnIndex = CellReference.convertColStringToIndex(columnLetter);

	        for (Row row : sheet) {
	            Cell cell = row.getCell(columnIndex);
	            if (cell != null && cell.getCellType() == CellType.NUMERIC) {
	                double percentage = cell.getNumericCellValue();
	                if (percentage < 0 || percentage > 100) {
	                    System.out.println("Invalid Topicwise % value: " + percentage);
	                }
	                int decimalPlaces = 2;
	                String[] parts = String.valueOf(percentage).split("\\.");
	                if (parts.length > 1 && parts[1].length() > decimalPlaces) {
	                    System.out.println("Invalid Topicwise % value: Too many decimal places");
	                }
	            }
	        }
	    }

	    private static String getCellValueAsString(Cell cell) {
	        if (cell == null) {
	            return "";
	        }

	        switch (cell.getCellType()) {
	            case STRING:
	                return cell.getStringCellValue().trim();
	            case NUMERIC:
	                return String.valueOf(cell.getNumericCellValue());
	            default:
	                return "";
	        }
	    }

	    
	        public static boolean isValidEmail(String email) {
	            
	            String emailRegex = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";

	            
	            boolean isValid = Pattern.matches(emailRegex, email) && !email.startsWith(".") && !email.endsWith(".") &&
	                    !email.contains("..") && !email.contains(".@") && !email.contains("@.");

	            
	            isValid = isValid && isValidDomain(email);

	            return isValid;
	        }

	        private static boolean isValidDomain(String email) {
	            
	            String[] validDomainExtensions = {".com", ".net", ".org", ".gov", ".edu", ".in", ".us"}; // Add more valid domain extensions as needed

	            int atIndex = email.lastIndexOf('@');
	            if (atIndex != -1) {
	                String domain = email.substring(atIndex + 1);
	                for (String extension : validDomainExtensions) {
	                    if (domain.endsWith(extension)) {
	                        return true;
	                    }
	                }
	            }

	            System.out.println("Invalid domain in email: " + email);
	            return false;
	        }

	        // Add more validation methods as needed
	    


	    private static boolean isValidDate(String date) {
	        
	        SimpleDateFormat dateFormat = new SimpleDateFormat("DD-mm-yyyy");
	        dateFormat.setLenient(false);

	        try {
	            
	            Date parsedDate = dateFormat.parse(date);
	            // Check if the parsed date is the same as the original date string
	            return dateFormat.format(parsedDate).equals(date);
	        } catch (ParseException e) {
	            return false;
	        }
	    }
	    private static void setAssociateId(int employeeId) {
	        
			assessmentReport.setAssociateId(employeeId);
	    }

	    private static void setAssociateEmail(String email) {
	        assessmentReport.setAssociateEmail(email);
	    }
	    private static void setAssociateName(String associate_name) {
	    	assessmentReport.setAssociateName(associate_name);
	    }
	    
	   

	}