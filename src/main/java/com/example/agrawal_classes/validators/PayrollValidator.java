package com.example.agrawal_classes.validators;

import com.example.agrawal_classes.dao.PayrollDao;
import com.example.agrawal_classes.model.Payroll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PayrollValidator implements Validator {
    @Autowired
    private PayrollDao payrollDao;

    @Override
    public boolean supports(Class<?> aClass) {
        return Payroll.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Payroll payroll = (Payroll) target;
        String paymentRefNo = payroll.getPaymentRefNo();

        if (payrollDao.get(paymentRefNo) != null) {
            errors.rejectValue("paymentRefNo", "Duplicate.payroll.paymentRefNo");
        }
    }
}
