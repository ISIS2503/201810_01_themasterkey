#include <Keypad.h>
#include "EEPROM.h"
#define SIZE_BUFFER_DATA 50
#define ROWS 4
#define COLS 3
#define MIN_VOLTAGE 1.2
#define BATTERY_PIN 0  //Analog Pin
#define KEYPAD1 4
#define KEYPAD2 5
#define KEYPAD3 6
#define KEYPAD4 7
#define KEYPAD5 8
#define KEYPAD6 9
#define KEYPAD7 10
#define RED 11
#define GREEN 12
#define BLUE 13
#define BUTTON 14
#define BATTERY_LED 15
#include "WProgram.h"
void setup();
void loop();
void resetPassw();
bool correctPassw();
void battery();
void paintRed();
void paintGreen();
void paintBlue();
void receiveData();
void processData();
const char keys[ROWS][COLS] = {{'1','2','3'},{'4','5','6'},{'7','8','9'},{'*','0','#'}};
byte rowPins[ROWS] = { KEYPAD1, KEYPAD2, KEYPAD3, KEYPAD4 };
byte colPins[COLS] = { KEYPAD5, KEYPAD6, KEYPAD7 };
boolean stringComplete = false;
String inputString = "";
char bufferData [SIZE_BUFFER_DATA];
Keypad kpd = Keypad( makeKeymap(keys), rowPins, colPins, ROWS, COLS );
enum state {door_open, standby, door_block, oppened_for_long_time};
state current_state = standby;
const byte VALID_PASS = 20;
char current_passw[4] = {' ', ' ', ' ', ' '};
int passw_pos = 0, wrong_passw = 0, time_block = 0;
long start_open = 0;
boolean passw = false;
double batteryCharge;
bool ch_wr = false;
int possPass = 0;
void setup(){
  Serial.begin(9600);
  pinMode(RED, OUTPUT);
  pinMode(GREEN, OUTPUT);
  pinMode(BLUE, OUTPUT);
  pinMode(BATTERY_LED,OUTPUT);
  
  paintBlue();
}
void loop(){
  battery();
  receiveData();
  processData();
  char key = 0;
  switch(current_state){
    case standby:
      paintBlue();
      if(digitalRead(BUTTON) == LOW){
        passw = false;
        current_state = door_open;
        Serial.println(1);
        start_open = millis();
      }
      key = kpd.getKey();
      if(key)
        if(key == '#')
          resetPassw();
        else{
          current_passw[passw_pos++] = key;
          if(passw_pos == 4){
            if(correctPassw()){
              wrong_passw = 0;
              current_state = door_open;
              passw = true;
              Serial.println(1);
              start_open = millis();
            }else{
              time_block = 1;
              current_state = door_block;
              ++wrong_passw;
            }
            resetPassw();
          }
          if(wrong_passw == 3){
            Serial.println(2);
            time_block = 30;
            current_state = door_block;
            wrong_passw = 0;
          }
        }
      break;
    case door_open:
      paintGreen();
      key = kpd.getKey();
      if(key)
        if(key == '*' && passw)
          current_state = standby;
      if(digitalRead(BUTTON) == HIGH && !passw)
        current_state = standby;
      if((millis() - start_open) >= 30000){
          current_state = oppened_for_long_time;
          Serial.println(3);
      }
      break;
    case door_block:
      paintRed();
      if(time_block == 0)
        current_state = standby;
      else{
        delay(1000);
        time_block--;
      }
      break;
    case oppened_for_long_time:
      paintRed();
      key = kpd.getKey();
      if(key)
        if(key == '*')
          current_state = standby;
      if(digitalRead(BUTTON) == HIGH && !passw)
        current_state = standby;
      if(digitalRead(BUTTON) == LOW && passw)
        current_state = standby;
      break;
  }
}
void resetPassw(){
    passw_pos = 0;
    current_passw[0] = ' ';
    current_passw[1] = ' ';
    current_passw[2] = ' ';
    current_passw[3] = ' ';
}
bool correctPassw(){
  bool ok = true;
  for (int i = 0; i < 4*VALID_PASS; i++) {
    ok = (current_passw[(i%4)] == EEPROM.read(i));
    if (i % 4 == 0)
      if (ok) return ok;
      else ok = true;
  }
  return false;
}
void battery(){
  batteryCharge = (analogRead(BATTERY_PIN)*5.4)/1024;
  if(batteryCharge <= MIN_VOLTAGE) {
    digitalWrite(BATTERY_LED,HIGH);
    Serial.println(4);
  }
  else 
    digitalWrite(BATTERY_LED,LOW);
}
void paintRed(){
  digitalWrite(RED,LOW);
  digitalWrite(GREEN,HIGH);
  digitalWrite(BLUE,HIGH);
}
void paintGreen(){
  digitalWrite(RED,HIGH);
  digitalWrite(GREEN,LOW);
  digitalWrite(BLUE,HIGH);
}
void paintBlue(){
  digitalWrite(RED,HIGH);
  digitalWrite(GREEN,HIGH);
  digitalWrite(BLUE,LOW);
}
void receiveData() {
  while (Serial.available()) {
    char inChar = (char)Serial.read();
    inputString += inChar;
    if (inChar == '\n') {
      inputString.toCharArray(bufferData, SIZE_BUFFER_DATA);
      stringComplete = true;
    }
  }
}
void processData() {
  if (stringComplete) {
    Serial.println(inputString);
    stringComplete = false;
    if(!ch_wr){
      possPass = 4*inputString.toInt();
      ch_wr = true;
    }else
       for (int i = 0; i < 4; i++)
         EEPROM.write(possPass + i, ((int)(inputString.charAt(i) - '0')));
    inputString = "";
  }
}





