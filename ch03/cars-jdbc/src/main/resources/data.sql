delete from Taco_Order_Tacos;
delete from Taco_Ingredients;
delete from Taco;
delete from Taco_Order;

delete from Ingredient;
insert into Ingredient (id, name, type) 
                values ('SUBW', 'Subwoofers', 'Audio');
insert into Ingredient (id, name, type) 
                values ('AMPL', 'Amplifiers', 'Audio');
insert into Ingredient (id, name, type) 
                values ('SEHA', 'Seat harnesses', 'Interior');
insert into Ingredient (id, name, type) 
                values ('FIEX', 'Fire extinguishers', 'Interior');
insert into Ingredient (id, name, type) 
                values ('SPPL', 'Spark plugs', 'Engine');
insert into Ingredient (id, name, type) 
                values ('MAFL', 'Mass air flow', 'Engine');
insert into Ingredient (id, name, type) 
                values ('SPRI', 'Springs', 'Suspension');
insert into Ingredient (id, name, type) 
                values ('SHAB', 'Shock absorbers', 'Suspension');
insert into Ingredient (id, name, type) 
                values ('RORE', 'Rolling resistance', 'Tires');
insert into Ingredient (id, name, type) 
                values ('HADL', 'Handling', 'Tires');
