package testdriven;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class TestPlainTextSegment {
	@Test
	public void plainTextEvaluatesAsIs() {
		Map<String, String> variables = new HashMap<>();
		String text = "abc def";
		assertEquals(text, new PlainText(text).evaluate(variables));
	}
}
