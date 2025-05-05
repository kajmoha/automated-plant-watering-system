#include <Arduino.h>
#include <U8x8lib.h>

#define MOSFET 2
#define REDLED 4
#define MOISTURE A1
#define WET_THRESH 550 

auto display = U8X8_SSD1306_128X64_NONAME_HW_I2C(U8X8_PIN_NONE);


int moistureValue = 0;
unsigned long st = 0;

const long et = 750;

void setup() {
 Serial.begin(9600);
  pinMode(MOSFET, OUTPUT);
  pinMode(REDLED, OUTPUT);
  pinMode(LED_BUILTIN, OUTPUT);
  
  digitalWrite(MOSFET, LOW);
  

  display.begin(); // starts up OLED display
  display.setFlipMode(1); 
  display.clearDisplay();
  display.setFont(u8x8_font_7x14B_1x2_r);

}

void loop() {

  automaticMoistureDetector();

  unsigned long ct = millis();

if(ct - st >= et){
  st = ct;
  soilMoistureSensorData();
}
}


void automaticMoistureDetector(){
   display.setFont(u8x8_font_7x14B_1x2_r);
   display.setCursor(0,0);
  
  
  const auto receivedData = Serial.read();
  char buf[16];
  sprintf(buf, "%03d", receivedData);

  
  moistureValue = analogRead(MOISTURE);

/* Displays Moisture condition on OLED, ~750 when dry, ~550 when wet*/
    
   if(moistureValue < WET_THRESH && receivedData == 8){


    digitalWrite(MOSFET, HIGH);
    digitalWrite(LED_BUILTIN, HIGH);
    delay(2000);
   
    }
    else if(moistureValue < WET_THRESH){

      digitalWrite(MOSFET, LOW);
      digitalWrite(LED_BUILTIN, LOW);
         
      

      display.print("soil is wet");
      display.setCursor(0,5);
      display.print("              ");
       
    }
    else{
      digitalWrite(MOSFET, HIGH);
      digitalWrite(LED_BUILTIN, HIGH);

    display.print("soil is dry");
    display.setCursor(0,5);
    display.print(" Watering.... ");
       }
       
 if(receivedData  < 6){
      digitalWrite(REDLED, LOW);
    } 
    else{
      digitalWrite(REDLED, HIGH);
     
    }       
}

void soilMoistureSensorData() {
    const auto value = analogRead(A1);
    const byte data[] = {0,0, highByte(value), lowByte(value)};
    Serial.write(data, 4); 
    Serial.println(); 
 
    }

   
