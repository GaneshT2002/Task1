package com.example.Thinclient.service;

import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import org.springframework.stereotype.Service;

@Service
public class PrinterService {

    private static final String PRINTER_IP = "192.168.2.67";
    private static final int PRINTER_PORT = 9100;

    // Function to send a print command with custom text
    public static boolean printReceipt(String text) {
        try {
            Socket socket = new Socket(PRINTER_IP, PRINTER_PORT);
            OutputStream outputStream = socket.getOutputStream();

            // ESC/POS command to initialize the printer
            byte[] initPrinter = new byte[]{0x1B, 0x40}; // ESC @ (Initialize the printer)
            outputStream.write(initPrinter);

            // Text to be printed
            String receiptText = text+"\n\n\nAdditional Text\n";
            outputStream.write(receiptText.getBytes(StandardCharsets.US_ASCII));

            // ESC/POS command to cut the paper
            byte[] cutPaper = new byte[]{0x1D, 'V', 1}; // GS V (cut command)
            outputStream.write(cutPaper);

            outputStream.flush();
            outputStream.close();
            socket.close();

            System.out.println("Receipt printed successfully.");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error: Unable to print the receipt.");
            return false;
        }
    }


    // Function to send a command to open the cash drawer
    public boolean openCashDrawer() {
        try (Socket socket = new Socket(PRINTER_IP, PRINTER_PORT);
             OutputStream outputStream = socket.getOutputStream()) {

            // Open Cash Drawer
            byte[] openDrawer = new byte[]{0x1B, 0x70, 0x00, 0x19, (byte) 0xFA};
            outputStream.write(openDrawer);

            outputStream.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
