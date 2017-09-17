package testdriven;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

public class TestTemplate {
	private Template template;

	@Before
	public void setUp() {
		template = new Template("${one}, ${two}, ${three}");
		template.set("one", "1");
		template.set("two", "2");
		template.set("three", "3");
	}

	@Test
	public void multipleVariables() {
		assertTemplateEvaluatesTo("1, 2, 3");
	}

	@Test
	public void unknownVariablesAreIgnored() {
		template.set("donotexist", "whatever");
		assertTemplateEvaluatesTo("1, 2, 3");
	}

	@Test
	public void variablesGetProcessedJustOnce() {
		template.set("one", "${one}");
		template.set("two", "${two}");
		template.set("three", "${three}");
		assertTemplateEvaluatesTo("${one}, ${two}, ${three}");
	}

	@Test
	public void missingValueRaisesException() throws Exception {
		try {
			new Template("${foo}").evaluate();
			fail("evaluate() should throw an exception if a variable was left without a value!");
		} catch (MissingValueException expected) {
			assertEquals("No value for ${foo}", expected.getMessage());
		}
	}

	private void assertTemplateEvaluatesTo(String expected) {
		assertEquals(expected, template.evaluate());
	}
}
