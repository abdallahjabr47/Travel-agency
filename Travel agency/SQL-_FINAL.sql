CREATE DATABASE Travel_Agency;
use Travel_Agency;

-- Yazan Daibes 1180414
-- Raghad Alqam 1181919
-- Abdallah Jabr 1181768
-- Abdallah Afifi 1182972

CREATE TABLE customer(
	Passport_Number int NOT NULL primary key, 
    First_Name varchar(32),
    Last_Name VARCHAR (32),
    nationality VARCHAR(40),
    DoB Date,
    passwordd VARCHAR(32),
	Email VARCHAR (32),
	Phone VARCHAR(15),
    Country VARCHAR (40)
);
-- Yazan Daibes
CREATE TABLE airPlane_tickets(
	Ticket_Number int NOT NULL primary key auto_increment, 
    Travel_Date date,
    return_Date date,
    selling_Price int,
    seat_number VARCHAR(4),
    ticket_type VARCHAR(32),
    FlightNoAPT int,
    invoiceidTicket int,
    ageCatagory VARCHAR(30),
	 FOREIGN KEY (FlightNoAPT) REFERENCES airplane (FlightNo),
     FOREIGN KEY (invoiceidTicket) REFERENCES invoice(inv_id_number) 
);

CREATE TABLE invoice(
	inv_id_number int NOT NULL primary key AUTO_INCREMENT , 
	Currentdate date,
    priceFlight double,
    priceHotel double,
    priceCar double,
    toatlPrice double,
	Passport_NumberINV int,
	 FOREIGN KEY (Passport_NumberINV) REFERENCES customer(Passport_Number)
);

CREATE TABLE car(
	plate_no int not null primary key,
	mileage varchar(32)  , 
    rental_date date DEFAULT NULL,
    noOfDays int,
	car_brand VARCHAR (32),
    basePrice DOUBLE,
    drop_location int,
	pickup_location int,
    FOREIGN KEY (drop_location) REFERENCES city(cityID),
    FOREIGN KEY (pickup_location) REFERENCES city(cityID)
);

insert into car values (1,200,"2021-01-21",1,"BMW",300,2,3);
insert into car values (2,33,"2021-01-14",1,"Mercedes",800,2,17);
insert into car values (322,200,"2021-01-11",1,"Skoda",300,12,19);


CREATE TABLE hotelBooking (
  ReservationID int NOT NULL,
  BookingDate date,
  CheckIn date,
  CheckOut date ,
  NumberOfRooms int,
  cost double ,
  NumberOfGuests int ,
  PRIMARY KEY (ReservationID),
  HotelidB int,
  inv_idB int,
   FOREIGN KEY (HotelidB) REFERENCES hotel(Hotelid),
   FOREIGN KEY (inv_idB) REFERENCES invoice(inv_id_number)

);

CREATE TABLE hotel (
  Hotelid int  NOT NULL,
  numberOfReservedRooms int,
  numberOfSingleRooms int,
  numberOfDoubleRooms int,
  numberOfSweets int,
  hotel_name  varchar(20),
  PRIMARY KEY (Hotelid),
  cityidHotel int,
  base_price double,
  FOREIGN KEY (cityidHotel) REFERENCES city(cityid)
);

insert into hotel (Hotelid,numberOfReservedRooms,cityidHotel,hotel_name,numberOfSingleRooms,numberOfDoubleRooms,numberOfSweets,base_price)
values (1,500,15,"Hyatt",150,150,40,220);
insert into hotel values (2,0,14,"Rotana",198,150,100,500);
insert into hotel values (3,0,14,"Rotana",198,150,30,500);
insert into hotel values (4,0,12,"Movinpek",600,550,456,100);
insert into hotel values (5,0,17,"Oasis",100,100,60,350);

CREATE TABLE city (
  cityid int,
  CityName varchar(20),
  CountryName varchar(20),
  PRIMARY KEY (cityid)
); 
insert into city values(8,"Amman","Jordan");
insert into city values (22,"Amsterdam","Holand");
insert into city values(9,"Baghdad","Iraq");
insert into city values (14,"Barcalona","Spain");
insert into city values (7,"Bristol","United Kingdom");
insert into city values (13,"Cairo","Egypt");
insert into city values (20,"Chigago","Usa");
insert into city values (10,"Damascus","Syria");
insert into city values (25,"Doha","Qatar");
insert into city values (18,"Dubai","United Arab Emirates");
insert into city values (11,"Frankfurt","Germany");
insert into city values (23,"Istanbul","Turkey");
insert into city values (24,"Las Vegas","USA");
insert into city values (2,"London","United Kingdom");
insert into city values (1,"Los Angelos","USA");
insert into city values (15,"Madrid","Spain");
insert into city values (27,"Miami","USA");
insert into city values (29,"Milan","Italy");
insert into city values (12,"Moscow","Russia");
insert into city values (28,"Munich","Germany");
insert into city values (17,"New York City","USA");
insert into city values (6,"Paris","France");
insert into city values (19,"Rome","Italy");
insert into city values (4,"Salt Lake City","USA");
insert into city values (3,"San Diago","USA");
insert into city values (26,"Seoul","South Korea");
insert into city values (16,"Tokyo","Japan");
insert into city values (21,"Toronto","Canada");
insert into city values (30,"Vienna","Austria");

