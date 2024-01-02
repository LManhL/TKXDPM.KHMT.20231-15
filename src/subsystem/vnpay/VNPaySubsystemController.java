package subsystem.vnpay;

import entity.order.Order;
import entity.payment.PaymentTransaction;
import entity.payment.RefundTransaction;

import java.io.IOException;
import java.util.Map;

public class VNPaySubsystemController {


	private static VNPayBoundary vnPayBoundary = new VNPayBoundary();


	public String generatePayOrderUrl(int amount, String content) throws IOException {
		return vnPayBoundary.generateURL(amount, content);
	}

	public RefundTransaction refund(PaymentTransaction paymentTransaction) throws IOException {
		return vnPayBoundary.refund(paymentTransaction);
	}

	public PaymentTransaction getPaymentTransaction(Map<String,String> response) {
		return vnPayBoundary.getPaymentTransaction(response);
	}


}
