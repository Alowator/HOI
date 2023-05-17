SELECT f.flight_id, f.flight_no, t.fare_conditions, AVG(t.amount) AS average_price, f.departure_airport, f.arrival_airport
FROM flights f 
JOIN ticket_flights t ON f.flight_id = t.flight_id
GROUP BY f.flight_id, t.fare_conditions;

CREATE TABLE pricing_rule AS
SELECT f.flight_no, t.fare_conditions, AVG(t.amount) AS average_price
FROM flights f 
JOIN ticket_flights t ON f.flight_id = t.flight_id
GROUP BY f.flight_no, t.fare_conditions;
