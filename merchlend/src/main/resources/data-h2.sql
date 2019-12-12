-- Lender mock data
INSERT INTO Lender
  (Lender_Id, Lender_Name, Min_Score, Max_Score, Interest_rate, Preferred, Score, Max_Amount)
VALUES
  (101, 'Ujjivan', 0, 10, 16, 'Y', 'Low', 10000);

INSERT INTO Lender
  (Lender_Id, Lender_Name, Min_Score, Max_Score, Interest_rate, Preferred, Score, Max_Amount)
VALUES
  (101, 'Ujjivan', 11, 20, 12, 'Y', 'Medium', 50000);

INSERT INTO Lender
  (Lender_Id, Lender_Name, Min_Score, Max_Score, Interest_rate, Preferred, Score, Max_Amount)
VALUES
  (101, 'Ujjivan', 21, 30, 9, 'Y', 'High', 100000);

INSERT INTO Lender
  (Lender_Id, Lender_Name, Min_Score, Max_Score, Interest_rate, Preferred, Score, Max_Amount)
VALUES
  (102, 'HDFC', 0, 10, 17, null, 'Low', 10000);

INSERT INTO Lender
  (Lender_Id, Lender_Name, Min_Score, Max_Score, Interest_rate, Preferred, Score, Max_Amount)
VALUES
  (102, 'HDFC', 11, 20, 12, null, 'Medium', 50000);

INSERT INTO Lender
  (Lender_Id, Lender_Name, Min_Score, Max_Score, Interest_rate, Preferred, Score, Max_Amount)
VALUES
  (102, 'HDFC', 21, 30, 9, null, 'High', 100000);

INSERT INTO Lender
  (Lender_Id, Lender_Name, Min_Score, Max_Score, Interest_rate, Preferred, Score, Max_Amount)
VALUES
  (103, 'DHFL', 0, 10, 16.5, null, 'Low', 10000);

INSERT INTO Lender
  (Lender_Id, Lender_Name, Min_Score, Max_Score, Interest_rate, Preferred, Score, Max_Amount)
VALUES
  (103, 'DHFL', 11, 20, 12.5, null, 'Medium', 50000);

INSERT INTO Lender
  (Lender_Id, Lender_Name, Min_Score, Max_Score, Interest_rate, Preferred, Score, Max_Amount)
VALUES
  (103, 'DHFL', 21, 30, 9.2, null, 'High', 100000);

INSERT INTO Lender
  (Lender_Id, Lender_Name, Min_Score, Max_Score, Interest_rate, Preferred, Score, Max_Amount)
VALUES
  (104, 'LIC', 0, 10, 16.8, null, 'Low', 10000);

INSERT INTO Lender
  (Lender_Id, Lender_Name, Min_Score, Max_Score, Interest_rate, Preferred, Score, Max_Amount)
VALUES
  (104, 'LIC', 11, 20, 12.6, null, 'Medium', 50000);

INSERT INTO Lender
  (Lender_Id, Lender_Name, Min_Score, Max_Score, Interest_rate, Preferred, Score, Max_Amount)
VALUES
  (104, 'LIC', 21, 30, 9, null, 'High', 100000);


-- Offer mock data
INSERT INTO Offer
  (Merchant_id, Lender_Id, Interest, Email, Merchant_Name, Offer_date, Loan_Amount_requested_by_merchant, Merchant_response_date, Merchant_accptance_status, Merchant_rejection_reason, Lender_acceptance_status, Lender_rejection_reason, Lender_Approved_amount, Remarks, Eligible_Amount, Offer_End_Date, Interest_Rate, Interest_Period)
VALUES
  (100001, 101, 12, 'merchant1@email.com', 'Merchant1 Kumar', '2018-11-02', null, null, null, null, null, null, null, null, null, null, 9, 1);

INSERT INTO Offer
  (Merchant_id, Lender_Id, Interest, Email, Merchant_Name, Offer_date, Loan_Amount_requested_by_merchant, Merchant_response_date, Merchant_accptance_status, Merchant_rejection_reason, Lender_acceptance_status, Lender_rejection_reason, Lender_Approved_amount, Remarks, Eligible_Amount, Offer_End_Date)
VALUES
  (100001, 102, 12, 'merchant1@email.com', 'Merchant1 Kumar', '2018-11-02', null, null, null, null, null, null, null, null, null, null);

