CREATE TABLE KPI_Detail (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    KPI_Priority VARCHAR(255),
    KPI_Number VARCHAR(255),
    KPI_Name VARCHAR(255),
    KPI_Category VARCHAR(255),
    Fraud_Type VARCHAR(255),
    Service_Cust_Type VARCHAR(255),
    Enable_SMS TINYINT, 
    Enable_Email TINYINT,
    Threshold INTEGER, 
    Duration_Frequency INTEGER,
    Enable_KPI VARCHAR(255),
    Description MEDIUMTEXT )
;
