# Automated Plant Watering System

This is a self-watering plant system created using Java and C++. It utilizes a combination of hardware components such as a Mosfet, water pump, moisture sensor, external power supply, and an ATmega320p-based Seeeduino Lotus board to automate the watering process for your plants.

## Hardware Requirements
* Mosfet
* Water pump
* External power supply (9v)
* Moisture sensor
* ATmega320p-based Seeeduino Lotus board

## Software Requirements
* Java
* C++ (Arduino IDE v1.8.13 or later)

## Setup Diagram
![Alt text](setup_diagram.png?raw=true "Setup")

# Usage
1.  Clone the repository using  **`git clone https://github.com/Kamsi-idimogu/Automated-Plant-Watering-System.git`**
2.  Connect the hardware components as specified in the code and setup diagram.
3. Upload the code to the Seeeduino Lotus board (Grove board).
4. Run Main.java to track the condition of the soil
5. The system should now be operational and will automatically water the plants based on the moisture level readings from the sensor.