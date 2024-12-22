/*
 [The "BSD licence"]
 Copyright (c) 2017 Ivan de Jesus Deras (ideras@gmail.com)
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:
 1. Redistributions of source code must retain the above copyright
    notice, this list of conditions and the following disclaimer.
 2. Redistributions in binary form must reproduce the above copyright
    notice, this list of conditions and the following disclaimer in the
    documentation and/or other materials provided with the distribution.
 3. The name of the author may not be used to endorse or promote products
    derived from this software without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package mc;

import purejavacomm.*;
import mc.MimasV2ConfigDownloader;
import mc.TerminalProgress;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.HelpFormatter;
import java.util.Enumeration;

/**
 *
 * @author guigui220d
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
      System.out.println("Hello from MimasV2Configurator, terminal edition (mv2c)");

      // CLI options
      Options options = new Options();
      options.addOption("h", "help", false, "Show help");
      options.addOption("l", "list", false, "List serial com port names");
      options.addOption("v", "verify", false, "Verify flash after programming");
      options.addOption("f", "fast", false, "Use 115200 bauds instead of 19200");
      options.addOption("i", "bitstream", true, "Bistream file path");
      options.addOption("o", "serial-port", true, "Serial com port name");
      // TODO: option to show serial port list

      boolean verify = false;
      boolean fast = false;
      String bitPath = null;
      String devPath = null;
      
      // Parse ClI arguments
      CommandLineParser parser = new DefaultParser();
      try {
        CommandLine cmd = parser.parse(options, args);

        if (cmd.hasOption("h")) {
          HelpFormatter formatter = new HelpFormatter();
          formatter.printHelp("mv2c [-f] [-v] -i bitstream.bin -o ttyACM0", options);
          return;
        }

        if (cmd.hasOption("l")) {
          listSerialPorts();
          return;
        }

        if (cmd.hasOption("v"))
          verify = true;

        if (cmd.hasOption("f"))
          fast = true;

        if (cmd.hasOption("i")) {
          bitPath = cmd.getOptionValue("i");
        } else {
          System.out.println("No bitstream file provided. Use -h for help.");
          return;
        }

        if (cmd.hasOption("o")) {
          devPath = cmd.getOptionValue("o");
        } else {
          System.out.println("No device file selected. Use -h for help.");
          return;
        }
      } catch (ParseException e) {
        System.err.println("Error parsing CLI arguments: " + e.getMessage());
        return;
      }

      // Get port
      CommPortIdentifier serialPortID = null;
      try {
        serialPortID = CommPortIdentifier.getPortIdentifier(devPath);
      } catch (NoSuchPortException e) {
        System.out.println("Error opening comm port, no such serial port.");
        return;
      }

      SerialPort serialPort = null;
      try {
        serialPort = (SerialPort)serialPortID.open("MimasV2ConfigDownloader", 2000);
        serialPort.enableReceiveTimeout(2000);
        serialPort.setSerialPortParams(fast ? 115200 : 19200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);      
      } catch (PortInUseException e) {
        System.out.println("Error opening comm port, port already in use or no permission.");
        return;
      } catch (UnsupportedCommOperationException e) {
        System.out.println("Error opening comm port, unsupported operaion.");
        serialPort.close();
        return;
      }
      
      // Start process
      TerminalProgress progress = new TerminalProgress();
      MimasV2ConfigDownloader configDownloader = new MimasV2ConfigDownloader(serialPort, bitPath, progress, verify);

      if (configDownloader.boardIsMimasV2()) {
        System.out.println("Identified Mimas V2 board! Continuing...");
      } else {
        System.out.println("Did not recognize Mimas V2 board, did you choose the right port?");
        return;
      }

      if (!bitPath.endsWith(".bin"))
        System.err.println("Warning: bitstream file doesn't end with .bin, are you sure you are using the right file?");

      configDownloader.run();

      System.out.println("Done!");

      serialPort.close();
    }        

    // Print a list of serial ports in the terminal
    private static void listSerialPorts() {
        Enumeration ports = CommPortIdentifier.getPortIdentifiers();
        System.out.println("List of available com port identifiers:");
        while (ports.hasMoreElements()) {
            CommPortIdentifier port = (CommPortIdentifier) ports.nextElement();
            switch (port.getPortType()) {
                case CommPortIdentifier.PORT_SERIAL:
                    System.out.println(" - " + port.getName());
                    break;
                default:
                    break;
            }
        }
    }
}

