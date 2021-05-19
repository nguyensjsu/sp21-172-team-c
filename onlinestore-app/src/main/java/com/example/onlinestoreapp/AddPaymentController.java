package com.example.onlinestoreapp;

import javax.validation.Valid;

import com.example.springcybersource.AuthRequest;
import com.example.springcybersource.AuthResponse;
import com.example.springcybersource.CaptureRequest;
import com.example.springcybersource.CaptureResponse;
import com.example.springcybersource.CyberSourceAPI;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.util.Optional;
import java.time.*; 
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64.Encoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/add")
public class AddPaymentController {  

    private static boolean DEBUG = true;

    @Value("${cybersource.apihost}") String apiHost;
    @Value("${cybersource.merchantkeyid}") String merchantKeyId;
    @Value("${cybersource.merchantsecretkey}") String merchantsecretKey;
    @Value("${cybersource.merchantid}") String merchantId;

    private CyberSourceAPI api = new CyberSourceAPI();

    @Getter
    @Setter
    class Message {
        private String msg;
        public Message(String m) { msg = m;}
    }

    class ErrorMessages {
        private ArrayList<Message> messages = new ArrayList<Message>() ;
        public void add( String msg ) { messages.add(new Message(msg) ) ; }
        public ArrayList<Message> getMessages() { return messages ; }
        public void print() {
            for ( Message m : messages ) {
                System.out.println( m.msg );
            }
        }
    }

    @Autowired
    private AddPaymentRepository repository;

    @GetMapping
    public String getAction( @ModelAttribute("command") AddPaymentCommand command, 
                            Model model) {

        return "addpayment";

    }

