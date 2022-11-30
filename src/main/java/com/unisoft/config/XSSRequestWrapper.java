package com.unisoft.config;

import org.apache.commons.lang.StringEscapeUtils;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Arrays;
import java.util.List;

public class XSSRequestWrapper extends HttpServletRequestWrapper {

    public XSSRequestWrapper(HttpServletRequest servletRequest) {
        super(servletRequest);
    }

    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);

        if (values == null) {
            return null;
        }

        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = stripXSS(values[i]);
        }

        return encodedValues;
    }

    @Override
    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);

        return stripXSS(value);
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        return stripXSS(value);
    }

    public static String sanitizeHTML(String untrustedHTML) {
        List<String> allowedElements = Arrays.asList("a", "label", "h1", "h2", "h3", "h4", "h5", "h6",
                "p", "i", "b", "u", "strong", "em", "small", "big", "pre", "code",
                "cite", "samp", "sub", "sup", "strike", "center", "blockquote",
                "hr", "br", "col", "font", "span", "div", "img",
                "ul", "ol", "li", "dd", "dt", "dl", "tbody", "thead", "tfoot",
                "table", "td", "th", "tr", "colgroup", "fieldset", "legend"
        );
        PolicyFactory policy = new HtmlPolicyBuilder()
                .allowAttributes("class").onElements(allowedElements.toString())
                .allowAttributes("style").onElements(allowedElements.toString())
                .allowStandardUrlProtocols()
                .allowElements(
                        allowedElements.toString()
                ).toFactory();

        return policy.sanitize(untrustedHTML);
    }

    private String stripXSS(String value) {
//        if(value != null)
//            System.out.println("escapeHTML work successfully and escapeHTML value is : " + StringEscapeUtils.escapeHtml(value));
        return StringEscapeUtils.escapeHtml(value);
    }
}
