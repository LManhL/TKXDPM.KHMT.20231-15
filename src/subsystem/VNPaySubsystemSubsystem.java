package subsystem;

import common.exception.PaymentException;
import common.exception.UnrecognizedException;
import entity.order.Order;
import entity.payment.PaymentTransaction;
import entity.payment.RefundTransaction;
import subsystem.vnpay.VNPaySubsystemController;

import java.io.IOException;
import java.util.Map;

/***
 * The {@code InterbankSubsystem} class is used to communicate with the
 * Interbank to make transaction.
 * 
 * @author hieud
 *
 */
public class VNPaySubsystemSubsystem implements IVNPaySubsystem {

	/**
	 * Represent the controller of the subsystem
	 */
	private VNPaySubsystemController controller;

	/**
	 * Initializes a newly created {@code InterbankSubsystem} object so that it
	 * represents an Interbank subsystem.
	 */
	public VNPaySubsystemSubsystem() {
		this.controller = new VNPaySubsystemController();
	}

	@Override
	public PaymentTransaction getPaymentTransaction(Map<String, String> res) throws PaymentException, UnrecognizedException {
		return controller.getPaymentTransaction(res);
	}

	@Override
	public String generateURL(int amount, String content) throws IOException {
		return controller.generatePayOrderUrl(amount, content);
	}

	@Override
	public RefundTransaction refund(PaymentTransaction paymentTransaction) throws IOException {
		return controller.refund(paymentTransaction);
	}
}
