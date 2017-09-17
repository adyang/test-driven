package testdriven;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class TestTemplatePerformance {
	private Template template;

	@Before
	public void setUp() throws Exception {
		buildTemplate();
		populateTemplate();
	}

	private void buildTemplate() {
		StringBuilder text = new StringBuilder(50000);
		for (int i = 0, var = 1; i < 100; i++) {
			text.append(" template ");
			if (i % (100 / 20) == 0) {
				text.append("${var").append(var).append("}");
				var++;
			}
		}
		template = new Template(text.toString());
	}

	private void populateTemplate() {
		for (int var = 1; var <= 20; var++) {
			template.set("var" + var, "value of var" + var);
		}
	}

	@Test
	public void templateWith100WordsAnd20Variables() {
		long expected = 200L;
		long time = System.currentTimeMillis();
		template.evaluate();
		time = System.currentTimeMillis() - time;
		assertTrue("Rendering the template took " + time + "ms while the target was " + expected + "ms",
				time <= expected);
	}
}
