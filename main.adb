with Ada.Text_IO;
with Ada.Numerics.Float_Random;
use Ada.Text_IO;

procedure Main is

   capacity: constant Integer := 5;
   type Cars_on_board is array(1..capacity) of Integer;

   task Ferry is
      entry Embark_A_Car(carId: in Integer);
      entry Disembark_A_Car;
   end Ferry;

   task body Ferry is
      cars: Cars_on_board;
      endIndex: Integer := 0;
      wait_count: Integer := 0;
      max_wait_count: Integer := 5;
      counter: Integer := 0;
      I: Integer := 0;
      startIndex: Integer := 1;
   begin
      loop
         select
            when counter < capacity => accept Embark_A_Car (carId : in Integer) do
                  Put_Line("Auto o id " & Integer'Image(carId) & " wjezdza na prom ");
                  cars(startIndex) := carId;
                  startIndex := (startIndex mod capacity) + 1;
                  endIndex := endIndex + 1;
                  counter := counter + 1;
               end Embark_A_Car;
         or
            when counter = capacity => accept Disembark_A_Car do
                  Put_Line("Na promie nie ma juz wiecej miejsc - odplywamy...");
                  delay 0.2;
                  for I in Integer range 1..endIndex loop
                     Put_Line("Auto o id " & Integer'Image(cars(I)) & " zjezdza z promu");
                  end loop;
                  endIndex := 0;
                  startIndex := 1;
                  counter := 0;
                  Put_Line("Prom plynie na drugi brzeg po kolejne auta");
                  delay 0.2;
               end Disembark_A_Car;
         or
            delay 3.0;
            if counter <= 0 then
               wait_count := wait_count + 1;
               if wait_count > max_wait_count then
                  raise Program_Error with "Koniec kursowania!";
               end if;
               Put_Line("Czas oczekiwania minal - prom kontynuuje oczekiwanie na samochody");
               null;
            elsif  counter > 0 then
               Put_Line("Plyniemy bez pelnego oblozenia " & Integer'Image(counter) & "  samochody na pokladzie");
               startIndex := 1;
               counter := 0;
            end if;
         end select;
      end loop;
   end Ferry;
   
   task Car;
   task body Car is
      use Ada.Numerics.Float_Random;
      Seed: Generator;
      id: Integer := 1;
   begin
      loop
         delay Duration(Random(Seed) * Float(id) / 4.0);
         Ferry.Embark_A_Car(id);
         id := id + 1;
      end loop;
   end Car;     
   
   task Transporter;
   task body Transporter is
   begin
      loop
         Ferry.Disembark_A_Car;
      end loop;
   end Transporter;

begin
   null;

end Main;
