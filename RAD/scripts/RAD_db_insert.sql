INSERT INTO roles (role_name) VALUES ('FH-Mitarbeiter');
INSERT INTO roles (role_name) VALUES ('Student');

INSERT INTO device_status (device_status_name) VALUES ('verfügbar');
INSERT INTO device_status (device_status_name) VALUES ('verliehen');
INSERT INTO device_status (device_status_name) VALUES ('defekt');
INSERT INTO device_status (device_status_name) VALUES ('ausgegliedert');
INSERT INTO device_status (device_status_name) VALUES ('überfällig');

INSERT INTO reservation_status (reservation_status_name) VALUES ('reserviert');
INSERT INTO reservation_status (reservation_status_name) VALUES ('laufend');
INSERT INTO reservation_status (reservation_status_name) VALUES ('überfällig');
INSERT INTO reservation_status (reservation_status_name) VALUES ('abgeschlossen');

INSERT INTO categories (category_name) VALUES ('Laptops');
INSERT INTO categories (category_name) VALUES ('Messgeräte');
INSERT INTO categories (category_name) VALUES ('Werkzeug');
INSERT INTO categories (category_name) VALUES ('Kameras');
INSERT INTO categories (category_name) VALUES ('Mikrofone');

INSERT INTO devices (inventory_id, inventory_code, name, brand, model, serial_nr, room_nr, buy_date, log_date, disposal_date, price, device_status_id, comments, category_id)
VALUES  
  ('INV00001', 'FHID00001', 'Oscilloscope', 'BrandC', 'ModelZ', 'K1L2M3N4O5', '103', '2020-01-02', '2020-01-02', NULL, '345.67', 1, NULL, 2),				#1
  ('INV00002', 'FHID00002', 'Multimeter', 'BrandD', 'ModelP', 'P9Q8R7S6T5U4', '104', '2021-01-03', '2021-01-03', NULL, '78.90', 2, NULL, 2),				#2
  ('INV00003', 'FHID00003', 'Camera', 'BrandE', 'ModelQ', 'A1B2C3D4E5', '105', '2023-01-04', '2023-01-04', NULL, '12.34', 1, NULL, 4),						#3
  ('INV00004', 'FHID00004', 'Microphone', 'BrandF', 'ModelR', 'F6G7H8I9J0', '106', '2013-01-05', '2013-01-05', NULL, '56.78', 1, NULL, 5),					#4
  ('INV00005', 'FHID00005', 'Tablet', 'BrandA', 'ModelX', 'K1L2M3N4O5', '107', '2018-01-06', '2018-01-06', NULL, '90.12', 2, NULL, 1),						#5
  ('INV00006', 'FHID00006', 'Power Drill', 'BrandB', 'ModelY', 'P9Q8R7S6T5U4', '108', '2015-01-07', '2015-01-07', NULL, '34.56', 5, NULL, 3),				#6	
  ('INV00007', 'FHID00007', 'Camera', 'BrandC', 'ModelZ', 'A1B2C3D4E5', '109', '2016-01-08', '2016-01-08', NULL, '78.90', 5, NULL, 4),						#7
  ('INV00008', 'FHID00008', 'Multimeter', 'BrandD', 'ModelP', 'F6G7H8I9J0', '110', '2014-01-09', '2014-01-09', NULL, '12.34', 3, NULL, 2),					#8
  ('INV00009', 'FHID00009', 'Microphone', 'BrandE', 'ModelQ', 'K1L2M3N4O5', '111', '2019-01-10', '2019-01-10', NULL, '56.78', 3, NULL, 5),					#9
  ('INV00010', 'FHID00010', 'Soldering Iron', 'BrandF', 'ModelR', 'P9Q8R7S6T5U4', '112', '2011-01-11', '2011-01-11', '2021-05-23', '90.12', 4, NULL, 3),	#10
  ('INV00011', 'FHID00011', 'Oscilloscope', 'BrandA', 'ModelX', 'A1B2C3D4E5', '113', '2003-01-12', '2003-01-12', NULL, '34.56', 2, NULL, 2),				#11
  ('INV00012', 'FHID00012', 'Infrared Camera', 'BrandB', 'ModelY', 'F6G7H8I9J0', '114', '2010-01-13', '2010-01-13', '2020-05-04', '78.90', 4, NULL, 4),		#12
  ('INV00013', 'FHID00013', 'Power Drill', 'BrandC', 'ModelZ', 'K1L2M3N4O5', '115', '2017-01-14', '2017-01-14', NULL, '12.34', 1, NULL, 3),					#13
  ('INV00014', 'FHID00014', 'Tablet', 'BrandD', 'ModelP', 'P9Q8R7S6T5U4', '116', '2023-01-15', '2023-01-15', NULL, '56.78', 1, NULL, 1),					#14
  ('INV00015', 'FHID00015', 'Camera', 'BrandE', 'ModelQ', 'A1B2C3D4E5', '117', '2022-01-16', '2022-01-16', NULL, '90.12', 1, NULL, 4),						#15	
  ('INV00016', 'FHID00016', 'Microphone', 'BrandF', 'ModelR', 'F6G7H8I9J0', '118', '2015-01-17', '2015-01-17', NULL, '34.56', 2, NULL, 5),					#16	
  ('INV00017', 'FHID00017', 'Multimeter', 'BrandA', 'ModelX', 'K1L2M3N4O5', '119', '2023-01-18', '2023-01-18', NULL, '78.90', 1, NULL, 2),					#17
  ('INV00018', 'FHID00018', 'Soldering Iron', 'BrandB', 'ModelY', 'P9Q8R7S6T5U4', '120', '2023-01-19', '2023-01-19', NULL, '12.34', 2, NULL, 3);			#18
  
