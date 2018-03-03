#include <Keypad.h>
const byte ROWS = 4;
const byte COLS = 3;
char keys[ROWS][COLS] = {
  {'1','2','3'},
  {'4','5','6'},
  {'7','8','9'},
  {'*','0','#'}
};
byte rowPins[ROWS] = { 2, 3, 4, 5 };
byte colPins[COLS] = { 6, 7, 8 }; 
Keypad kpd = Keypad( makeKeymap(keys), rowPins, colPins, ROWS, COLS );
enum state {door_open, standby, door_block, oppened_for_long_time};
state current_state = standby;
const byte VALID_PASS = 2;
char current_passw[4] = {' ', ' ', ' ', ' '};
char correct_passw[VALID_PASS][4] = {
       {'1', '2', '3', '4'},
       {'0', '0', '0', '0'}
};
int passw_pos = 0, wrong_passw = 0, time_block = 0;
long start_open = 0;
boolean passw = false;
void setup(){
  Serial.begin(9600);
  pinMode(9, OUTPUT);
  pinMode(10, OUTPUT);
  pinMode(11, OUTPUT);
  pinMode(12, INPUT_PULLUP);
  paintBlue();
}

void loop(){
  
  char key = 0;
  switch(current_state){
    case standby:
      paintBlue();
      if(digitalRead(12) == LOW){
        passw = false;
        current_state = door_open;
        Serial.println("DOOR OPENED");
        start_open = millis();
      }
      key = kpd.getKey();
      if(key){
        if(key == '#'){
          resetPassw();
          Serial.println("PASSWORD RESET");
          Serial.println("Current password:");
        }else{
          current_passw[passw_pos++] = key;
          printCurrent_passw();
          if(passw_pos == 4){
            if(correctPassw()){
              wrong_passw = 0;
              current_state = door_open;
              passw = true;
              Serial.println("DOOR OPENED");
              start_open = millis();
            }else{
              time_block = 1;
              current_state = door_block;
              Serial.print("WRONG PASSWORD x");
              Serial.println(++wrong_passw);
              Serial.println("DOOR BLOCKED");
            }
            resetPassw();
          }
          if(wrong_passw == 3){
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
          Serial.println("DOOR CLOSED");
        }
      }
      if(digitalRead(12) == HIGH && !passw){
        current_state = standby;
        Serial.println("DOOR CLOSED");
      }
      if((millis() - start_open) >= 30000){
          current_state = oppened_for_long_time;
          Serial.println("DOOR OPPENED FOR A LONG TIME");
      }
      break;
    case door_block:
      paintRed();
      if(time_block == 0){
        current_state = standby;
        Serial.println("DOOR CLOSED");
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
          Serial.println("DOOR CLOSED");
        }
      }
      if(digitalRead(12) == HIGH){
        current_state = standby;
        Serial.println("DOOR CLOSED");
      }
      break;
  }
}

void printCurrent_passw(){
  Serial.print("Current password:\t");
  Serial.print(current_passw[0]);
  Serial.print(" ");
  Serial.print(current_passw[1]);
  Serial.print(" ");
  Serial.print(current_passw[2]);
  Serial.print(" ");
  Serial.print(current_passw[3]);
  Serial.print("\n");
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

void paintRed(){
  digitalWrite(9,LOW);
  digitalWrite(10,HIGH);
  digitalWrite(11,HIGH);
}
void paintGreen(){
  digitalWrite(9,HIGH);
  digitalWrite(10,LOW);
  digitalWrite(11,HIGH);
}
void paintBlue(){
  digitalWrite(9,HIGH);
  digitalWrite(10,HIGH);
  digitalWrite(11,LOW);
}

