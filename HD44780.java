/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.yenshuli.i2c.module;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import java.io.IOException;

/**
 *
 * @author rottenCoriander
 */
public class HD44780 {
    
    
    private static boolean verbose = "true".equals(System.getProperty("hd44780.verbose", "false"));
    
    private I2CBus bus;
    private I2CDevice device;
       
    
    public HD44780(int address, int BusNumber) throws I2CFactory.UnsupportedBusNumberException {
		try {
			// Get i2c bus
			bus = I2CFactory.getInstance(BusNumber); // Depends onthe RaspberryPI version
                        
			if (verbose) {
				System.out.println("Connected to i2cbus");
			}
			// Get device
			device = bus.getDevice(address);
			if (verbose) {
				System.out.println("Connected to Slave Device.");
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
    
    public void close() {
		try {
			this.bus.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
    
    public int read() throws Exception {
		int r = device.read();
		return r;
	}
    
    public void write(byte b) throws Exception {
		device.write(b);
	}

    private static void delay(float d){
		try {
			Thread.sleep((long) (d * 1_000));
		} catch (Exception ex) {
		}
	}    
    
    public void init() throws Exception{
        //4 bit Mode 
            write((byte) 0x2C);
            write((byte) 0x28);
            Thread.sleep(250);
            //4bit, 2ROW 5*8 
            write((byte) 0x2C);
            write((byte) 0x28);
            write((byte) 0x8C);
            write((byte) 0x88);
            Thread.sleep(250);
            //Show Cousor
            write((byte) 0x0C);
            write((byte) 0x08);
            write((byte) 0xFC);
            write((byte) 0xF8);
            Thread.sleep(250);
            //Clear
            write((byte) 0x0C);
            write((byte) 0x08);
            write((byte) 0x1C);
            write((byte) 0x18);
            Thread.sleep(250);
    }
    
    public void clear() throws Exception{
        write((byte) 0x0C);
        write((byte) 0x08);
        write((byte) 0x1C);
        write((byte) 0x18);
        Thread.sleep(250);
    }
    public void setRow0() throws Exception{
        write((byte) 0x8C);
        write((byte) 0x88);
        write((byte) 0x0C);
        write((byte) 0x08);
        Thread.sleep(250);
    }
    public void setRow1() throws Exception{
        write((byte) 0xCC);
        write((byte) 0xC8);
        write((byte) 0x0C);
        write((byte) 0x08);
        Thread.sleep(250);
    
    }
    public void setRow2() throws Exception{
        write((byte) 0x9C);
        write((byte) 0x98);
        write((byte) 0x4C);
        write((byte) 0x48);
        Thread.sleep(250);
    }
    public void setRow3() throws Exception{
        write((byte) 0xDC);
        write((byte) 0xD8);
        write((byte) 0x6C);
        write((byte) 0x68);
        Thread.sleep(250);
    }
    
    public void writeByte(byte b) throws Exception{
        write(b);
    }
    
    public void writeChar(byte c) throws Exception{
        
        write((byte) ((c & 0xF0) | 0x0D));
        write((byte) ((c & 0xF0) | 0x09));
        write((byte) (((c & 0x0F) << 4) | 0x0D));
        write((byte) (((c & 0x0F) << 4) | 0x09));
        
        
    }
    
    public void writeChar(char c) throws Exception{
        write((byte) (((byte)c & (byte)0xF0) | (byte)0x0D));
        write((byte) (((byte)c & (byte)0xF0) | (byte)0x09));
        write((byte) ((((byte)c & (byte)0x0F) << 4) | (byte)0x0D));
        write((byte) ((((byte)c & (byte)0x0F) << 4) | (byte)0x09));
       
        
    }
    
}

