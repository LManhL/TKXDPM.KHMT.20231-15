package subsystem.vnpay;

import entity.payment.PaymentTransaction;
import entity.payment.RefundTransaction;
import subsystem.IPaymentSubsystem;

import java.io.IOException;
import java.util.Map;

public class VNPaySubsystemController implements IPaymentSubsystem {


	private static VNPayBoundary vnPayBoundary = new VNPayBoundary();

	@Override
	public String generateURL(int amount, String content) throws IOException {
		return vnPayBoundary.generateURL(amount, content);
	}

	@Override
	public RefundTransaction refund(PaymentTransaction paymentTransaction) throws IOException {
		return vnPayBoundary.refund(paymentTransaction);
	}

	@Override
	public PaymentTransaction getPaymentTransaction(Map<String,String> response) {
		return vnPayBoundary.getPaymentTransaction(response);
	}


}
