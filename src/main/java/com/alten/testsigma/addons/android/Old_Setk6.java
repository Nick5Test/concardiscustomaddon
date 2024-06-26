
package com.alten.testsigma.addons.android;

        import com.fasterxml.jackson.databind.JsonNode;
        import com.fasterxml.jackson.databind.ObjectMapper;
        import com.testsigma.sdk.AndroidAction;
        import com.testsigma.sdk.ApplicationType;
        import com.testsigma.sdk.annotation.Action;
        import com.testsigma.sdk.annotation.RunTimeData;
        import com.testsigma.sdk.annotation.TestData;
        import lombok.Data;
        import okhttp3.*;
        import org.json.JSONObject;
        import org.openqa.selenium.NoSuchElementException;

        import javax.net.ssl.SSLContext;
        import javax.net.ssl.TrustManager;
        import javax.net.ssl.X509TrustManager;
        import java.io.IOException;
        import java.security.cert.CertificateException;
        import java.security.cert.X509Certificate;



@Data
@Action(actionText = "Old_Set Key6 with Pan and Cf ", applicationType = ApplicationType.ANDROID)
public class Old_Setk6 extends AndroidAction {

    @TestData(reference = "Pan")
    private com.testsigma.sdk.TestData pan;
    @TestData(reference = "Key6")
    private com.testsigma.sdk.TestData key6;
    @TestData(reference = "Cf")
    private com.testsigma.sdk.TestData cf;

    @RunTimeData
    private com.testsigma.sdk.RunTimeData runTimeData;

    @Override
    public com.testsigma.sdk.Result execute() throws NoSuchElementException {
        //Your Awesome code starts here
        logger.info("Initiating execution");
        logger.info("PAN = " + pan.getValue().toString());
        logger.info("KEY6 = " + key6.getValue().toString());
        logger.info("CF = " + cf.getValue().toString());
        String token = geToken();

        String responseSetkey6 = "";


         responseSetkey6 = setKey6(token,pan.getValue().toString(),cf.getValue().toString(),key6.getValue().toString());

        System.out.println(responseSetkey6);

        com.testsigma.sdk.Result result = com.testsigma.sdk.Result.SUCCESS;
        setSuccessMessage("Utenze pulite correttamente");
        return result;
    }

    private String geToken() {
        OkHttpClient newClient = createCustomOkHttpClient();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials&client_id=21538efa-f030-487c-8172-3c41ef95021f&client_secret=4d4ff39c-6e66-450a-a697-9fc5452a4765");
        Request request = new Request.Builder()
                .url("https://stgsecgw.private.nexicloud.it:8443/oauth/v2/token")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
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

    private String setKey6(String token, String pan, String cf, String key6) {
        String StrResponse ="";
        OkHttpClient client = createCustomOkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\n  \"autoEnroll\": true,\n  \"cards\": [\n    {\n      \"cardId\": {\n        \"processor\": \"{{processor}}\",\n        \"type\": \"PAN\",\n        \"value\": "+pan+"\n      },\n      \"key6\": \""+key6+"\"\n    }\n  ],\n  \"cmpOwner\": \"NEXI\",\n  \"fiscalCode\": \""+cf+"\"\n}");
        Request request = new Request.Builder()
                .url("https://stgmtissuing.private.nexicloud.it/mt-services/issuing/k6/v1/setK6")
                .method("POST", body)
                .addHeader("MT_APPLICATION", "CH")
                .addHeader("MT_USER", "testdeutsche2@yopmail.com")
                .addHeader("MT_CHANNEL", "WEB")
                .addHeader("MT_COMPANY_GROUP", "NEXI")
                .addHeader("MT_COMPANY", "NEXI")
                .addHeader("MT_PARTNER", "NEXI")
                .addHeader("MT_REQUEST_ID", "c48ddcd7-5337-4b79-aafd-d43ee4a8abdc")
                .addHeader("MT_SESSION_ID", "49da772d-e768-47c4-9067-7958422d7c54")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + token)
                .build();
        try {
            Response response = client.newCall(request).execute();
            StrResponse = response.body().string();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode responseNode = mapper.readTree(StrResponse);
            JsonNode enrolmentResNode = responseNode.get("enrollmentResults");
            JsonNode enrollmentResultsNode = enrolmentResNode.get(0);
            JsonNode resultDetailsNode = enrollmentResultsNode.get("resultDetails");
            String description = resultDetailsNode.get("description").asText();
            if(description.equals("The request was successfully processed")){
                setSuccessMessage("The request was successfully processed");
            }else{
                System.out.println("ERROR, The request was NOT successfully processed");
                setErrorMessage("ERROR, The request was NOT successfully processed");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return StrResponse;

    }

    //bypassare la verifica dei certificati ssl
    public static OkHttpClient createCustomOkHttpClient() {
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
        try {
            //configurazione dell'sslContext per utilizzare il protocollo TLS (Transport Layer Security)
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{customTrustManager}, new java.security.SecureRandom());
        } catch (Exception e) {
            throw new RuntimeException("Errore durante la configurazione del SSLContext.", e);
        }
        //creazione OkHttpClient.Builder e configurazione del custom trust manager
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .sslSocketFactory(sslContext.getSocketFactory(), customTrustManager)
                .hostnameVerifier((hostname, session) -> true);
        return builder.build();
    }
}