INSERT INTO users (name, username, password, role_id) VALUES ('Waiter Pudsey', 'wpudsey0', 'xG6(.c58a', 2);
INSERT INTO users (name, username, password, role_id) VALUES ('Vonni Tindley', 'vtindley1', 'aX4''T#3bl', 2);
INSERT INTO users (name, username, password, role_id) VALUES ('Peyter Masarrat', 'pmasarrat2', 'oJ5)1X{d,c(%/)?6', 1);
INSERT INTO users (name, username, password, role_id) VALUES ('Ruthy Jobern', 'rjobern3', 'tI8|V04LN(', 2);
INSERT INTO users (name, username, password, role_id) VALUES ('Muire MacClure', 'mmacclure4', 'nF5$GW8ew''=', 1);
INSERT INTO users (name, username, password, role_id) VALUES ('Miguel Little', 'mlittle5', 'tS1)?#rnc%s,', 2);
INSERT INTO users (name, username, password, role_id) VALUES ('Perren Cobby', 'pcobby6', 'iP2$=e0{H=Hme%', 2);
INSERT INTO users (name, username, password, role_id) VALUES ('Marna Bezant', 'mbezant7', 'eC3#b+0%,Jy2', 1);
INSERT INTO users (name, username, password, role_id) VALUES ('Jourdan McIleen', 'jmcileen8', 'aS1|d<ayi', 1);
INSERT INTO users (name, username, password, role_id) VALUES ('Tierney Tiptaft', 'ttiptaft9', 'kK0$HLgFx!', 2);
INSERT INTO users (name, username, password, role_id) VALUES ('Urban Brechin', 'ubrechina', 'dE1~8Ur/7''', 2);
INSERT INTO users (name, username, password, role_id) VALUES ('Gayle Chiplen', 'gchiplenb', 'jQ7{ScIcsI', 1);
INSERT INTO users (name, username, password, role_id) VALUES ('Reggis Scoble', 'rscoblec', 'fB7/R''3tLmgau', 2);
INSERT INTO users (name, username, password, role_id) VALUES ('Florida Gomes', 'fgomesd', 'lN8&!LG8Q"2', 1);
INSERT INTO users (name, username, password, role_id) VALUES ('Mella Champneys', 'mchampneyse', 'fJ6~9X0X,#*f=1M', 1);
INSERT INTO users (name, username, password, role_id) VALUES ('Hakim Hocking', 'hhockingf', 'eP4+wqXn$', 1);
INSERT INTO users (name, username, password, role_id) VALUES ('Jeno Betke', 'jbetkeg', 'xI1a5kyjgZF}', 2);
INSERT INTO users (name, username, password, role_id) VALUES ('Katherine Brackley', 'kbrackleyh', 'gA5}(JESwoB', 1);
INSERT INTO users (name, username, password, role_id) VALUES ('Victoir Kilborn', 'vkilborni', 'jB9~<y=h', 1);
INSERT INTO users (name, username, password, role_id) VALUES ('Frederigo Rue', 'fruej', 'bI1=nB}Hi(kD', 2);
INSERT INTO users (name, username, password, role_id) VALUES ('Lucinda Rattenbury', 'lrattenburyk', 'qY3!G#Aw', 2);
INSERT INTO users (name, username, password, role_id) VALUES ('Minne Shears', 'mshearsl', 'xH8&@`>e7', 1);
INSERT INTO users (name, username, password, role_id) VALUES ('Dorothea Kelcher', 'dkelcherm', 'yH9@G8Ya<)Sew@v', 2);
INSERT INTO users (name, username, password, role_id) VALUES ('Reagen Valois', 'rvaloisn', 'pZ6=(7n<3bRv', 1);
INSERT INTO users (name, username, password, role_id) VALUES ('Kyla Hanbury-Brown', 'khanburybrowno', 'bO0~9o/pF,2@J#p4', 1);
INSERT INTO users (name, username, password, role_id) VALUES ('Artie Lafflina', 'alafflinap', 'wV9?~cY=z1M', 1);
INSERT INTO users (name, username, password, role_id) VALUES ('Werner Buddington', 'wbuddingtonq', 'gJ2<(kE2nI', 2);
INSERT INTO users (name, username, password, role_id) VALUES ('Gabrila Klimke', 'gklimker', 'wX2$I,AKz', 2);
INSERT INTO users (name, username, password, role_id) VALUES ('Clarissa Borrie', 'cborries', 'kP9!5Een', 2);
INSERT INTO users (name, username, password, role_id) VALUES ('Anetta Pitrasso', 'apitrassot', 'nC4@I_@TR', 1);
INSERT INTO users (name, username, password, role_id) VALUES ('Katha Skeffington', 'kskeffingtonu', 'qL6"2XCvKe', 1);
INSERT INTO users (name, username, password, role_id) VALUES ('Garald Sigert', 'gsigertv', 'nV7_!=+"JYU', 2);
INSERT INTO users (name, username, password, role_id) VALUES ('Donnell Stuckford', 'dstuckfordw', 'aF0~x"2i', 1);
INSERT INTO users (name, username, password, role_id) VALUES ('Templeton Welds', 'tweldsx', 'eS4{?/as', 1);
INSERT INTO users (name, username, password, role_id) VALUES ('Grantham Whisby', 'gwhisbyy', 'hQ0~|PVYGW/,', 2);
INSERT INTO users (name, username, password, role_id) VALUES ('Galen FitzGeorge', 'gfitzgeorgez', 'fE3<k},l', 1);
INSERT INTO users (name, username, password, role_id) VALUES ('Gael Boulds', 'gboulds10', 'zI2*U|''ku', 1);
INSERT INTO users (name, username, password, role_id) VALUES ('Noland Rahlof', 'nrahlof11', 'sO4$+FK~v5)X6', 2);
INSERT INTO users (name, username, password, role_id) VALUES ('Isabel Spoure', 'ispoure12', 'vQ1~R/r<4*0A', 2);
INSERT INTO users (name, username, password, role_id) VALUES ('Pattin Harrison', 'pharrison13', 'gO3`9Wvi{''ONE', 2);
INSERT INTO users (name, username, password, role_id) VALUES ('Joel Knock', 'jknock14', 'hU7+.I<N$Z_', 2);
INSERT INTO users (name, username, password, role_id) VALUES ('Mariana Caughan', 'mcaughan15', 'mM8.26Fv', 2);
INSERT INTO users (name, username, password, role_id) VALUES ('Rockwell Jancar', 'rjancar16', 'qG9!4T#da0940', 2);
INSERT INTO users (name, username, password, role_id) VALUES ('Lil Laughtisse', 'llaughtisse17', 'cP2*zvuD', 2);
INSERT INTO users (name, username, password, role_id) VALUES ('Charissa Beddow', 'cbeddow18', 'wZ8_@$ft2C6u7"', 2);
INSERT INTO users (name, username, password, role_id) VALUES ('Jacqui Woodcroft', 'jwoodcroft19', 'eF3?m07d8K', 2);
INSERT INTO users (name, username, password, role_id) VALUES ('Marne Worvill', 'mworvill1a', 'qP4''G,Amq_okp(){', 2);
INSERT INTO users (name, username, password, role_id) VALUES ('Averill Kissack', 'akissack1b', 'fM2<rVo|4', 1);
INSERT INTO users (name, username, password, role_id) VALUES ('Brian Yurinov', 'byurinov1c', 'kG2*x8Da!jUU`a', 1);
INSERT INTO users (name, username, password, role_id) VALUES ('Aristotle Hellikes', 'ahellikes1d', 'pN4#CP},', 2);

