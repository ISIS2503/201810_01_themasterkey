#include <Keypad.h>
#define SIZE_BUFFER_DATA       50
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
const byte ROWS = 4;
const byte COLS = 3;
const double MIN_VOLTAGE = 1.2;
const int BATTERY_LED = 16;
const int BATTERY_PIN = 0;
double batteryCharge;
boolean     stringComplete = false;
String      inputString = "";
char        bufferData [SIZE_BUFFER_DATA];
char keys[ROWS][COLS] = {
  {'1','2','3'},
  {'4','5','6'},
  {'7','8','9'},
  {'*','0','#'}
};
byte rowPins[ROWS] = { 4, 5, 6, 7 };
byte colPins[COLS] = { 8, 9, 10 };
Keypad kpd = Keypad( makeKeymap(keys), rowPins, colPins, ROWS, COLS );
enum state {door_open, standby, door_block, oppened_for_long_time};
state current_state = standby;
const byte VALID_PASS = 20;
char current_passw[4] = {' ', ' ', ' ', ' '};
char correct_passw[VALID_PASS][4] = {
       {'1', '2', '3', '4'},
       {'0', '0', '0', '0'},
       {'0', '0', '0', '0'},
       {'0', '0', '0', '0'},
       {'0', '0', '0', '0'},
       {'0', '0', '0', '0'},
       {'0', '0', '0', '0'},
       {'0', '0', '0', '0'},
       {'0', '0', '0', '0'},
       {'0', '0', '0', '0'},
       {'0', '0', '0', '0'},
       {'0', '0', '0', '0'},
       {'0', '0', '0', '0'},
       {'0', '0', '0', '0'},
       {'0', '0', '0', '0'},
       {'0', '0', '0', '0'},
       {'0', '0', '0', '0'},
       {'0', '0', '0', '0'},
       {'0', '0', '0', '0'},
       {'0', '0', '0', '0'},
};
int passw_pos = 0, wrong_passw = 0, time_block = 0;
long start_open = 0;
boolean passw = false;
void setup(){
  Serial.begin(9600);
  pinMode(11, OUTPUT);
  pinMode(12, OUTPUT);
  pinMode(13, OUTPUT);
  pinMode(14, INPUT);
  pinMode(BATTERY_LED,OUTPUT);
  pinMode(BATTERY_PIN,INPUT);
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
      if(digitalRead(14) == LOW){
        passw = false;
        current_state = door_open;
        Serial.println(1);
        start_open = millis();
      }
      key = kpd.getKey();
      if(key){
        if(key == '#'){
          resetPassw();
          //Serial.println("PASSWORD RESET");
          //Serial.println("Current password:");
        }else{
          current_passw[passw_pos++] = key;
          //printCurrent_passw();
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
              //Serial.print("WRONG PASSWORD x");
              //Serial.println(++wrong_passw);
              //Serial.println("DOOR BLOCKED");
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
      }
      break;
    case door_open:
      paintGreen();
      key = kpd.getKey();
      if(key){
        if(key == '*' && passw){
          current_state = standby;
          //Serial.println("DOOR CLOSED");
        }
      }
      if(digitalRead(14) == HIGH && !passw){
        current_state = standby;
        //Serial.println("DOOR CLOSED");
      }
      if((millis() - start_open) >= 30000){
          current_state = oppened_for_long_time;
          Serial.println(3);
      }
      break;
    case door_block:
      paintRed();
      if(time_block == 0){
        current_state = standby;
        //Serial.println("DOOR CLOSED");
      }else{
        delay(1000);
        time_block--;
      }
      break;
    case oppened_for_long_time:
      paintRed();
      key = kpd.getKey();
      if(key){
        if(key == '*'){
          current_state = standby;
          //Serial.println("DOOR CLOSED");
        }
      }
      if(digitalRead(14) == HIGH && !passw){
        current_state = standby;
        //Serial.println("DOOR CLOSED");
      }
      if(digitalRead(14) == LOW && passw){
        current_state = standby;
        //Serial.println("DOOR CLOSED");
      }
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
  bool flag = true;
  for(int i = 0; i < VALID_PASS; i++){
    for(int j = 0; j < 4; j++){
      flag = flag && (current_passw[j] == correct_passw[i][j]);
    }
    if(flag) return flag;
    flag = true;
  }
  return false;
}
void battery(){
  batteryCharge = (analogRead(BATTERY_PIN)*5.4)/1024;
  if(batteryCharge <= MIN_VOLTAGE) {
    digitalWrite(BATTERY_LED,HIGH);
    Serial.println(4);
  }
  else {
    digitalWrite(BATTERY_LED,LOW);
  }
}
void paintRed(){
  digitalWrite(11,LOW);
  digitalWrite(12,HIGH);
  digitalWrite(13,HIGH);
}
void paintGreen(){
  digitalWrite(11,HIGH);
  digitalWrite(12,LOW);
  digitalWrite(13,HIGH);
}
void paintBlue(){
  digitalWrite(11,HIGH);
  
  digitalWrite(12,HIGH);
  digitalWrite(13,LOW);
}
void receiveData() {
  while (Serial.available()) {
    // get the new byte:
    char inChar = (char)Serial.read();
    // add it to the inputString:
    inputString += inChar;
    if (inChar == '\n') {
      inputString.toCharArray(bufferData, SIZE_BUFFER_DATA);
      stringComplete = true;
    }
  }
}
bool ch_wr = false;
int possPass = 0;
void processData() {
  if (stringComplete) {
    stringComplete = false;
    if(!ch_wr){
      possPass = inputString.toInt();
      ch_wr = true;
      Serial.print("Posicion: ");
      Serial.print(possPass);
    }else{
      char pas[4];
      inputString.toCharArray(pas,4);
      for (int i = 0; i < 4; i++)
        correct_passw[possPass][i] = pas[i] - '0';
      ch_wr = false;
      Serial.print(correct_passw[possPass][0]);
      Serial.print(" ");
      Serial.print(correct_passw[possPass][1]);
      Serial.print(" ");
      Serial.print(correct_passw[possPass][2]);
      Serial.print(" ");
      Serial.print(correct_passw[possPass][3]);
      Serial.print("\n");
    }
    Serial.println(inputString);
    //inputString = "";
  }
}



