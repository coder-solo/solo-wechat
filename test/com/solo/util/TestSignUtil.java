package com.solo.util;

import org.junit.Test;

public class TestSignUtil {

	@Test
	public void testVerifySignature() {

		String signature = "";
		String timestamp = "";
		String nonce = "";
		boolean result = SignUtil.verifySignature(signature, timestamp, nonce);
		System.out.println("result:" + result);
	}
}
