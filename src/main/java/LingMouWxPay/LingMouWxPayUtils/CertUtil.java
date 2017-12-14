package LingMouWxPay.LingMouWxPayUtils;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;

import LingMouWxPay.LingMouWxPayconfig.LingMouWxPayConstant;

@SuppressWarnings("deprecation")
public class CertUtil {

	/**
	 * 加载证书
	 */
	public static SSLConnectionSocketFactory initCert() throws Exception {
		FileInputStream instream = null;
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		instream = new FileInputStream(new File(LingMouWxPayConstant.LINGMOUWXPAY_CERT_PATH));
		keyStore.load(instream, LingMouWxPayConstant.LINGMOUWXPAY_MCH_ID.toCharArray());
		
		if (null != instream) {
			instream.close();
		}

		SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore,LingMouWxPayConstant.LINGMOUWXPAY_MCH_ID.toCharArray()).build();
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"}, null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

		return sslsf;
	}
}