INSERT INTO Offer
  (Merchant_id, Lender_Id, Interest, Email, Merchant_Name, Offer_date, Loan_Amount_requested_by_merchant, Merchant_response_date, Merchant_accptance_status, Merchant_rejection_reason, Lender_acceptance_status, Lender_rejection_reason, Lender_Approved_amount, Remarks, Eligible_Amount, Offer_End_Date)
VALUES
  (100001, 103, 12.5, 'merchant1@email.com', 'Merchant1 Kumar', '2018-11-02', null, null, null, null, null, null, null, null, null, null);

INSERT INTO Offer
  (Merchant_id, Lender_Id, Interest, Email, Merchant_Name, Offer_date, Loan_Amount_requested_by_merchant, Merchant_response_date, Merchant_accptance_status, Merchant_rejection_reason, Lender_acceptance_status, Lender_rejection_reason, Lender_Approved_amount, Remarks, Eligible_Amount, Offer_End_Date)
VALUES
  (100002, 101, 16, 'merchant2@email.com', 'Merchant2 Kumari', '2018-09-30', 10000, '2018-11-01', 'Interested', null, 'Verified', null, 7000, null, null, null);

INSERT INTO Offer
  (Merchant_id, Lender_Id, Interest, Email, Merchant_Name, Offer_date, Loan_Amount_requested_by_merchant, Merchant_response_date, Merchant_accptance_status, Merchant_rejection_reason, Lender_acceptance_status, Lender_rejection_reason, Lender_Approved_amount, Remarks, Eligible_Amount, Offer_End_Date)
VALUES
  (100002, 102, 17, 'merchant2@email.com', 'Merchant2 Kumari', '2018-11-02', null, null, null, null, null, null, null, null, null, null);

INSERT INTO Offer
  (Merchant_id, Lender_Id, Interest, Email, Merchant_Name, Offer_date, Loan_Amount_requested_by_merchant, Merchant_response_date, Merchant_accptance_status, Merchant_rejection_reason, Lender_acceptance_status, Lender_rejection_reason, Lender_Approved_amount, Remarks, Eligible_Amount, Offer_End_Date)
VALUES
  (100002, 103, 16.5, 'merchant2@email.com', 'Merchant2 Kumari', '2018-11-01', null, null, null, null, null, null, null, null, null, null);

INSERT INTO Offer
  (Merchant_id, Lender_Id, Interest, Email, Merchant_Name, Offer_date, Loan_Amount_requested_by_merchant, Merchant_response_date, Merchant_accptance_status, Merchant_rejection_reason, Lender_acceptance_status, Lender_rejection_reason, Lender_Approved_amount, Remarks, Eligible_Amount, Offer_End_Date)
VALUES
  (100002, 104, 16.8, 'merchant2@email.com', 'Merchant2 Kumari', '2018-11-04', null, null, null, null, null, null, null, null, null, null);

INSERT INTO Offer
  (Merchant_id, Lender_Id, Interest, Email, Merchant_Name, Offer_date, Loan_Amount_requested_by_merchant, Merchant_response_date, Merchant_accptance_status, Merchant_rejection_reason, Lender_acceptance_status, Lender_rejection_reason, Lender_Approved_amount, Remarks, Eligible_Amount, Offer_End_Date)
VALUES
  (100003, 103, 8.7, 'merchant3@email.com', 'Merchant3', '2018-09-30', 20000, '2018-11-01', 'Interested', null, 'Rejected', 'Business is too risky', null, null, null, null);

INSERT INTO Offer
  (Merchant_id, Lender_Id, Interest, Email, Merchant_Name, Offer_date, Loan_Amount_requested_by_merchant, Merchant_response_date, Merchant_accptance_status, Merchant_rejection_reason, Lender_acceptance_status, Lender_rejection_reason, Lender_Approved_amount, Remarks, Eligible_Amount, Offer_End_Date)
VALUES
  (100003, 104, 8.7, 'merchant3@email.com', 'Merchant3', '2018-11-01', 20000, '2018-11-01', 'Interested', null, 'Document Submission', null, 12000, null, null, null);

INSERT INTO Offer
  (Merchant_id, Lender_Id, Interest, Email, Merchant_Name, Offer_date, Loan_Amount_requested_by_merchant, Merchant_response_date, Merchant_accptance_status, Merchant_rejection_reason, Lender_acceptance_status, Lender_rejection_reason, Lender_Approved_amount, Remarks, Eligible_Amount, Offer_End_Date)
