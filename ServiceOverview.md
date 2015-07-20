

# Introduction #
We have not decided on Messaging / RPC yet, so I defined the services below using simple RPC mockup.

There are some changes I made to the service layout:
  * Twitter Service and Normalization Service are now 1 service. The reasoning is, because the [FetchTweets](ServiceOverview#Fetch_Tweets.md) method returns serialized Tweet objects, which is something very special and specific to Twitter, the Normalization, which takes Tweets and returns Strings, should also happen here. This is what the [ExtractText](ServiceOverview#Extract_Text_From_Tweets.md) method is for.
  * Sentiment and Sentiment Aggregator are now 1 service. Since computing and averaging sentiments is something so similar, you could say: Sentiment Service worked for 1 text, Sentiment Aggregator Service worked for N texts, which is the same class of responsibility for a service, there is now only a Sentiment Service housing both methods.


# JAXB Mappings and User Defined Types #

The following mappings from Java types to XML Schema types are defined by [JAXB](http://en.wikipedia.org/wiki/Java_Architecture_for_XML_Binding#Default_data_type_bindings). Notice that xsd:dateTime is mapped to javax.xml.datatype.XMLGregorianCalendar, so we have to use that one...

| **Java Type** | **XML Schema Type** |
|:--------------|:--------------------|
| String        | xsd:string          |
| Boolean       | xsd:boolean         |
| Double        | xsd:double          |
| javax.xml.datatype.XMLGregorianCalendar | xsd:dateTime        |
| Long          | xsd:long            |

**Definition of User Defined Types:**

_Tweet:_ (taken from [Twt Interface](http://twitter4j.org/en/javadoc/twitter4j/Twt.html))
```
  <xsd:complexType name="Tweet">
    <xsd:sequence>
      <xsd:element name="ID" type="xsd:long"/>
      <xsd:element name="CreatedAt" type="xsd:dateTime"/>
      <xsd:element name="Source" type="xsd:string"/>
      <xsd:element name="Text" type="xsd:string"/>
    </xsd:sequence>
  </xsd:complexType>
```

# Services #
## User Service ##
This service is responsible for managing user information in the database. There are only two services that access the database, the other one is the [Accounting Service](ServiceOverview#Accounting_Service.md).

### Add User To Database ###
`Boolean Add(String companyName, String password);`
  * _companyName:_
> > The name of the company to add, which is the user name throughout the application.
> > Must be at least 1 character long.
> > Must not be longer than 255 characters.
> > MySQL datatype: VARCHAR(255) NOT NULL PRIMARY KEY
  * _password:_
> > The password for the company to register.
> > Must be at least 5 characters long.
> > Must not be longer than 50 characters.
> > MySQL datatype: VARCHAR(50) NOT NULL
  * _Return Value:_
> > True, if the company has been registered in the application, False, if a company with the same name already existed.
  * _Faults:_
> > If there was a problem communicating with the database.


> The method first checks if the provided parameters fulfill the constraints.
> Then it tries to add a new company to the database.
> If 0 rows were affected, a company with the same already exists (no duplicates allowed by PRIMARY KEY constraint) and it returns False.
> If 1 row was affected, it returns True.

### Remove User From Database ###
`Boolean Remove(String companyName);`
  * _companyName:_
> > The name of the company that should be unregistered.
  * _Return Value:_
> > True, if the company has been removed from the application, False, if no company with the provided name existed.
  * _Faults:_
> > If there was a problem communicating with the database.


> The method tries to remove the provided company from the database.
> If 0 rows were affected, it returns False.
> If 1 row was affected, it returns True.
> More than 1 rows cannot be affected, since we perform an equality check on a PRIMARY KEY field.

### Check If User Exists In Database ###
`Boolean Exists(String companyName);`
  * _companyName:_
> > The name of the company to look up.
  * _Return Value:_
> > True, if the companyName is registered as a user, otherwise, False.
  * _Faults:_
> > If there was a problem communicating with the database.

### Check If User Credentials Are Valid ###
`Boolean CheckCredentials(String companyName, String password);`
  * _companyName:_
> > The name of the company to look up.
  * _password:_
> > The password for the company.
  * _Return Value:_
> > True if a company with the provided name and password is registered, otherwise False.
  * _Faults:_
> > If there was a problem communicating with the database.

## Accounting Service ##
This service is responsible for managing the amount a user has to pay in the database. It also provides a command to get the account data for our company. There are only two services accessing the database, the other one is the [User Service](ServiceOverview#User_Service.md).

### Get The Current Bill ###
`Double GetBill(String companyName);`
  * _companyName:_
> > The name of the company for which to get the bill.
  * _Return Value:_
> > NaN if the company name is not registered.
> > Otherwise, the current bill of the company.
  * _Faults:_
> > If there was a problem communicating with the database.


> The method tries to fetch the billing information from the database.
> If the result set is empty, it returns NaN.
> Otherwise, it returns the current bill.

### Charge A Given Amount ###
`Boolean Charge(String companyName, Double amount);`
  * _companyName:_
> > The name of the company to charge.
  * _amount:_
> > The amount of money to charge.
  * _Return Value:_
> > True, if the company has been charged, False, if:
    * The company name is not registered.
    * value is invalid (NaN, Infinity, ...)
    * Adding value to the bill would result in a negative bill.
  * _Faults:_
> > If there was a problem communicating with the database.


> At first, the method begins a transaction.
> Then, the method gets the current bill for the company.
> If the result set is empty, it returns False and rolls back the transaction.
> It then adds the value to the current bill.
> If the value is positive, this means we are charging for a query.
> If the value is negative, this means the user has paid for his queries.
> If the new bill is negative, we received more money than the user had queries. We return False and roll back the transaction.
> Otherwise, we update the bill, commit the transaction and return True.

### Get The Account Data Of Our System ###
`String GetOurAccountData( );`
  * _Return Value:_
> > Serialized information the payment service needs as the target account.
> > This is where we want the money our users transfer us to end up.
  * _Faults:_
> > If there was a problem communicating with the database.

## Payment Service ##
This service is responsible for transferring money from a user account to our account. It is trivially designed and allows transfer of a given amount of money from a source to a target account.

### Perform User Payment ###
`Boolean Pay(String ourAccountData, String userAccountData, Double amount);`
  * _ourAccountData:_
> > Serialized information the payment service needs to complete the transaction (The target account).
> > This is provided as a parameter so that the Payment Service never needs to access the database.
  * _userAccountData:_
> > Serialized information the payment service needs to complete the transaction (The source account).
  * _amount:_
> > The amount of money to transfer from the user account to our account.
  * _Return Value:_
> > True, if the transfer has succeeded, False, if no money has been transferred.


> This method establishes a connection to the payment service coded into the user account data and transfers
> the given amount of money from the user account to our account.
> If everything went OK, True is returned, otherwise, False.

## Twitter Service ##
This service is used to fetch tweets from Twitter. The tweets can be processes so only the contained text is left.

### Fetch Tweets ###
`Tweet[] FetchTweets(String searchString, XMLGregorianCalendar from, XMLGregorianCalendar to);`
  * _searchString:_
> > The text that has to be contained in each tweet.
  * _from:_
> > No tweet older than this value is regarded. The beginning of the timeframe.
  * _to:_
> > No tweet newer than this value is regarded. The end of the timeframe.
  * _Return Value:_
> > The fetched tweets which match the given constraints.
  * _Faults:_
> > The same faults the Twitter API exposes.

### Extract Text From Tweets ###
`String[] ExtractText(Tweet[] tweets);`
  * _tweets:_
> > A collection of tweets to normalize.
  * _Return Value:_
> > The extracted Text fields of the given tweets.


> This method cannot fail and is characterized by this Linq query:
> `from tweet in tweets select tweet.Text;`

### Attach Twitter Listener ###
`Boolean AttachListener(String filter);`
  * _filter:_
> > If a tweet contains this string, it is stored.
  * _Return Value:_
> > True, if the listener could be attached for the given filter string, otherwise, False.

### Detach Twitter Listener ###
`Boolean DetachListener(String filter);`
  * _filter:_
> > A previously attached filter for a listener.
  * _Return Value:_
> > True, if the listener could be detached for the given filter string, otherwise, False.

## Sentiment Service ##
This service is responsible for the actual computation of the sentiment of a given text. There can be multiple texts provided and the average sentiment is returned. Although it uses the algorithm for [Twitter sentiment analysis](https://code.google.com/p/twitter-sentiment-analysis/), it can theoretically be used to compute the sentiment of arbitrary strings.

### Compute Sentiment For Single Text ###
`Double Compute(String text);`
  * _text:_
> > The text for which to compute the sentiment.
  * _Return Value:_
> > A value between 0 (bad) and 1 (good).


> This involves executing the sentiment code given as a topic requirement.

### Compute Average Sentiment For Multiple Texts ###
`Double ComputeAverage(String[] texts);`
  * _texts:_
> > The texts for which to compute the sentiment.
  * _Return Value:_
> > If texts contains nothing, NaN is returned.
> > Otherwise, a value between 0 (overall bad) and 1 (overall good).


> This method uses the Compute method above to get the sentiment for every item contained in texts.
> If texts is empty, NaN is returned.
> Initialize the current sentiment with 0.
> Compute the next partial sentiment value, divide it by the number of texts, and add it to the current sentiment.
> Repeat last step until all texts have been processed.
> Return the current sentiment.