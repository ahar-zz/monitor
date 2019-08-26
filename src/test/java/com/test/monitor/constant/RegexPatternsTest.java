package com.test.monitor.constant;

import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.test.monitor.testdata.BasicTestData;

import org.junit.Test;

public class RegexPatternsTest {

    BasicTestData testData = new BasicTestData();

    @Test
    public void validateDomain() {
        testData.getDomains().forEach(domain -> {
            Pattern pattern = Pattern.compile(RegexPatterns.URL);
            Matcher m = pattern.matcher( domain.getUrl() );
            assertEquals(domain.isResult(), m.matches());
        });
    }
}