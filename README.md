# Team C Journal 
## Architecture Diagram

## Cashier's App

### Screenshots

![Cashier's App](cashiers-app-images/cashiersApp.png)

![Cashier's App 2](cashiers-app-images/cashiersApp2.png)

### Discussion

- For the Starbucks Cashier's Application, we enabled the barista to have easy access to customizing an order for the customer. They can choose from five different drink options, five different milk choices, and four different drink sizes. The first image above shows exactly this, which is what the barista will see upon page landing. The barista can also quickly set at which location the order is being made, by using the store selector placed at the top-right corner of the fixed navigation bar.
- After the barista has finished adding an order to the register, they will see what is shown in the second screenshot above. From here, the barista has three options:
  1. Get Order
     - This is used to get the active order at there selected store register, if available. Upon an "Add to Order" action, this action is performed automatically. If an order had been added previously, then the barista will need to click this button to get the active order.
  2. Place Order
     - When the barista is ready to complete the transaction, they will need to simply input the customer's Starbucks Card number, and then click this button to finalize the order.
  3. Delete Order
     - If the barista would like to clear the active order for the selected register, then all they have to do is just click this button to "reset."

## Online Store - Running Locally

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

### Discussion

## REST API

## Integrations

## Cloud Deployments

### Online Store - Deployed

1. Docker & DockerHub
![onlinestore-app](onlinestore-images/docker.png)
![onlinestore-app](onlinestore-images/dockerhub.png)

2. GKE
![onlinestore-app](onlinestore-images/cluster.png)
![onlinestore-app](onlinestore-images/workload.png)
![onlinestore-app](onlinestore-images/service.png)
![onlinestore-app](onlinestore-images/ingress.png)
![onlinestore-app](onlinestore-images/deployed.png)