package testdriven;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class TestTemplateParse {
	@Test
	public void emptyTemplateRendersAsEmptyString() {
		List<String> segments = parse("");
		assertSegments(segments, "");
	}

	@Test
	public void templateWithOnlyPlainText() {
		List<String> segments = parse("plain text only");
		assertSegments(segments, "plain text only");
	}

	@Test
	public void parsingMultipleVariables() {
		List<String> segments = parse("${a}:${b}:${c}");
		assertSegments(segments, "${a}", ":", "${b}", ":", "${c}");
	}

	private List<String> parse(String template) {
		return new TemplateParse().parse(template);
	}

	private void assertSegments(List<String> actual, String... expected) {
		assertEquals("Number of segments doesn't match.", expected.length, actual.size());
		assertEquals(Arrays.asList(expected), actual);
	}
}
