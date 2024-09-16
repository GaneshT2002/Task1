package com.example.Thinclient.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.Thinclient.service.PrinterService;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class Thinclientcontroller {

    @Autowired
    private PrinterService service;

    @PostMapping("/print")
    public ResponseEntity<String> printReceipt(@RequestBody PrintRequest printRequest) {
        boolean success = service.printReceipt(printRequest.getText());
        if (success) {
            return ResponseEntity.ok("Print command executed successfully");
        } else {
            return ResponseEntity.status(500).body("Failed to execute print command");
        }
    }

    @GetMapping("/cash-drawer")
    public ResponseEntity<String> getCashDrawer() {
        boolean success = service.openCashDrawer();
        if (success) {
            return ResponseEntity.ok("Cash Drawer is Opened");
        } else {
            return ResponseEntity.status(500).body("Failed to open cash drawer");
        }
    }
}

// Additional class to handle incoming print requests
class PrintRequest {
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
