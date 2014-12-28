/*
 * Copyright (C) 2010 The MobileSecurePay Project
 * All right reserved.
 * author: shiqun.shi@alipay.com
 * 
 *  提示：如何获取安全校验码和合作身份者id
 *  1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *  2.点击“商家服务”(https://b.alipay.com/order/myorder.htm)
 *  3.点击“查询合作者身份(pid)”、“查询安全校验码(key)”
 */

package com.alipay.android.msp.demo;

//
// 请参考 Android平台安全支付服务(msp)应用开发接口(4.2 RSA算法签名)部分，并使用压缩包中的openssl RSA密钥生成工具，生成一套RSA公私钥。
// 这里签名时，只需要使用生成的RSA私钥。
// Note: 为安全起见，使用RSA私钥进行签名的操作过程，应该尽量放到商家服务器端去进行。
public final class Keys {

	//合作身份者id，以2088开头的16位纯数字
	public static final String DEFAULT_PARTNER = "2088311474028851";

	//收款支付宝账号
	public static final String DEFAULT_SELLER = "ilehuozu@163.com";

	//商户私钥，自助生成
	public static final String PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALVRpT4eSKRqGDuNkcKkqbD0Krtilo5v8gTWIOwpJK63YIvxrNEsVztqRx+aR/XEdcHyNBNbAGEIA2X7Gu1L7cmu2b7cn2MmDJTwfPGQMCybrBB8lXdBgkIBLrxMM/Jo+PjWETJPBUUk/GXqZ/AZX3icXZ8THrISBkwXe25vjTGzAgMBAAECgYBK5uRtKdN2YAGMsGnTT3RuDh+M8yggxSvkRZSqGkD2D/jJNtfePQP4HmotKu2pIDRJH0XV7RTWAJpuyXGRL3mV0iwgUIy0ggcP7dhjoJx5jiMJxwFoG1o79zDa9mgdbqSOb3F5oQWNvRpMfBnE67O/psTfbVkr5d7GVIAh1VBjuQJBANkb0VHV+++Wr8P0s0y8daqgdnqX/3YwjL9QFKupEJa7/9p8P9ZYupX5GWNxXI549q4rJrBxw3IzRY5l33RNJwcCQQDVzJR4cStjP3Big1TEwFC+54mcXZrs2yf9yfHkMufigKEBQZezCL+GRAwoMY/oNH8gaZ2ESR9jWO3JaI80RGj1AkEApg/w+3eBTLEln+z7eCZumiRCe2Lns69O+MZ4CRU36xPBj4yaB4m2rh/qm3WKJi+//1hiL3PU2vT8rv6c/IhG4QJBAKsDt4cXzwLWTckfEAFJa80oW5St8yyeqMCCdnB4n684AJGGrBdTWg/GAotsCZZN15pPoOWdr/PBwIKollPSnLkCQAkzsa4hE75YwP5XI+KXGs4c+ekM8VdghmWDI7TFGPqBd3rSfc/AhtFpZ0FSZ77di0SN4FX01uRNZxil8jdBec8=";

	public static final String PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

}
