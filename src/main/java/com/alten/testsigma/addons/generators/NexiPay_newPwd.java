


package com.alten.testsigma.addons.generators;

import com.testsigma.sdk.TestData;
import com.testsigma.sdk.TestDataFunction;
import com.testsigma.sdk.annotation.TestDataFunctionParameter;
import lombok.Data;
import okhttp3.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@Data
@com.testsigma.sdk.annotation.TestDataFunction(displayName = "Generate new password for NexiPay user and newPassword",
        description = "Set new password for NexiPay")
public class NexiPay_newPwd extends TestDataFunction {

    @TestDataFunctionParameter(reference = "user")
    private com.testsigma.sdk.TestDataParameter user;
    @TestDataFunctionParameter(reference = "newPassword")
    private com.testsigma.sdk.TestDataParameter newPassword;

    @Override
    public TestData generate() throws Exception {
        // Try use of run time data
        logger.info("Initiating execution");

        // Recupera il token
        String token = getToken();
        String response;
        String uid = getUserId(user.getValue().toString(), token);


        // Genera e imposta 6 nuove password casuali
        int count = 0;
        for (int i = 0; i < 20; i++) {
            String generatedString = generateRandomString();
            response = setPassword(uid, token, generatedString);
            logger.info(response + generatedString);
            // Verifica se il valore "OK" è stato restituito
            if (response.contains("\"value\":\"OK\"")) {
                count++; // Incrementa il contatore
            }
            // Verifica se il contatore ha raggiunto il limite di 6 volte
            if (count == 6) {
                logger.info("arrivati a 6 stoppp");
                break; // Interrompi il ciclo
            }
        }

        response = setPassword(uid, token, newPassword.getValue().toString());
        // Stampa i risultati
        logger.info("Password Ripristinata!" + newPassword.getValue().toString());
        String password = newPassword.getValue().toString();

        TestData testData = new TestData(password);
        return testData;
    }

    private String getToken() {
        OkHttpClient newClient = createUnsafeOkHttpClient();

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials&" +
                "client_id=21538efa-f030-487c-8172-3c41ef95021f" +
                "&client_secret=4d4ff39c-6e66-450a-a697-9fc5452a4765");
        Request request = new Request.Builder()
                .url("https://stgsecgw.private.nexicloud.it:8443/oauth/v2/token")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();


        try {
            Response responseBody = newClient.newCall(request).execute();
            String resStr = responseBody.body().string();

            JSONObject responseJson = new JSONObject(resStr);
            String token = responseJson.getString("access_token");

            return token;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getUserId(String email, String token) {
        OkHttpClient client = createUnsafeOkHttpClient();

        String url = "https://stglbiam-ms.private.nexicloud.it:443/ext-idm/ms7/account/search?uid=" + email +
                "&type=PORTALE_TITOLARI&includeDeleted=false";

        Request request = new Request.Builder()
                .url(url)
                .get()
                .header("Authorization", "Bearer " + token)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();

            // Parse the response and extract the account_id
            JSONObject responseJson = new JSONObject(responseBody);
            String accountId = String.valueOf(responseJson.getInt("account_id"));

            logger.info("Account ID: " + accountId);

            return accountId;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String setPassword(String uid, String token, String varPassword) {
        OkHttpClient newClient = createUnsafeOkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\n     \"password\": \"" + varPassword + "\",\n    \"send_mail\": false\n}");
        String url = "https://stglbiam-ms.private.nexicloud.it:443/ext-idm/ms15/account/" + uid + "/set_password";
        Request request = new Request.Builder()
                .url(url)
                .method("PUT", body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + token)
                .build();

        String resStr;
        try {
            Response responseBody = newClient.newCall(request).execute();
            resStr = responseBody.body().string();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return resStr;
    }

    private OkHttpClient createUnsafeOkHttpClient() {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                }
        };

        SSLContext sslContext;
        try {
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException(e);
        }

        OkHttpClient.Builder newBuilder = new OkHttpClient.Builder();
        newBuilder.sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0]);
        newBuilder.hostnameVerifier((hostname, session) -> true);

        return newBuilder.build();
    }

    public static String generateRandomString() {
        String uppercaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowercaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";

        String randomString = RandomStringUtils.random(1, uppercaseLetters) +
                RandomStringUtils.random(1, lowercaseLetters) +
                RandomStringUtils.random(1, numbers) +
                RandomStringUtils.random(5, uppercaseLetters + lowercaseLetters + numbers);

        randomString = RandomStringUtils.random(8, randomString);

        return randomString;
    }
}
