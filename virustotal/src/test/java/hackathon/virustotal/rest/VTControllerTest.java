package hackathon.virustotal.rest;

import static org.junit.Assert.fail;

import org.junit.Ignore;
import org.junit.Test;

import hackathon.virustotal.exception.VTException;

@Ignore
public class VTControllerTest {
	
	VTController subjectUnderTest = new VTController();
	
	@Test
	public void test_getReport_cleanFile() {
		
		try {
			subjectUnderTest.getReport("45ef5c5bd4b89c7d1a0126ecb37cd49f8d7f176458c1084149315bfc3af0eccd");
		} catch (VTException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void test_getReport_multipleFiles() {
		
		try {
			subjectUnderTest.getReport("45ef5c5bd4b89c7d1a0126ecb37cd49f8d7f176458c1084149315bfc3af0eccd,275a021bbfb6489e54d471899f7db9d1663fc695ec2fe2a2c4538aabf651fd0f");
		} catch (VTException e) {
			e.printStackTrace();
			fail();
		}
	}

}
