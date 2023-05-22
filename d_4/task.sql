# Посмотрев на таблицу вот так приходим к выводу, 
# что для каждого PK (f.flight_no, bp.seat_no, tf.fare_conditions)
# существует только 1 уникальное значение стоимости билета, 
# за исключением тарифа Economy, для него существует 1 или 2 уникальные стоимости
SELECT 
	f.flight_no, 
	bp.seat_no, 
	tf.fare_conditions, 
	COUNT(DISTINCT(tf.amount)) AS count_unique_amounts
FROM ticket_flights tf
JOIN boarding_passes bp ON tf.ticket_no = bp.ticket_no
JOIN flights f ON f.flight_id = tf.flight_id
GROUP BY f.flight_no, bp.seat_no, tf.fare_conditions;



# Назовем более дорогой Economy тариф - Economy plus
# и создадим таблицу с ценами для 4 тарифов для каждого
# PK (f.flight_no, bp.seat_no) 
CREATE TABLE pricing_rule AS
SELECT
	f.flight_no,
	bp.seat_no,
	CASE
    	WHEN tf.amount = MAX(tf.amount) OVER (PARTITION BY f.flight_no, bp.seat_no, tf.fare_conditions)
			AND COUNT(tf.amount) OVER (PARTITION BY f.flight_no, bp.seat_no, tf.fare_conditions) = 2
		THEN CONCAT(tf.fare_conditions, ' plus')
    	ELSE tf.fare_conditions
  	END AS fare_conditions,
	tf.amount
FROM ticket_flights tf
JOIN boarding_passes bp ON tf.ticket_no = bp.ticket_no
JOIN flights f ON f.flight_id = tf.flight_id
GROUP BY f.flight_no, bp.seat_no, tf.fare_conditions, tf.amount;



# Получим вот такой результат (223-230 строки таблицы pricing_rule)
"PG0013"	"13H"	"Business"		42100.00
"PG0013"	"13H"	"Comfort"		23900.00
"PG0013"	"13H"	"Economy"		14000.00
"PG0013"	"13H"	"Economy plus"	15400.00
"PG0013"	"13K"	"Business"		42100.00
"PG0013"	"13K"	"Comfort"		23900.00
"PG0013"	"13K"	"Economy"		14000.00
"PG0013"	"13K"	"Economy plus"	15400.00
