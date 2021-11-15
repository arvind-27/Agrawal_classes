package com.example.agrawal_classes.services;

import com.example.agrawal_classes.dao.UserPhoneNumberDao;
import com.example.agrawal_classes.model.User;
import com.example.agrawal_classes.model.UserPhoneNumber;
import com.example.agrawal_classes.utils.ServerUtil;
import com.instamojo.wrapper.api.ApiContext;
import com.instamojo.wrapper.api.Instamojo;
import com.instamojo.wrapper.api.InstamojoImpl;
import com.instamojo.wrapper.exception.ConnectionException;
import com.instamojo.wrapper.exception.HTTPException;
import com.instamojo.wrapper.model.PaymentOrder;
import com.instamojo.wrapper.model.PaymentOrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Transactional
@Service
public class PaymentService {
    @Autowired
    private ServerUtil serverUtil;

    @Autowired
    private UserPhoneNumberDao userPhoneNumberDao;

    /*
     * Get a reference to the instamojo api
     */
    ApiContext context = ApiContext.create(System.getenv("INSTAMOJO_CLIENT_ID"), System.getenv("INSTAMOJO_CLIENT_SECRET"), ApiContext.Mode.TEST);
    Instamojo api = new InstamojoImpl(context);

    public String getTransactionPrefix() {
        String transaction_prefix = System.getenv("TRANSACTION_PREFIX");
        if (transaction_prefix == null) {
            return "";
        } else {
            return transaction_prefix;
        }
    }

    public int getTransactionId(String transaction_id) {
        String prefix = getTransactionPrefix();
        return Integer.valueOf(transaction_id.substring(prefix.length()));
    }

    public String createPayment(User user, int transactionId, int amount, String courseId, String batchId) {

        /*
         * Create a new payment order
         */
        PaymentOrder order = new PaymentOrder();
        order.setName(user.getName());
        order.setEmail(user.getEmailAddress());
        order.setPhone("9999999999");
        order.setCurrency("INR");
        order.setAmount((double)amount);
        order.setDescription("Payment for enrolling " + user.getName());
        String redirectUrl = serverUtil.getHostAddressAndPort() + "/student/transaction/" + courseId + "/" + batchId;
        order.setRedirectUrl(redirectUrl);
        order.setTransactionId(getTransactionPrefix() + transactionId);

        List<UserPhoneNumber> phoneNumbers = userPhoneNumberDao.getByUserId(user.getUserId());
        if (phoneNumbers.size() > 0) {
            String phoneNumber = phoneNumbers.get(0).getPhoneNumber();
            if (phoneNumber.length() == 10) {
                order.setPhone(phoneNumber);
            }
        }

        try {
            PaymentOrderResponse paymentOrderResponse = api.createPaymentOrder(order);
            return paymentOrderResponse.getPaymentOptions().getPaymentUrl();

        } catch (HTTPException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    e.getStatusCode() + e.getMessage() + e.getJsonPayload());

        } catch (ConnectionException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public String getTransactionDetails(int transactionId) {
        /*
         * Get details of payment order when transaction id is given
         */
        try {
            PaymentOrder paymentOrder = api.getPaymentOrderByTransactionId(getTransactionPrefix() + transactionId);
            return paymentOrder.getStatus();

        } catch (HTTPException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    e.getStatusCode() + " " + e.getMessage() + " " + e.getJsonPayload());

        } catch (ConnectionException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