VALUES
  (100004, 101, 9, 'merchant4@email.com', 'Merchant4', '2018-09-30', 5000, null, 'Interested', null, 'Approved', null, 5000, null, null, null);

INSERT INTO Offer
  (Merchant_id, Lender_Id, Interest, Email, Merchant_Name, Offer_date, Loan_Amount_requested_by_merchant, Merchant_response_date, Merchant_accptance_status, Merchant_rejection_reason, Lender_acceptance_status, Lender_rejection_reason, Lender_Approved_amount, Remarks, Eligible_Amount, Offer_End_Date)
VALUES
  (100004, 102, 9, 'merchant4@email.com', 'Merchant4', '2018-11-01', null, null, null, null, null, null, null, null, null, null);

INSERT INTO Offer
  (Merchant_id, Lender_Id, Interest, Email, Merchant_Name, Offer_date, Loan_Amount_requested_by_merchant, Merchant_response_date, Merchant_accptance_status, Merchant_rejection_reason, Lender_acceptance_status, Lender_rejection_reason, Lender_Approved_amount, Remarks, Eligible_Amount, Offer_End_Date)
VALUES
  (100004, 103, 9.2, 'merchant4@email.com', 'Merchant4', '2018-09-27', null, null, 'Rejected', 'Interest rate is too high', null, null, null, null, null, null);

INSERT INTO Offer
  (Merchant_id, Lender_Id, Interest, Email, Merchant_Name, Offer_date, Loan_Amount_requested_by_merchant, Merchant_response_date, Merchant_accptance_status, Merchant_rejection_reason, Lender_acceptance_status, Lender_rejection_reason, Lender_Approved_amount, Remarks, Eligible_Amount, Offer_End_Date)
VALUES
  (100004, 104, 9, 'merchant4@email.com', 'Merchant4', '2018-09-28', null, null, null, null, null, null, null, null, null, null);

INSERT INTO Offer
  (Merchant_id, Lender_Id, Interest, Email, Merchant_Name, Offer_date, Loan_Amount_requested_by_merchant, Merchant_response_date, Merchant_accptance_status, Merchant_rejection_reason, Lender_acceptance_status, Lender_rejection_reason, Lender_Approved_amount, Remarks, Eligible_Amount, Offer_End_Date)
VALUES
  (100005, 101, 12, 'merchant5@email.com', 'Merchant5', '2018-09-30', null, null, 'Not Interested', 'Not interested in this bank', null, null, null, null, null, null);

INSERT INTO Offer
  (Merchant_id, Lender_Id, Interest, Email, Merchant_Name, Offer_date, Loan_Amount_requested_by_merchant, Merchant_response_date, Merchant_accptance_status, Merchant_rejection_reason, Lender_acceptance_status, Lender_rejection_reason, Lender_Approved_amount, Remarks, Eligible_Amount, Offer_End_Date)
VALUES
  (100005, 102, 12, 'merchant5@email.com', 'Merchant5', '2018-11-01', 900, null, 'Interested', null, null, null, null, null, null, null);

INSERT INTO Offer
  (Merchant_id, Lender_Id, Interest, Email, Merchant_Name, Offer_date, Loan_Amount_requested_by_merchant, Merchant_response_date, Merchant_accptance_status, Merchant_rejection_reason, Lender_acceptance_status, Lender_rejection_reason, Lender_Approved_amount, Remarks, Eligible_Amount, Offer_End_Date)
VALUES
  (100005, 103, 12.5, 'merchant5@email.com', 'Merchant5', '2018-09-27', null, null, 'Not Interested', 'Interest rate is too high', null, null, null, null, null, null);

INSERT INTO Offer
  (Merchant_id, Lender_Id, Interest, Email, Merchant_Name, Offer_date, Loan_Amount_requested_by_merchant, Merchant_response_date, Merchant_accptance_status, Merchant_rejection_reason, Lender_acceptance_status, Lender_rejection_reason, Lender_Approved_amount, Remarks, Eligible_Amount, Offer_End_Date)
VALUES
  (100005, 104, 12.6, 'merchant5@email.com', 'Merchant5', '2018-09-28', null, null, 'Not Interested', 'Interest rate is too high', null, null, null, null, null, null);
