

package validators;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;


import org.hamcrest.Matchers;

public class Validations {

	public void verifyString(String actual , String expected , String reportText) {
		try {
			assertThat(reportText , actual , Matchers.equalToIgnoringWhiteSpace(expected));
		}
		catch(NullPointerException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void verifyList(List<String> actual , List<String>expected , String reportText) {
		try {
			assertThat(reportText , actual, Matchers.containsInAnyOrder(expected.toArray(new String[expected.size()])));
		}
		catch(NullPointerException e) {
			e.printStackTrace();
		}
	}

	public void verifyInt(int actual , int expected , String reportText) {
		try {
			
			assertThat(reportText,actual ,Matchers.comparesEqualTo(expected));
		}
		catch (NullPointerException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	
}
