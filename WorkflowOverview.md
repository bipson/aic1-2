

# Introduction #
This page lists the workflows we have to create using our base service as described in ServiceOverview.

Each task in the BPMN diagrams that has cogs in the upper left corner directly maps to a base service call. I have not modeled the message flow throughout the workflows yet because they are not hard to get and lots of extra lines make them hard to read.

# Workflows #
## RegisterUser ##
This workflow is used to introduce a new company into our system. The database entry is set-up and initialized.

![https://aic1-2.googlecode.com/git/BPMN/RegisterUser.png](https://aic1-2.googlecode.com/git/BPMN/RegisterUser.png)

## UnregisterUser ##
This workflow is used to delete a company from our system. As Philip pointed out, it is only allowed to be removed if there are no pending payments.

![https://aic1-2.googlecode.com/git/BPMN/UnregisterUser.png](https://aic1-2.googlecode.com/git/BPMN/UnregisterUser.png)

## GetCurrentBill ##
This workflow is used by companies to check how their payment backlog holds up.

![https://aic1-2.googlecode.com/git/BPMN/GetCurrentBill.png](https://aic1-2.googlecode.com/git/BPMN/GetCurrentBill.png)

## PayCurrentBill ##
This workflow is used by companies to pay the bills. Notice that we first transfer the money (because this is the most likely to fail) and only then we change our database. If this goes wrong, we compensate for the transferred money by transferring it back. This way, we don't need a transaction.

![https://aic1-2.googlecode.com/git/BPMN/PayCurrentBill.png](https://aic1-2.googlecode.com/git/BPMN/PayCurrentBill.png)

## QuerySentiment ##
This is the heart of our service, the actual sentiment computation. Pretty straightforward (if nothing goes wrong along the way). I have decided to bill the user first, even if there is a chance that something fails, but this is debatable. :)

![https://aic1-2.googlecode.com/git/BPMN/QuerySentiment.png](https://aic1-2.googlecode.com/git/BPMN/QuerySentiment.png)