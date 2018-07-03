# revolut-transfer-master


This model starts with create a new customer.
After creating customer a domain event is triggered and an account
will be created that associated with customer.

After this process all given methods will called with this accounts 
id.

There is two domains here Customer and Account.

Customer and Account entities are Aggregate Roots.
Both of them can be unique by their ids.

Money is a value type. 



Build:

For creating standalone jar you can use gradle shadowJar comment.

For testing run  gradle  test
