DELIMITER //

CREATE EVENT DailyStatusUpdate
ON SCHEDULE EVERY 1 DAY
STARTS CURRENT_TIMESTAMP
DO
BEGIN
    -- Update reservations with reservation_status_id = 2
    UPDATE reservations
    SET reservation_status_id = 3
    WHERE reservation_status_id = 2 AND end_date < CURDATE();

    -- Update associated device statuses to 5
    UPDATE devices
    SET status_id = 5
    WHERE device_id IN (
        SELECT device_id
        FROM reservations
        WHERE reservation_status_id = 3
    );
END;
//

DELIMITER ;