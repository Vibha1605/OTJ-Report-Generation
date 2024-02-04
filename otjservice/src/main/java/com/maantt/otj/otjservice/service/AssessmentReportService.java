package com.maantt.otj.otjservice.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.regex.Pattern;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

//import com.example.otj.OtjApplication;
import com.maantt.otj.otjservice.model.AssessmentReport;
import com.maantt.otj.otjservice.model.ExcelUploadResponse;
import com.maantt.otj.otjservice.model.SkillCluster;
import com.maantt.otj.otjservice.repository.AssessmentReportRepository;

@Service
@Transactional
public class AssessmentReportService {

	private final AssessmentReportRepository repository;

	private static final Logger log = LoggerFactory.getLogger(AssessmentReportRepository.class);
	private static final String FILE_NAME = "C://Users//vibha srinivasan//Downloads//excel.xlsx";

	@Autowired
	public AssessmentReportService(AssessmentReportRepository repository) {
		this.repository = repository;

	}

	public List<AssessmentReport> listAllReport() {
		return repository.findAll();
	}

	public void save(AssessmentReport assessmentReport) {
		repository.save(assessmentReport);
	}

	public ExcelUploadResponse validateExcelAndSave(MultipartFile multipartFile)
			throws IllegalStateException, IOException {
//		
		byte[] bytes = multipartFile.getBytes();
		InputStream is = new ByteArrayInputStream(bytes);
		ExcelUploadResponse response = new ExcelUploadResponse();
		try (Workbook workbook = new XSSFWorkbook(is)) {

			List<String> errors = new ArrayList<>();
			AssessmentReport report = new AssessmentReport();

			Sheet reportSheet = workbook.getSheetAt(0);
			validateReportSheet(reportSheet, errors);

			Sheet skillClusterSheet = workbook.getSheetAt(2);
			validateSkillClusterSheet(skillClusterSheet, errors);

			if (errors.size() > 0) {
				for (String error : errors) {
					log.error(error);
				}
				response.setStatus("error");
				response.setErrors(errors);
			}

			else {
				AssessmentReport assessmentReport = generateReport(reportSheet, skillClusterSheet);
				repository.save(generateReport(reportSheet, skillClusterSheet));
				response.setAssessmentReport(report);
				response.setStatus("success");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	private AssessmentReport generateReport(Sheet reportSheet, Sheet skillClusterSheet) {
		AssessmentReport assessmentReport = new AssessmentReport();

		Row reportRow = reportSheet.getRow(0);
		assessmentReport.setAssociateId((int) reportSheet.getRow(5).getCell(0).getNumericCellValue());
		log.info("{}", (int) reportSheet.getRow(5).getCell(0).getNumericCellValue());

		assessmentReport.setAssociateName(reportRow.getCell(1).getStringCellValue());

		assessmentReport.setAssociateEmail(reportSheet.getRow(5).getCell(4).getStringCellValue());

		log.info("{}", reportSheet.getRow(5).getCell(4).getStringCellValue());
		
		assessmentReport.setOtjName(reportSheet.getRow(6).getCell(4).getStringCellValue());
		
		assessmentReport.setOtjCode(reportRow.getCell(6).getStringCellValue());
		
		assessmentReport.setStatus(reportSheet.getRow(9).getCell(0).getStringCellValue());
		
		assessmentReport.setScore((int)skillClusterSheet.getRow(6).getCell(3).getNumericCellValue());
		
//		assessmentReport.setOtjReceivedDate((java.sql.Date) reportSheet.getRow(5).getCell(9).getDateCellValue());
		
		assessmentReport.setOtjReceivedDate(getJavaSqlDate(reportSheet.getRow(5).getCell(9)));

		int assessmentReportId = repository.save(assessmentReport).getId();
		List<SkillCluster> skillClusters = new ArrayList<>();
		for (Row row : skillClusterSheet) {
			if (row.getRowNum() > 1) {
				SkillCluster skillCluster = new SkillCluster();
				 skillCluster.setAssessmentReport(assessmentReport);


//				log.info("{}", assessmentReport.getId());
				skillCluster.setFeatures(row.getCell(0).getStringCellValue());
				log.info("{}", row.getCell(0).getStringCellValue());

				skillClusters.add(skillCluster);
			}
		}

		assessmentReport.setSkillClusters(skillClusters);

		return assessmentReport;
	}

	private void validateReportSheet(Sheet sheet, List<String> errors) {
		System.out.println("Validating On the Job Training Assessment Report sheet...");

		validateCellValue(sheet, "A2", "On the Job Training Assessment Report", errors);

		validateCellValue(sheet, "A4", "Employee ID", errors);
		validateEmployeeId(sheet, "A6", errors);

		validateCellValue(sheet, "E4", "Employee Name", errors);

		validateCellValue(sheet, "J4", "Assessment Date", errors);

		validateCellValue(sheet, "A7", "OJT Name", errors);

		validateOjtName(sheet, "E7", errors);

		validateEmail(sheet, "E6", errors);

		validateDate(sheet, "J6", errors);

		validateCellValue(sheet, "A13", "Areas of Excellence", errors);
		validateAreas(sheet, "A14", errors);

		validateCellValue(sheet, "A18", "Areas of Improvement", errors);
		validateAreas(sheet, "A19", errors);

		validateCellValue(sheet, "A24", "Areas of Focus", errors);
		validateAreas(sheet, "A24", errors);
	}

	private void validateSkillClusterSheet(Sheet sheet, List<String> errors) {
		System.out.println("Validating Skill Cluster sheet...");

		validateCellValue(sheet, "A2", "Features", errors);

		validateCellValue(sheet, "B2", "Status", errors);

		validateCellValue(sheet, "C2", "Weightage", errors);

		validateCellValue(sheet, "D2", "Score", errors);

		validateCellValue(sheet, "E2", "Topicwise %", errors);

		validateScoreColumn(sheet, "D", errors);

		validateTopicwisePercentageColumn(sheet, "E", errors);
	}

	private void validateCellValue(Sheet sheet, String cellAddress, String expectedValue, List<String> errors) {
		CellReference cellRef = new CellReference(cellAddress);
		Row row = sheet.getRow(cellRef.getRow());
		Cell cell = row.getCell(cellRef.getCol());
		String cellValue = getCellValueAsString(cell);

		if (!cellValue.equalsIgnoreCase(expectedValue)) {
			errors.add("Expected" + expectedValue + "in cell" + cellAddress + "but it is " + cellValue);

		}
	}

	private void validateEmployeeId(Sheet sheet, String cellAddress, List<String> errors) {
		CellReference cellRef = new CellReference(cellAddress);
		Row row = sheet.getRow(cellRef.getRow());
		Cell cell = row.getCell(cellRef.getCol());

		if (cell.getCellType() == CellType.NUMERIC) {
			// Additional validation for Employee ID (e.g., numeric format)
			double numericValue = cell.getNumericCellValue();
			int employeeId = (int) numericValue;

			if (String.valueOf(employeeId).length() != 7) {
				log.error("Valid Employee ID format: {}", employeeId);

			} else {
				log.info("{}", employeeId);
				
			}
		} else {

			errors.add("Invalid Employee ID cell format");
		}
	}

	private void validateEmail(Sheet sheet, String cellAddress, List<String> errors) {
		CellReference cellRef = new CellReference(cellAddress);
		Row row = sheet.getRow(cellRef.getRow());
		Cell cell = row.getCell(cellRef.getCol());

		if (cell.getCellType() == CellType.STRING) {
			// Additional validation for Email format
			String email = cell.getStringCellValue().trim();
			if (!isValidEmail(email)) {
				log.info("InValid Email format: {}", email);
				errors.add("Invalid Email format");
			} else {

			}
		}
	}

	private void validateDate(Sheet sheet, String cellAddress, List<String> errors) {
		CellReference cellRef = new CellReference(cellAddress);
		Row row = sheet.getRow(cellRef.getRow());
		Cell cell = row.getCell(cellRef.getCol());

		if (cell.getCellType() == CellType.STRING) {
			String dateString = cell.getStringCellValue().trim();
			if (isValidDate(dateString)) {
				try {
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
					Date utilDate = (Date) dateFormat.parse(dateString);
					java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
					log.info("Valid Date format: {}", sqlDate);

				} catch (ParseException e) {
					System.out.println("Error parsing date: " + dateString);
					errors.add("Invalid Date cell format");
				}
			}
		}
	}

	private void validateAreas(Sheet sheet, String cellAddress, List<String> errors) {

		CellReference cellRef = new CellReference(cellAddress);
		Row row = sheet.getRow(cellRef.getRow());
		Cell cell = row.getCell(cellRef.getCol());

		if (cell.getCellType() == CellType.STRING) {

			log.info("{}", cell.getCellType());
			log.info("Valid Areas cell format");

		} else {

			errors.add("Invalid Areas cell format");
		}
	}

	private void validateOjtName(Sheet sheet, String cellAddress, List<String> errors) {
		CellReference cellRef = new CellReference(cellAddress);
		Row row = sheet.getRow(cellRef.getRow());
		Cell cell = row.getCell(cellRef.getCol());

		if (cell.getCellType() == CellType.STRING) {
			String ojtName = cell.getStringCellValue().trim();

		} else {

			errors.add("Invalid OTJ Name cell format");
		}
	}

	private void validateScoreColumn(Sheet sheet, String columnLetter, List<String> errors) {

		int columnIndex = CellReference.convertColStringToIndex(columnLetter);

		for (Row row : sheet) {
			Cell cell = row.getCell(columnIndex);
			if (cell != null && cell.getCellType() == CellType.NUMERIC) {
				double numericValue = cell.getNumericCellValue();
				int score = (int) numericValue;
				log.info("{}",score);
				if (score < 0 || score > 100) {
					errors.add("Invalid Score value: " + score);
				}
				
			}

		}
	}

	private void validateTopicwisePercentageColumn(Sheet sheet, String columnLetter, List<String> errors) {

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
					errors.add("Invalid Topicwise % value: Too many decimal places");
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

		boolean isValid = Pattern.matches(emailRegex, email) && !email.startsWith(".") && !email.endsWith(".")
				&& !email.contains("..") && !email.contains(".@") && !email.contains("@.");

		isValid = isValid && isValidDomain(email);

		return isValid;
	}

	private static boolean isValidDomain(String email) {

		String[] validDomainExtensions = { ".com", ".net", ".org", ".gov", ".edu", ".in", ".us" };  

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

	
	private static boolean isValidDate(String date) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		dateFormat.setLenient(false);

		try {

			Date parsedDate = (Date) dateFormat.parse(date);
			
			return dateFormat.format(parsedDate).equals(date);
		} catch (ParseException e) {
			return false;
		}
	}
	private java.sql.Date getJavaSqlDate(Cell cell) {
	    java.util.Date utilDate = cell.getDateCellValue();
	    return new java.sql.Date(utilDate.getTime());
	}

}