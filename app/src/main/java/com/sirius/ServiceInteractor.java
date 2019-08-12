package com.sirius;

import android.support.annotation.NonNull;

import java.security.cert.CertificateException;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class ServiceInteractor {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String SSL = "SSL";
    public static OkHttpClient client = new OkHttpClient();

    public static Call get(String mUrl, Callback callback) {
        Request request = new Request.Builder()
                .url(mUrl)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }

    public static Call post(String url, String json, Callback callback) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }

    @NonNull
    private static TrustManager[] getTrustManagers() {
        return new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        try {
                            chain[0].checkValidity();
                        } catch (Exception e) {
                            throw new CertificateException("Certificate not valid or trusted.");
                        }
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        try {
                            chain[0].checkValidity();
                        } catch (Exception e) {
                            throw new CertificateException("Certificate not valid or trusted.");
                        }
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                }
        };
    }

    public static void post(String s, Callback callback) {
    }

    /**
     * With the CertificatePinner we will be able to do the handshake using the cerficate.
     *
     * @return CertificatePinner
     */
//    private static CertificatePinner getCertificatePinner() {
//        String[] sha256 = {"sha256/FzqX/aKZ+1kVDNt3okBsaDkEc/ii9SmrZ9WXYF6x1tg"};
//        CertificatePinner.Builder certificatePinner = new CertificatePinner.Builder();
//        try {
////            certificatePinner.add(new URL(BethmannServiceUtil.getBaseUrl()).getHost(), sha256);
//            certificatePinner.add(new URL(get().request()));
//        } catch (MalformedURLException e) {
//         Log.d(TAG, "MalformedURLException e");
//        }
//        return certificatePinner.build();
//    }
}

