package subsystem;

import common.exception.PaymentException;
import common.exception.UnrecognizedException;
import entity.payment.PaymentTransaction;
import entity.payment.RefundTransaction;

import java.io.IOException;
import java.util.Map;

/**
 * The {@code InterbankInterface} class is used to communicate with the
 * {@link PaymentSubsystem InterbankSubsystem} to make transaction
 * 
 * @author hieud
 * 
 */
public interface IPaymentSubsystem {

	public abstract PaymentTransaction getPaymentTransaction(Map<String,String> res)
			throws PaymentException, UnrecognizedException, IOException;

	public abstract String generateURL(int amount, String content) throws IOException;
	public abstract RefundTransaction refund(PaymentTransaction paymentTransaction) throws IOException;

}
