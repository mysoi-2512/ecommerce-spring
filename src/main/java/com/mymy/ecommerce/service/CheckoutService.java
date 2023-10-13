package com.mymy.ecommerce.service;

import com.mymy.ecommerce.dto.PaymentInfo;
import com.mymy.ecommerce.dto.Purchase;
import com.mymy.ecommerce.dto.PurchaseResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

public interface CheckoutService {
	
	PurchaseResponse placeOrder(Purchase purchase);
	
	PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException;

}
