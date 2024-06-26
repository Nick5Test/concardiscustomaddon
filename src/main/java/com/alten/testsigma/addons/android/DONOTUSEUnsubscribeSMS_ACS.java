
package com.alten.testsigma.addons.android;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testsigma.sdk.*;

import com.testsigma.sdk.annotation.Action;
import com.testsigma.sdk.annotation.TestData;
import com.testsigma.sdk.annotation.RunTimeData;
import io.appium.java_client.android.AndroidDriver;
import lombok.Data;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.NoSuchElementException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
@Deprecated

@Data
@Action(actionText = "DONOTUSE_Unsubscribe ACS and SMS inserting: pan, phoneNumber, panAlias", applicationType = ApplicationType.ANDROID)
public class DONOTUSEUnsubscribeSMS_ACS extends AndroidAction {
    @TestData(reference = "pan")
    private com.testsigma.sdk.TestData pan;

    @TestData (reference = "phoneNumber")
    private com.testsigma.sdk.TestData phoneNumber;

    @TestData (reference = "panAlias")
    private com.testsigma.sdk.TestData panAliasList;

    @RunTimeData
    private com.testsigma.sdk.RunTimeData runTimeData;


    @Override
    public Result execute() throws NoSuchElementException {

        logger.info("Initiating execution");
        logger.info("PAN = " + pan.getValue().toString());
        logger.info("Phone Number = " + phoneNumber.getValue().toString());
        logger.info("Pan Alias = "+panAliasList.getValue().toString());

        com.testsigma.sdk.Result result;
        AndroidDriver androidDriver = (AndroidDriver)this.driver;

        String token = geToken();
        String responseSMS = unsubscribeSMS(token,pan.getValue().toString(),phoneNumber.getValue().toString());
        String responseACS = unsubscribeACS(token,pan.getValue().toString(),phoneNumber.getValue().toString());
        String unlockNumber = UnlockNumber(panAliasList.getValue().toString());

        System.out.println(unlockNumber);
        System.out.println(responseSMS);
        System.out.println(responseACS);

        if(responseACS.equals("SUCCESS")  && responseSMS.equals("SUCCESS") && unlockNumber.equals("SUCCESS")){
            setSuccessMessage("The requests were successfully processed");
            System.out.println("The requests were successfully processed");
            result = Result.SUCCESS;

        }else{
            setErrorMessage("Unsubscribe ACS request:\n"+responseACS +"\n\n"+"Unsubscribe SMS request:\n"+responseSMS+"\n\n"+"Remove pan alias request:\n"+unlockNumber);
            System.out.println("Unsubscribe ACS request:\n"+responseACS +"\n\n"+"Unsubscribe SMS request:\n"+responseSMS+"\n\n"+"Remove pan alias request:\n"+unlockNumber);
            result = Result.FAILED;
        }

        return result;

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
    public String unsubscribeSMS(String token, String pan,String phoneNumber){
        String StrResponse;
        String ris="";
        OkHttpClient client = createCustomOkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\n    \"cardId\": {\n        \"type\": \"PAN\",\n        " +
                "\"value\": "+pan+"\n    },\n    \"mobilePhone\": {\n        \"mobilePhoneNumber\": "+phoneNumber+",\n        " +
                "\"countryAlphaTwoCode\": \"IT\",\n        \"mobilePhonePrefix\": \"+39\"\n    },\n    \"updateSystem\": \"SMS\",\n    \"disablePropagation\": false\n}");
        Request request = new Request.Builder()
                .url("https://stgmtissuing.private.nexicloud.it/mt-services/issuing/orch/subscription/service/v1/unsubscribe")
                .method("POST", body)
                .addHeader("MT_APPLICATION", "CH")
                .addHeader("MT_CHANNEL", "WEB")
                .addHeader("MT_USER", "testdeutsche2@yopmail.com")
                .addHeader("MT_COMPANY", "NEXI")
                .addHeader("MT_COMPANY_GROUP", "NEXI")
                .addHeader("MT_PARTNER", "NEXI")
                .addHeader("MT_REQUEST_ID", "af2d5d01-ff2a-4b16-bc88-68ac184f59d7")
                .addHeader("MT_SESSION_ID", "58a22bb9-d4d8-4b93-a766-f4d1aa91907b")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + token)
                .build();
        try {
            Response response = client.newCall(request).execute();
            StrResponse = response.body().string();

            JSONObject jsonObject = new JSONObject(StrResponse);
            JSONObject description1 = (JSONObject) jsonObject.get("result");
            String description = description1.get("description").toString();

            if(description.contains("SUCCESS")){
                ris = "SUCCESS";
                //System.out.println("The request was successfully processed");
                logger.info("The request was successfully processed");
            }else{
                ris = "ERROR";
                //System.out.println("ERROR, The request was NOT successfully processed");
                logger.info("ERROR, The request was NOT successfully processed");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return ris;
    }

    public String unsubscribeACS(String token, String pan, String phoneNumber){
        String StrResponse;
        String ris = "";
        OkHttpClient client = createCustomOkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\r\n    \"acs\": {\r\n        \"channel\": \"web\"\r\n    },\r\n    \"cardId\": {\r\n        \"type\": \"PAN\",\r\n        " +
                "\"value\": "+pan+"\r\n    },\r\n    \"disablePropagation\": true,\r\n    \"mobilePhone\": {\r\n        \"carrier\": \"1\",\r\n        " +
                "\"mobilePhoneNumber\": "+phoneNumber+",\r\n        \"mobilePhonePrefix\": \"+39\"\r\n    },\r\n    \"updateSystem\": \"ACS\"\r\n}");
        Request request = new Request.Builder()
                .url("https://stgmtissuing.private.nexicloud.it/mt-services/issuing/orch/subscription/service/v1/unsubscribe")
                .method("POST", body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("MT_APPLICATION", "CH")
                .addHeader("MT_USER", "testdeutsche2@yopmail.com")
                .addHeader("MT_CHANNEL", "WEB")
                .addHeader("MT_COMPANY_GROUP", "NEXI")
                .addHeader("MT_COMPANY", "NEXI")
                .addHeader("MT_REQUEST_ID", "0c1b1a92-3c94-4e5b-b941-792f1cb46b22")
                .addHeader("MT_PARTNER", "NEXI")
                .addHeader("MT_SESSION_ID", "abdc738e-e5d9-4748-85d1-071997ce1dbb")
                .addHeader("Authorization", "Bearer " + token)
                .build();
        try {
            Response response = client.newCall(request).execute();
            StrResponse = response.body().string();
            //System.out.println(StrResponse);

            JSONObject jsonObject = new JSONObject(StrResponse);
            JSONObject description1 = (JSONObject) jsonObject.get("result");
            String description = description1.get("description").toString();

            if(description.contains("SUCCESS")){
                ris ="SUCCESS";
                //System.out.println("The request was successfully processed");
                logger.info("The request was successfully processed");
            }else{
                ris ="ERROR";
                //System.out.println("ERROR, The request was NOT successfully processed");
                logger.info("ERROR, The request was NOT successfully processed");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return ris;
    }
    public String UnlockNumber(String panAliasList){
        String StrResponse;
        String ris = "";
        OkHttpClient client = createCustomOkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\r\n    \"panaliases\": [\""+panAliasList+"\"]\r\n}");
        Request request = new Request.Builder()
                .url("https://intmt.private.nexicloud.it:4003/mt-services/database/gateway/v1/unlockNumbers")
                .method("POST", body)
                .addHeader("MT_APPLICATION", "CH")
                .addHeader("MT_USER", "USER")
                .addHeader("MT_CHANNEL", "WEB")
                .addHeader("MT_COMPANY_GROUP", "NEXI")
                .addHeader("MT_COMPANY", "NEXI")
                .addHeader("MT_REQUEST_ID", "672fe8b3-374e-48d8-b1a9-553c80878646")
                .addHeader("MT_PARTNER", "NEXI")
                .addHeader("MT_SESSION_ID", "4c0af0e0-7771-463d-82f7-29cede023acb")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Basic dGVzdEZhY3Rvcnk6dDM1dGY0Y3Qwcnk=")
                .addHeader("Cookie", "JSESSIONID=4F4BC13DFEDEA3415A735FDFFD52B604")
                .build();
        try {
            Response response = client.newCall(request).execute();
            StrResponse = response.body().string();

            //JSONObject jsonObject = new JSONObject(StrResponse);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode responseNode = mapper.readTree(StrResponse);
            JsonNode databaseResNode = responseNode.get("databaseResults");
            String message = databaseResNode.get(0).asText();

            if(message.contains("successfully")){
                ris="SUCCESS";

            }else{
                ris="ERROR: "+message;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ris;
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
