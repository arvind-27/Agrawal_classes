package com.example.agrawal_classes.dao;

import com.example.agrawal_classes.dao.rowmappers.PayrollRowMapper;
import com.example.agrawal_classes.model.Payroll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public class PayrollDaoImpl implements com.example.agrawal_classes.dao.PayrollDao {
    @Autowired
    private JdbcTemplate template;

    @Override
    public void save(Payroll payroll) {
        String sql = "INSERT INTO Payroll (paymentRefNo, month, year, salaryCredited, dateCredited, employeeId) VALUES (?, ?, ?, ?, ?, ?)";
        template.update(sql, payroll.getPaymentRefNo(), payroll.getMonth(), payroll.getYear(),
                payroll.getSalaryCredited(), payroll.getDateCredited(), payroll.getEmployee().getEmployeeId());
    }

    @Override
    public Payroll get(String paymentRefNo) {
        try {
            String sql = "SELECT * FROM Payroll NATURAL JOIN Employee NATURAL JOIN User WHERE paymentRefNo = ?";
            Payroll payroll = template.queryForObject(sql, new Object[] { paymentRefNo }, new PayrollRowMapper());
            return payroll;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Payroll> getAll() {
        String sql = "SELECT * FROM Payroll NATURAL JOIN Employee NATURAL JOIN User";
        List<Payroll> payrolls = template.query(sql, new PayrollRowMapper());
        return payrolls;
    }

    @Override
    public List<Payroll> getAllByEmployeeId(int employeeId) {
        String sql = "SELECT * FROM Payroll NATURAL JOIN Employee NATURAL JOIN User WHERE employeeId = ?";
        List<Payroll> payrolls = template.query(sql, new Object[] { employeeId }, new PayrollRowMapper());
        return payrolls;
    }

    @Override
    public List<Payroll> getAllByMonthYear(int month, int year) {
        String sql = "SELECT * FROM Payroll NATURAL JOIN Employee NATURAL JOIN User WHERE month = ? AND year = ?";
        List<Payroll> payrolls = template.query(sql, new Object[] { month, year }, new PayrollRowMapper());
        return payrolls;
    }

    /**
     * Update all attributes except paymentRefNo
     */
    @Override
    public void update(Payroll payroll) {
        String sql = "UPDATE Payroll SET month = ?, year = ?, salaryCredited = ?, dateCredited = ?, employeeId = ? WHERE paymentRefNo = ?";
        template.update(sql, payroll.getMonth(), payroll.getYear(), payroll.getSalaryCredited(),
                payroll.getDateCredited(), payroll.getEmployee().getEmployeeId(), payroll.getPaymentRefNo());
    }

    @Override
    public void delete(String paymentRefNo) {
        String sql = "DELETE FROM Payroll WHERE paymentRefNo = ?";
        template.update(sql, paymentRefNo);
    }

}