CREATE TABLE airplane (
  FlightNo int NOT NULL,
  DepartureDate date,
  returnDate date,
  Number_of_seats varchar(20),
  Number_of_passengers varchar(20),
  airlineName  VARCHAR(32),
  PRIMARY KEY (FlightNo),
 BasePriceOfTicket double,
 cityIDFrom int,
 cityIDTo int,
 FOREIGN KEY (cityIDFrom) REFERENCES city(cityid),
 FOREIGN KEY (cityIDTo) REFERENCES city(cityid)
);

INSERT INTO airplane (DepartureDate,returnDate,Number_of_seats , Number_of_passengers,airlineName,BasePriceOfTicket,cityIDFrom,cityIDTo)
 VALUES("2021-10-10","2021-10-20",10,500,"South-West",500,1,2);
 
INSERT INTO airplane (DepartureDate,returnDate,Number_of_seats , Number_of_passengers,airlineName,BasePriceOfTicket,cityIDFrom,cityIDTo)
 VALUES("2021-2-1","2021-4-22",102,100,"South-West",600,2,1);

INSERT INTO airplane (DepartureDate,returnDate,Number_of_seats , Number_of_passengers,airlineName,BasePriceOfTicket,cityIDFrom,cityIDTo)
 VALUES("2021-3-5","2021-4-20",122,500,"Aemrican Airlines",300,6,7);
 
 INSERT INTO airplane (DepartureDate,returnDate,Number_of_seats , Number_of_passengers,airlineName,BasePriceOfTicket,cityIDFrom,cityIDTo)
 VALUES("2021-4-11","2021-4-20",122,500,"Aemrican Airlines",600,25,27);
 
 INSERT INTO airplane (DepartureDate,returnDate,Number_of_seats , Number_of_passengers,airlineName,BasePriceOfTicket,cityIDFrom,cityIDTo)
 VALUES("2021-4-11","2021-4-20",700,500,"German Airlines",250,20,26);
 INSERT INTO airplane (DepartureDate,returnDate,Number_of_seats , Number_of_passengers,airlineName,BasePriceOfTicket,cityIDFrom,cityIDTo)
 VALUES("2021-4-4","2021-4-15",600,500,"British Airlines",800,22,24);

 INSERT INTO airplane (DepartureDate,returnDate,Number_of_seats , Number_of_passengers,airlineName,BasePriceOfTicket,cityIDFrom,cityIDTo)
 VALUES("2021-8-11","2021-8-20",600,250,"United Airlines",450,19,22);
 
 INSERT INTO airplane (DepartureDate,returnDate,Number_of_seats , Number_of_passengers,airlineName,BasePriceOfTicket,cityIDFrom,cityIDTo)
 VALUES("2021-7-11","2021-7-15",100,100,"Lufthanza Airlines",1000,18,15);

 INSERT INTO airplane (DepartureDate,returnDate,Number_of_seats , Number_of_passengers,airlineName,BasePriceOfTicket,cityIDFrom,cityIDTo)
 VALUES("2021-2-10","2021-2-15",1000,1000,"Lufthanza Airlines",250,16,11);

 INSERT INTO airplane (DepartureDate,returnDate,Number_of_seats , Number_of_passengers,airlineName,BasePriceOfTicket,cityIDFrom,cityIDTo)
 VALUES("2021-6-10","2021-7-15",250,250,"Delta Airlines",450,18,15);

 INSERT INTO airplane (DepartureDate,returnDate,Number_of_seats , Number_of_passengers,airlineName,BasePriceOfTicket,cityIDFrom,cityIDTo)
 VALUES("2021-6-10","2021-7-15",250,250,"JetBlue Airlines",350,21,20);

 INSERT INTO airplane (DepartureDate,returnDate,Number_of_seats , Number_of_passengers,airlineName,BasePriceOfTicket,cityIDFrom,cityIDTo)
 VALUES("2021-3-10","2021-3-15",300,300,"Cape Airlines",350,20,19);

 INSERT INTO airplane (DepartureDate,returnDate,Number_of_seats , Number_of_passengers,airlineName,BasePriceOfTicket,cityIDFrom,cityIDTo)
 VALUES("2021-3-10","2021-3-15",400,400,"United Airlines",450,20,19);
 
 INSERT INTO airplane (DepartureDate,returnDate,Number_of_seats , Number_of_passengers,airlineName,BasePriceOfTicket,cityIDFrom,cityIDTo)
 VALUES("2021-3-10","2021-3-15",400,400,"Delta Airlines",500,20,19);

 INSERT INTO airplane (DepartureDate,returnDate,Number_of_seats , Number_of_passengers,airlineName,BasePriceOfTicket,cityIDFrom,cityIDTo)
 VALUES("2021-3-10","2021-3-15",800,800,"Delta Airlines",800,20,19);
 
  INSERT INTO airplane (DepartureDate,returnDate,Number_of_seats , Number_of_passengers,airlineName,BasePriceOfTicket,cityIDFrom,cityIDTo)
 VALUES("2021-4-11","2021-4-20",800,800,"Delta Airlines",200,20,26);
   INSERT INTO airplane (DepartureDate,returnDate,Number_of_seats , Number_of_passengers,airlineName,BasePriceOfTicket,cityIDFrom,cityIDTo)
 VALUES("2021-4-11","2021-4-20",800,800,"Delta Airlines",400,20,26);

 
CREATE TABLE adminEmployee(
	employeeNum INT PRIMARY KEY,
    employeeFirstName VARCHAR(32),
	employeeLastName VARCHAR(32),
	employeeEmail VARCHAR(32),
    employeeDOB date,
	employeePassword VARCHAR(32)
);
insert into adminEmployee values (1,"ADMIN","ADMIN","Admin@gmail.com","1990-02-02",123);
