# Team C Journal 
## Architecture Diagram

## Cashier's App

### Screenshots

#### Running Locally

![Cashier's App](cashiers-app-images/cashiersApp.png)

![Cashier's App 2](cashiers-app-images/cashiersApp2.png)

#### GKE Deployment

![Cashier's App / API Workloads](cashiers-app-images/cashiersApiWorkloads.png)

![Cashier's App / API Services](cashiers-app-images/cashiersApiServicesWithKong.png)

![Cashier's App / API Ingresses](cashiers-app-images/cashiersApiIngresses.png)

### Discussion

- For the Starbucks Cashier's Application, we enabled the barista to have easy access to customizing an order for the customer. They can choose from five different drink options, five different milk choices, and four different drink sizes. The first image above shows exactly this, which is what the barista will see upon page landing. The barista can also quickly set at which location the order is being made, by using the store selector placed at the top-right corner of the fixed navigation bar.
- After the barista has finished adding an order to the register, they will see what is shown in the second screenshot above. From here, the barista has three options:
  1. Get Order
     - This is used to get the active order at there selected store register, if available. Upon an "Add to Order" action, this action is performed automatically. If an order had been added previously, then the barista will need to click this button to get the active order.
  2. Place Order
     - When the barista is ready to complete the transaction, they will need to simply input the customer's Starbucks Card number, and then click this button to finalize the order.
  3. Delete Order
     - If the barista would like to clear the active order for the selected register, then all they have to do is just click this button to "reset."

## Online Store

### Running Locally

1. Starbuck Cards w and w/o Log
![onlinestore-app](onlinestore-images/sbc.png)
![onlinestore-app](onlinestore-images/sbclog.png)

2. Add Payment Form w Input after Clicked Add Money Button
![onlinestore-app](onlinestore-images/input.png)
![onlinestore-app](onlinestore-images/input1.png)

3. Error Message on View and Log
![onlinestore-app](onlinestore-images/mess.png)
![onlinestore-app](onlinestore-images/messlog.png)

4. Updated Input
![onlinestore-app](onlinestore-images/input2.png)

5. Updated Starbuck Cards w and w/o Log
![onlinestore-app](onlinestore-images/updatedsbc.png)
![onlinestore-app](onlinestore-images/updatedsbclog.png)

6. Cybersoure API Transaction
![onlinestore-app](onlinestore-images/cybersource.png)
![onlinestore-app](onlinestore-images/cybersource1.png)
![onlinestore-app](onlinestore-images/cybersource2.png)

7. H2 Console
![onlinestore-app](onlinestore-images/h2console.png)
![onlinestore-app](onlinestore-images/h2console1.png)

### GKE Deployment

1. Docker & DockerHub
![onlinestore-app](onlinestore-images/docker.png)
![onlinestore-app](onlinestore-images/dockerhub.png)

2. GKE
![onlinestore-app](onlinestore-images/cluster.png)
![onlinestore-app](onlinestore-images/workload.png)
![onlinestore-app](onlinestore-images/service.png)
![onlinestore-app](onlinestore-images/ingress.png)
![onlinestore-app](onlinestore-images/deployed.png)

#### Discussion

Requirement: 
- Online Store is using a "Real Credit Card" to Load Credits to the Starbucks Card.

Implementation:
- Our Online Store App has Add Payment Method Form for customers to fill out. It handles input error by showing the error message if the input is an incorrect format or the input is empty. 
- It used Cybersource API to check if a credit card is valid. It valid, then it automatically loads $172 to the Starbuck Card. 

Challenge: 
- One of the challenges is that we couldn't use the same MySQL database in the assigned time frame. Therefore, we don't really have Starbuck Cards in a database to store or load money to purchase the drinks.

## REST API

### Sample Request - REST API Deployed w/ Kong API Gateway

![REST API / Kong](cashiers-app-images/proofOfKongGateway.png)

### Discussion

- The final design of our REST API serves functionality for both Starbucks Cards as well as Starbucks Orders.
  - Starbucks Cards
    - Get all cards
    - Create a new card
    - Get specific card details
    - Activate a card
    - Delete all cards
  - Starbucks Orders
    - Create a new order
    - Get the active order for a specific register
    - Delete a register's active order
    - Pay for an order with an activated Starbucks card
    - Get a list of all active orders, for all registers
    - Delete all active orders, at all registers

### Kong API Gateway

- Kong was used to proxy our REST API service. Specifically, it was integrated to require authentication from the API client.

## Back Office 

### Running Locally

![Main Page](backoffice-images/main.png)
![Customer List](backoffice-images/customer-list.png)
![Customer Search](backoffice-images/customer-search.png)
![Search Result](backkoffice-images/search-result.png)

### Cloud Deployment
![Docker Image](backoffice-images/docker-image.png)
![]
### Discussion