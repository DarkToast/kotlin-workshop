## Task 1: Manage products

- Add the ability to create and update products.
- Validate the product and enforce data integrity constraints:
  - articleNo: required, not empty, unique
  - Name: required, not empty
  - EANs: At least one, non-empty item
- URL should be `/products/{articleNo}`  
  
## Task 2: Prices for products

- Extend the `Product` class with information about its price.
- Add validation for the new attribute: required, greater than 0
- Store the price in the database.

## Task 3: Manage carts

- Add the ability to manage carts.
  A user should be able to:
  - Create a new cart
  - Update their cart
  - Get their current cart
- Requirements for a cart:
  - Contains a non-empty list of items
  - Each item can have a specific quantity
  - Includes the total price of all items
- URL should be `/user/cart`

## Task 4: Enable multi user support

- Add the ability to manage multi users.
  - A user can be registered
  - A user can be deleted
  - No authentication is needed
- Requirements for a user:
  - A user uuid
  - A user name
- URL should be `/user/{userUuid}`
  - e.g for the shopping cart access: `/user/{userUuid}/cart`