    @PostMapping
    public String postAction(@Valid @ModelAttribute("command") AddPaymentCommand command,  
                            @RequestParam(value="action", required=true) String action,
                            Errors errors, Model model, HttpServletRequest request) {
                                    
        log.info( "Action: " + action ) ;
        log.info( "Command: " + command ) ;

        CyberSourceAPI.setHost(apiHost);
        CyberSourceAPI.setKey(merchantKeyId);
        CyberSourceAPI.setSecret(merchantsecretKey);
        CyberSourceAPI.setMerchant(merchantId);

        CyberSourceAPI.debugConfig();
    
        ErrorMessages msgs = new ErrorMessages();

        //Check for errors
        boolean hasErrors = false;
        if(command.firstname().equals("Select"))
        {
            hasErrors = true; 
            msgs.add("Please Select A Card Type.");
        }                        
        if(command.lastname().equals(""))
        {
            hasErrors = true; 
            msgs.add("Last Name Required.");
        }
        if(command.address().equals(""))
        {
            hasErrors = true; 
            msgs.add("Address Required.");
        }                     
        if(command.city().equals(""))
        {
            hasErrors = true; 
            msgs.add("City Required.");
        }                        
        if(command.state().equals("Select"))
        {
            hasErrors = true;
            msgs.add("Please Select A State.");
        }    
        if(command.zip().equals(""))
        {
            hasErrors = true; 
            msgs.add("Zip Required.");
        }    
        if(command.phone().equals(""))
        {
            hasErrors = true; 
            msgs.add("Phone Required.");
        }                               
        if(command.cardnum().equals(""))
        {
            hasErrors = true; 
            msgs.add("Credit Card Number Required.");
        }                        
        if(command.cardexpmon().equals(""))
        {
            hasErrors = true; 
            msgs.add("Credit Card Expiration Month Required.");
        }                        
        if(command.cardexpyear().equals(""))
        {
            hasErrors = true; 
            msgs.add("Credit Card Expiration Year Required.");
        }                        
        if(command.cardcvv().equals(""))
        {
            hasErrors = true; 
            msgs.add("Credit Card CVV Required.");
        }                        
        if(command.email().equals(""))
        {
            hasErrors = true; 
            msgs.add("Email Address Required.");
        }    
        
        //regex validation
        if(!command.zip().matches("\\d{5}"))
        {
            hasErrors = true;
            msgs.add("Invalid Zip Code");
        }
        if(!command.phone().matches("[(]\\d{3}[)] \\d{3}-\\d{4}"))
        {
            hasErrors = true;
            msgs.add("Invalid Phone Number. Please use the following format: (444) 444-4444");
        }
        if(!command.cardnum().matches("\\d{4}-\\d{4}-\\d{4}-\\d{4}"))
        {
            hasErrors = true;
            msgs.add("Invalid Card Number. Please use the following format: 4444-4444-4444-4444");
        }
        if(!command.cardexpyear().matches("\\d{4}"))
        {
            hasErrors = true;
            msgs.add("Invalid Card Expire Year");
        }
        if(!command.cardexpmon().matches("\\d{2}"))
        {
            hasErrors = true;
            msgs.add("Invalid Card Expire Month");
        }
        if(!command.cardcvv().matches("\\d{3}"))
        {
            hasErrors = true;
            msgs.add("Invalid Card CVV");
        }

        if(hasErrors)
        {
            msgs.print();
            model.addAttribute("messages", msgs.getMessages());
            return "addpayment";
        }

        //Process payment
        int min = 1239871;
        int max = 9999999;
        int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
        String order_num = String.valueOf(random_int);
        AuthRequest auth = new AuthRequest();
        auth.reference = order_num;
        auth.billToFirstName = command.firstname();
        auth.billToLastName = command.lastname();
        auth.billToAddress = command.address();
        auth.billToCity = command.city();
        auth.billToState = command.state();
        auth.billToZipCode = command.zip();
        auth.billToPhone = command.phone();
        auth.billToEmail = command.email();
        auth.transactionAmount = "172.00";
        auth.transactionCurrency = "USD";
        auth.cardNumnber = command.cardnum();
        auth.cardExpMonth = command.cardexpmon();
        auth.cardExpYear = command.cardexpyear();
        auth.cardCVV = command.cardcvv();
        auth.cardType = CyberSourceAPI.getCardType(auth.cardNumnber);
        if(auth.cardType.equals("ERROR")) {
            System.out.print("Unsupported Credit Card Type.");
            model.addAttribute("message", "Unsupported Credit Card Type.");
            return "addpayment";
        }

        boolean authValid = true;
        AuthResponse authResponse = new AuthResponse();
        System.out.println("\n\nAuth Request: " + auth.toJson());
        authResponse = api.authorize(auth);
        System.out.println("\n\nAuth Response: " + authResponse.toJson());
        if(!authResponse.status.equals("AUTHORIZED")) {
            authValid = false;
            System.out.println(authResponse.message);
            model.addAttribute("message", authResponse.message);
            return "addpayment";
        }

        boolean captureValid = true;
        CaptureRequest capture = new CaptureRequest();
        CaptureResponse captureResponse = new CaptureResponse();
        if(authValid)
        {
            capture.reference = order_num;
            capture.paymentId = authResponse.id;
            capture.transactionAmount = "172.00";
            capture.transactionCurrency = "USD";
            System.out.println("\n\nCapture Request: " + capture.toJson());
            captureResponse = api.capture(capture);
            System.out.println("\n\nCapture Response: " + captureResponse.toJson());
            if(!captureResponse.status.equals("PENDING")) 
            {
                captureValid = false;
                System.out.println(captureResponse.message);
                model.addAttribute("message", captureResponse.message);
                return "addpayment";
            }
        }
      
        /* Render View */
        if(authValid && captureValid)
        {
            command.setOrderNumber(order_num);
            command.setTransactionAmount("172.00");
            command.setTransactionCurrency("USD");
            command.setAuthId(authResponse.id);
            command.setAuthStatus(authResponse.status);
            command.setCaptureId(captureResponse.id);
            command.setCaptureStatus(captureResponse.status);

            repository.save(command);

            System.out.println("Payment Method Saved") ;
            model.addAttribute( "message", "Payment Method Saved" ) ;
        }

        return "updatedstarbuckcards";
    }
  }