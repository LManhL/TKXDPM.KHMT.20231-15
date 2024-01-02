package subsystem.vnpay;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import entity.order.Order;
import entity.payment.PaymentTransaction;
import entity.payment.RefundTransaction;
import utils.Utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import static subsystem.vnpay.ConfigVNPay.vnp_Version;

public class RefundRequestVNPay {
    PaymentTransaction paymentTransaction;

    public RefundRequestVNPay(PaymentTransaction paymentTransaction){
        this.paymentTransaction = paymentTransaction;
    }
    public String refund() throws IOException {
        long amountVNPay = paymentTransaction.getAmount() * 100L;

        String vnp_TxnRef = paymentTransaction.getTransactionNum();
        String vnp_RequestId = ConfigVNPay.getRandomNumber(8);
        String vnp_IpAddr = ConfigVNPay.getIpAddress();

        String vnp_TmnCode = ConfigVNPay.vnp_TmnCode;
        String orderInfor = "Refund";

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", ConfigVNPay.vnp_Command_refund);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amountVNPay));
        vnp_Params.put("vnp_OrderInfo", orderInfor);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        vnp_Params.put("vnp_TransactionType", "02");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_CreateBy", ConfigVNPay.website_name);
        vnp_Params.put("vnp_RequestId", vnp_RequestId);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_TransactionDate = Utils.convertDateFormat(paymentTransaction.getCreatedAt());
        vnp_Params.put("vnp_TransactionDate", vnp_TransactionDate);
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        String hash_Data= String.join("|", vnp_RequestId, vnp_Version, ConfigVNPay.vnp_Command_refund, vnp_TmnCode,
                "02", vnp_TxnRef, String.valueOf(amountVNPay), "", vnp_TransactionDate,
                ConfigVNPay.website_name, vnp_CreateDate, vnp_IpAddr, orderInfor);

        String vnp_SecureHash = ConfigVNPay.hmacSHA512(ConfigVNPay.secretKey, hash_Data.toString());

        vnp_Params.put("vnp_SecureHash", vnp_SecureHash);

        URL url = new URL(ConfigVNPay.vnp_ApiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
        Gson gson = new Gson();
        Type typeObject = new TypeToken<HashMap>() {}.getType();
        String gsonData = gson.toJson(vnp_Params, typeObject);
        wr.writeBytes(gsonData);
        wr.flush();
        wr.close();
        int responseCode = connection.getResponseCode();
        System.out.println("nSending 'POST' request to URL : " + url);
        System.out.println("Post Data : " + vnp_Params);
        System.out.println("Response Code : " + responseCode);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String output;
        StringBuffer response = new StringBuffer();
        while ((output = in.readLine()) != null) {
            response.append(output);
        }
        in.close();
        System.out.println(response.toString());
        return response.toString();
    }
}
