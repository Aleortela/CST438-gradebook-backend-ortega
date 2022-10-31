package com.cst438;


import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;

import com.cst438.domain.Assignment;
import com.cst438.domain.AssignmentGrade;
import com.cst438.domain.AssignmentGradeRepository;
import com.cst438.domain.AssignmentRepository;
import com.cst438.domain.Course;
import com.cst438.domain.CourseRepository;
import com.cst438.domain.Enrollment;
import com.cst438.domain.EnrollmentRepository;


@SpringBootTest
public class EndToEndAddAssignment {
	
	public static final String FIREFOX_DRIVER_FILE_LOCATION = "/Users/alecortega/Downloads/geckodriver";
	public static final String URL = "http://localhost:3000";
	public static final String TEST_USER_EMAIL = "test@csumb.edu";
	public static final String TEST_INSTRUCTOR_EMAIL = "dwisneski@csumb.edu";
	public static final int SLEEP_DURATION = 1000; // 1 second.
	public static final String TEST_ASSIGNMENT_NAME = "Test Assignment";
	public static final String TEST_COURSE_TITLE = "Test Course";
	public static final String TEST_STUDENT_NAME = "Test";
	
	@Autowired
	EnrollmentRepository enrollmentRepository;

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	AssignmentGradeRepository assignnmentGradeRepository;

	@Autowired
	AssignmentRepository assignmentRepository;
	
	@Test
	public void testAddAssignment() throws Exception{
		
		
		Course c = new Course();
		c.setCourse_id(10101);
		c.setInstructor(TEST_INSTRUCTOR_EMAIL);
		c.setSemester("Spring");
		c.setYear(2022);
		c.setTitle(TEST_COURSE_TITLE);
		courseRepository.save(c);
		
		
		
		Assignment a  = new Assignment();
		a.setAssignmentName(TEST_ASSIGNMENT_NAME);
		a.setCourse(c);
		a.setNeedsGrading(1);
		a.setDueDate(new Date(2022-10-31));
		
		System.setProperty("webdriver.gecko.driver", FIREFOX_DRIVER_FILE_LOCATION);
		WebDriver driver = new FirefoxDriver();
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(URL);
		Thread.sleep(SLEEP_DURATION);
		try {
			/*
			* locate input element for test assignment by assignment name
			* 
			* To select a radio button in a Datagrid display
			* 1.  find the elements in the assignmentName column of the data grid table.
			* 2.  locate the element with test assignment name and click the input tag.
			*/
			driver.findElement(By.xpath("//button[@id='add']")).click();
			Thread.sleep(SLEEP_DURATION);
			driver.findElement(By.xpath("//input[@name='name']")).sendKeys(a.getAssignmentName());
			driver.findElement(By.xpath("//input[@name='dueDate']")).sendKeys("2022-12-31");
			driver.findElement(By.xpath("//input[@name='courseId']")).sendKeys(Integer.toString(a.getCourse().getCourse_id()));
			driver.findElement(By.xpath("//button[@id='dialogAdd']")).click();
			Thread.sleep(SLEEP_DURATION);
			String toast_text = driver.findElement(By.cssSelector(".Toastify__toast-body div:nth-child(2)")).getText();
			String sec_text = driver.findElement(By.cssSelector(".Toastify__toast:nth-child(2) .Toastify__toast-body div:nth-child(2)")).getText();
			System.out.println("First toast text: " + toast_text);
			System.out.println("Second toast text: "+sec_text);
			Thread.sleep(SLEEP_DURATION);
			assertEquals(toast_text, "Assignment added successfully!");
			assertEquals(sec_text, "Fetch assignments successful");
			
		}finally {
			/*
			 *  clean up database so the test is repeatable.
			 */
			Assignment repoCheck = assignmentRepository.findByCourseId(a.getCourse().getCourse_id()).orElse(null);
			if(repoCheck == null) {
				System.out.println("Assignment for course does not exist!");
			}else {
				assignmentRepository.delete(repoCheck);
				courseRepository.delete(c);
				Assignment check = assignmentRepository.findByCourseId(a.getCourse().getCourse_id()).orElse(null);
				if(check == null) {
					System.out.println("Assignment deleted successfully!");
					System.out.println("Course deleted successfully! Ready for repeat testing.");
				}
			}
			Thread.sleep(SLEEP_DURATION*2);
			driver.quit();
		}
		
	}

}
