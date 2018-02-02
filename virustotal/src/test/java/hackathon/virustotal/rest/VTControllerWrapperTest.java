package hackathon.virustotal.rest;

import static org.junit.Assert.fail;

import org.junit.Ignore;
import org.junit.Test;

import hackathon.virustotal.exception.VTException;

@Ignore
public class VTControllerWrapperTest {

//	private IVTController subjectUnderTest = new VTControllerWrapper();
	private IVTController subjectUnderTest = new VTController();

	@Test
	public void testReport_cleanFile() {
		try {
			subjectUnderTest.getReport("45ef5c5bd4b89c7d1a0126ecb37cd49f8d7f176458c1084149315bfc3af0eccd-1517467044");
		} catch (VTException e) {
			fail();
		}
	}

	@Test
	public void testReport_corruptFile() {

		try {
			subjectUnderTest.getReport("275a021bbfb6489e54d471899f7db9d1663fc695ec2fe2a2c4538aabf651fd0f");
		} catch (VTException e) {
			fail();
		}
	}
}
