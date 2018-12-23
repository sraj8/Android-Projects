# Bind-Service Implementation
Developed two android applications as part of mobile development course at University of Illinois at Chicago

### FedMoneyClient
FederalMoneyClient takes as input a date from the device user and provides the following three functionalities to fetch data from database maintained by the US
Department of the Treasury:
1. Takes a particular year as input from 2006 to 2016 (both inclusive) and returns a list of values denoting the cash the US government had at the opening of each month of the input year
2. Takes a particular year, month, day and a number denoting the number of working days and fetches the values denoting cash the US government has at the opening of the specified day and the subsequent days denoted by the fourth parameter

### FedMoneyServer
The service in the FederalMoneyServer app defines a simple API,for use by FederalMoneyClient. Upon receiving an API call from a client, FederalMoneyServer appropriately creates and formats a query to be forwarded to the treasury.io site and forwards the response it recieves to the client. The API exposes two remote methods - monthlyAvgCash(int aYear) and dailyCash(int aYear, int aMonth, int aDay, int aNumber).

AIDL is used to expose the service’s functionality to the client
