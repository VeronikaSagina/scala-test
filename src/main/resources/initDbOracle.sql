BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE' || wwe_logging_test;
    EXCEPTION
    WHEN OTHERS
    THEN
        IF SQLCODE != -942 THEN/*исключение отсутствующей таблицы*/
            RAISE;
        END IF;
END;

CREATE TABLE wwe_logging_test
(
    id NUMBER GENERATED BY DEFAULT AS IDENTITY (START WITH 1) NOT NULL,
    interaction_id VARCHAR2(16),
    operator_id NUMBER,
    interaction_type NUMBER,
    interaction_date DATE,
    created TIMESTAMP DEFAULT systimestamp
);