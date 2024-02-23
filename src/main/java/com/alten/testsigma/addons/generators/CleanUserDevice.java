package com.alten.testsigma.addons.generators;
        import com.testsigma.sdk.TestData;
        import com.testsigma.sdk.TestDataFunction;
        import com.testsigma.sdk.annotation.TestDataFunctionParameter;
        import lombok.Data;
        import okhttp3.*;
        import org.json.JSONObject;
        import javax.net.ssl.SSLContext;
        import javax.net.ssl.TrustManager;
        import javax.net.ssl.X509TrustManager;
        import java.io.IOException;
        import java.security.cert.CertificateException;
        import java.security.cert.X509Certificate;
@Data
@com.testsigma.sdk.annotation.TestDataFunction(displayName = "Nexipay clean device list of user",
        description = "Wipe the list of saved devices")
public class CleanUserDevice extends TestDataFunction {
    @TestDataFunctionParameter (reference = "user")
    private com.testsigma.sdk.TestDataParameter user;

    @Override
    public com.testsigma.sdk.TestData generate() throws Exception {
        String userName = user.getValue().toString();
        String[] singleUser = userName.split(",");
        String token = geToken();

        String responseDelete = "";
        for(int i = 0; i < singleUser.length; i++){
            responseDelete = deleteDevice(token, singleUser[i]);
        }

        System.out.println(responseDelete);
        String risultato = "passed";
        TestData testData = new TestData(risultato);
        return testData;

    }
        private String geToken(){
        OkHttpClient newClient = createCustomOkHttpClient();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType,"grant_type=client_credentials&client_id=21538efa-f030-487c-8172-3c41ef95021f&client_secret=4d4ff39c-6e66-450a-a697-9fc5452a4765");
        Request request = new Request.Builder()
                .url("https://stgsecgw.private.nexicloud.it:8443/oauth/v2/token")
                .method("POST", body)
                .addHeader("Content-Type","application/x-www-form-urlencoded")
                .build();
        try {
            Response responseBody = newClient.newCall(request).execute();
            String response = responseBody.body().string();
            JSONObject Jresponse = new JSONObject(response);
            //System.out.println("JSON completo: " + Jresponse.toString());
            String token = Jresponse.getString("access_token");

            return token;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private String deleteDevice(String token, String userName){
        String StrResponse;
        OkHttpClient client = createCustomOkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{ \n   \"mfaId\": \""+userName+"\",\n   \"organization\": \"NEXI\",\n   \"profileType\": \"NEXI\"\n }");
        Request request = new Request.Builder()
                .url("https://stgmtissuing.private.nexicloud.it/mt-services/issuing/orch/mfa/v1/deleteDevices")
                .method("POST", body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("MT_APPLICATION", "CH")
                .addHeader("MT_CHANNEL", "WEB")
                .addHeader("MT_COMPANY", "NEXI")
                .addHeader("MT_COMPANY_GROUP", "NEXI")
                .addHeader("MT_PARTNER", "NEXI")
                .addHeader("MT_REQUEST_ID", "NEXI")
                .addHeader("MT_SESSION_ID", "NEXI")
                .addHeader("MT_USER", "Test")
                .addHeader("Authorization", "Bearer " + token)
                .build();
        try {
            Response response = client.newCall(request).execute();
            StrResponse = response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return StrResponse;
    }
    //bypassare la verifica dei certificati ssl
    public static OkHttpClient createCustomOkHttpClient(){
        //creo l'istaza del trust manager personalizzato
        X509TrustManager customTrustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            }
            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            }
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
        //Crazione SSLContext inizializzando con il trust manager personalizzato
        SSLContext sslContext;
        try{
            //configurazione dell'sslContext per utilizzare il protocollo TLS (Transport Layer Security)
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{customTrustManager}, new java.security.SecureRandom());
        }catch (Exception e){
            throw new RuntimeException("Errore durante la configurazione del SSLContext.", e);
        }
        //creazione OkHttpClient.Builder e configurazione del custom trust manager
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .sslSocketFactory(sslContext.getSocketFactory(),customTrustManager)
                .hostnameVerifier((hostname, session) -> true);
        return builder.build();
    }
}