// package com.example.backoffice_app;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.server.ResponseStatusException;
// import org.springframework.http.HttpStatus;

// @Controller
// public class HomeController {
    
//   @GetMapping("/")
//   public String greetingForm(Model model) {
//     return "index";
//   }

//   @PostMapping("/")
//   public String greetingSubmit(@ModelAttribute String customerID, Model model) {
//     model.addAttribute("customerID", customerID);
//       Customer customer = CustomerController.customerRepository.findByCustomerID(customerID);
//       if (customer == null) {
//         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error. Customer not found!");
//       }
//       else {
//         String firstName = customer.getFirstName();
//         model.addAttribute("name", firstName);
//       }
//       return "backoffice";
//   }

// }
