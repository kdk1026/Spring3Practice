package kr.co.test;

import org.junit.Assert;
import org.junit.Test;

import com.nhncorp.lucy.security.xss.XssPreventer;

public class TestXssPreventer {

	@Test
	public void test() {

		// lucy-xss-servlet 유틸

		String dirty = "\"><script>alert('xss');</script>";
	    String clean = XssPreventer.escape(dirty);

	    System.out.println( clean );
	    Assert.assertEquals(dirty, XssPreventer.unescape(clean));

	}
}
