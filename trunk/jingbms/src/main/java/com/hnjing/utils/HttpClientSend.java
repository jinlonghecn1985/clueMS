package com.hnjing.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang3.StringUtils;


   /**
     * 无视Https证书是否正确的Java Http Client
     * 
     * 
     * @author chenwei
     *
     * @create 2017.3.23
     * @version 1.0
     */
public class HttpClientSend {


    /**
     * 忽视证书HostName
     */
    private static HostnameVerifier ignoreHostnameVerifier = new HostnameVerifier() {
        public boolean verify(String s, SSLSession sslsession) {
//            System.out.println("WARNING: Hostname is not matched for cert.");
            return true;
        }
    };


     /**
     * Ignore Certification
     */
    private static TrustManager ignoreCertificationTrustManger = new X509TrustManager() {


        private X509Certificate[] certificates;


        @Override
        public void checkClientTrusted(X509Certificate certificates[],
                String authType) throws CertificateException {
            if (this.certificates == null) {
                this.certificates = certificates;
//                System.out.println("init at checkClientTrusted");
            }


        }


        @Override
        public void checkServerTrusted(X509Certificate[] ax509certificate,
                String s) throws CertificateException {
            if (this.certificates == null) {
                this.certificates = ax509certificate;
//                System.out.println("init at checkServerTrusted");
            }


//            for (int c = 0; c < certificates.length; c++) {
//                X509Certificate cert = certificates[c];
//                System.out.println(" Server certificate " + (c + 1) + ":");
//                System.out.println("  Subject DN: " + cert.getSubjectDN());
//                System.out.println("  Signature Algorithm: "
//                        + cert.getSigAlgName());
//                System.out.println("  Valid from: " + cert.getNotBefore());
//                System.out.println("  Valid until: " + cert.getNotAfter());
//                System.out.println("  Issuer: " + cert.getIssuerDN());
//            }


        }


        @Override
        public X509Certificate[] getAcceptedIssuers() {
            // TODO Auto-generated method stub
            return null;
        }


    };


    public static String getMethod(String urlString,String token) throws UnsupportedEncodingException {


        ByteArrayOutputStream buffer = new ByteArrayOutputStream(512);
        try {


            URL url = new URL(urlString);


            /*
             * use ignore host name verifier
             */
            HttpsURLConnection.setDefaultHostnameVerifier(ignoreHostnameVerifier);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            
            if(StringUtils.isNotBlank(token)){
            	connection.setRequestProperty("Authorization", "Bearer "+token );
//            	connection.connect();
//            	// 获取所有响应头字段
//            	Map<String, List<String>> map = connection.getHeaderFields();
//            	// 遍历所有的响应头字段
//            	for (String key : map.keySet()) {
//            		System.out.println(key + "--->" + map.get(key));
//            	}
            }
            // Prepare SSL Context
            TrustManager[] tm = { ignoreCertificationTrustManger };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());


            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            connection.setSSLSocketFactory(ssf);
            
            InputStream reader = connection.getInputStream();
            byte[] bytes = new byte[512];
            int length = reader.read(bytes);

            do {
                buffer.write(bytes, 0, length);
                length = reader.read(bytes);
            } while (length > 0);


            // result.setResponseData(bytes);
//            System.out.println(new String(buffer.toByteArray(),"UTF-8"));
            reader.close();
            
            connection.disconnect();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
        }
        String repString= new String (buffer.toByteArray(),"UTF-8");
        return repString;
    }
    
    public static void main(String[] args) {
//      String urlString = "https://api.dev.daydao.com/token?grant_type=client_credentials&client_id=cfd2aa6d-1fbe-" +
//      		"463d-9919-ec5e357bdbeb&client_secret=2a5c1e1e-7d40-4d9a-892c-8a0080d43b26";
//      String output = new String(HttpClientSend.getMethod(urlString));
//      System.out.println(output);
    }
}