INSERT INTO reservations(user_id, device_id, start_date, end_date, reservation_status_id) VALUES 
(44, 	4, '2023-01-13',	'2023-01-19', 	4),
(43,	1, 	'2023-02-13',	'2023-02-19', 	4),
(35,	14, '2023-03-13',	'2023-03-19', 	4),
(31,	5, 	'2023-09-26',	'2023-10-15', 	2),
(39, 	16, '2023-09-27',	'2023-10-16', 	2),
(46, 	11, '2023-12-13',	'2023-12-19', 	1),
(40, 	13, '2023-12-03',	'2023-12-05', 	1),
(49, 	14, '2024-01-13',	'2024-01-19', 	1),
(36, 	6, 	'2023-08-13',	'2023-08-19', 	3),
(39, 	7, 	'2023-09-13',	'2023-09-19', 	3),
(26, 	8, '2024-02-13',	'2024-02-19', 	1),
(25, 	3, 	'2024-03-13',	'2024-03-19', 	1),
(27, 	2, 	'2023-09-25',	'2023-10-15', 	2),
(1, 	11, '2023-01-13',	'2023-01-19', 	2),
(34, 	13, '2023-09-21',	'2023-10-10', 	2),
(44, 	18, '2023-09-27',	'2023-10-15', 	2);

