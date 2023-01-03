package sample.project.jobissue;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

public class RegularExTest {

	@Test
	public void passwordTest() {
		Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$");
		String pawd = "1234!qwer";
		Matcher matcher = pattern.matcher(pawd);
		
		assertThat(matcher.matches()).isEqualTo(false);
		
		String pawd2 = "1234!Qwer";
		matcher = pattern.matcher(pawd2);
		
		assertThat(matcher.matches()).isEqualTo(true);
		
		matcher = pattern.matcher("1234");
		assertThat(matcher.matches()).isEqualTo(false);
		
	}
}
