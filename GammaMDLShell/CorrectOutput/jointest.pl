table(parts_x_odetails,[parts.pno,"parts.pname",parts.qoh,parts.price,parts.olevel,odetails.ono,odetails.pno,odetails.qty]).
parts_x_odetails(10506,'Land Before Time I',200,19.99,20,1020,10506,1).
parts_x_odetails(10507,'Land Before Time II',156,19.99,20,1020,10507,1).
parts_x_odetails(10508,'Land Before Time III',190,19.99,20,1020,10508,2).
parts_x_odetails(10509,'Land Before Time IV',60,19.99,20,1020,10509,3).
parts_x_odetails(10601,'Sleeping Beauty',300,24.99,20,1021,10601,4).
parts_x_odetails(10601,'Sleeping Beauty',300,24.99,20,1022,10601,1).
parts_x_odetails(10701,'When Harry Met Sally',120,19.99,30,1022,10701,1).
parts_x_odetails(10800,'Dirty Harry',140,14.99,30,1023,10800,1).
parts_x_odetails(10900,'Dr. Zhivago',100,24.99,30,1023,10900,1).


table(client_x_viewing,[client.cno,client.fname,client.lname,"client.telno",client.preftype,client.maxrent,viewing.cno,viewing.propertyno,"viewing.viewdate","viewing.comment"]).
client_x_viewing(CR76,John,Kay,'0207-774-5632',Flat,425,CR76,PG4,'20-APR-04','too remote').
client_x_viewing(CR56,Aline,Stewart,'0141-848-1825',Flat,350,CR56,PA14,'24-MAY-04','too small').
client_x_viewing(CR56,Aline,Stewart,'0141-848-1825',Flat,350,CR56,PG4,'26-MAY-04','').
client_x_viewing(CR56,Aline,Stewart,'0141-848-1825',Flat,350,CR56,PG36,'28-APR-04','').
client_x_viewing(CR62,Mary,Tregear,'01224-196720',Flat,600,CR62,PA14,'14-MAY-04','no dining room').


table(orders_x_odetails,[orders.ono,orders.cno,orders.eno,"orders.received","orders.shipped",odetails.ono,odetails.pno,odetails.qty]).
orders_x_odetails(1020,1111,1000,'10-DEC-94','12-DEC-94',1020,10506,1).
orders_x_odetails(1020,1111,1000,'10-DEC-94','12-DEC-94',1020,10507,1).
orders_x_odetails(1020,1111,1000,'10-DEC-94','12-DEC-94',1020,10508,2).
orders_x_odetails(1020,1111,1000,'10-DEC-94','12-DEC-94',1020,10509,3).
orders_x_odetails(1021,1111,1000,'12-JAN-95','15-JAN-95',1021,10601,4).
orders_x_odetails(1022,2222,1001,'13-FEB-95','20-FEB-95',1022,10601,1).
orders_x_odetails(1022,2222,1001,'13-FEB-95','20-FEB-95',1022,10701,1).
orders_x_odetails(1023,3333,1000,'20-JUN-97','',1023,10800,1).
orders_x_odetails(1023,3333,1000,'20-JUN-97','',1023,10900,1).


