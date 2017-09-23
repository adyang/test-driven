package velocity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.junit.Before;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public abstract class VelocityTestCase {
	private VelocityContext context;
	protected Document document;

	@Before
	public void setUp() throws Exception {
		context = new VelocityContext();
	}

	protected String getWebRoot() {
		return "/";
	}

	protected Document getResponse() {
		return document;
	}

	protected void setAttribute(String name, Object value) {
		context.put(name, value);
	}

	protected void render(String templatePath) throws Exception {
		File templateFile = new File(this.getClass().getResource(getWebRoot() + templatePath).toURI());
		String template = readFileContent(templateFile);
		String renderedHtml = renderTemplate(template);
		this.document = parseHtml(renderedHtml);
	}

	private String readFileContent(File file) throws IOException {
		try (Reader reader = new BufferedReader(
				new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
			StringBuilder builder = new StringBuilder();
			char[] buffer = new char[8096];
			int read;
			while ((read = reader.read(buffer)) > 0) {
				builder.append(buffer, 0, read);
			}
			return builder.toString();
		}
	}

	private String renderTemplate(String template) {
		VelocityEngine engine = new VelocityEngine();
		engine.init();
		StringWriter writer = new StringWriter();
		engine.evaluate(context, writer, "test", template);
		return writer.toString();
	}

	private Document parseHtml(String renderedHtml) throws Exception {
		return parseAsXml(renderedHtml);
	}

	private Document parseAsXml(String xml) throws Exception {
		DocumentBuilder b = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		return b.parse(new ByteArrayInputStream(xml.getBytes()));
	}

	protected void assertFormFieldPresent(String name) throws Exception {
		assertNodeExists(xPathForField(name));
	}

	protected void assertFormSubmitButtonPresent(String name) throws Exception {
		assertNodeExists("//form//input[@type='submit' and @name='" + name + "']");
	}

	protected void assertFormFieldValue(String name, String expectedValue) throws Exception {
		String xPath = xPathForField(name);
		assertNodeExists(xPath);
		String actual = getString(xPath + "/@value");
		assertEquals(expectedValue, actual);
	}

	private String xPathForField(String name) {
		return "//form//input[@name='" + name + "']";
	}

	private void assertNodeExists(String xPath) throws XPathExpressionException {
		assertNotNull("Node doesn't exist: " + xPath, getNode(xPath));
	}

	private Node getNode(String xPath) throws XPathExpressionException {
		return (Node) evaluate(xPath, XPathConstants.NODE);
	}

	private String getString(String xPath) throws XPathExpressionException {
		return (String) evaluate(xPath, XPathConstants.STRING);
	}

	private Object evaluate(String xPath, QName type) throws XPathExpressionException {
		XPath engine = XPathFactory.newInstance().newXPath();
		return engine.evaluate(xPath, getResponse(), type);
	}
}
