# sayurbox-inventory-test
Implementation of SayurBox inventory test

Utilized: Spring Boot 2 with Maven, MySQL, JDK 1.8

How to run application for the first time:
- Create database with name "sbox"
- Run spring boot application with command "mvn spring-boot:run"
- Run following commands in sql script(location: /resources/sql_script.sql):
	1. use sbox database
	2. delete v_item in tables
	3. create item_view by execute query create item_view
	4. set global event_scheduler on
	5. create event expired_order scheduler

Requirement:
1. (Selecting) As a customer I can select item which I want, if stock is available
2. (Ordering) As a customer I can order items which I've already selected is stock available

Answer:
1. If we breakdown this requirement, we need the following APIs:
	- Get list available item,so I can select/add to cart the item which is available
	  - API: GET, {server}:{port}/api/item/get-list-available-items
	  - Postman: Item/Get list available item
	  - Note:
		 - I handled only item with available stock will show with query where available_stock is greater than 0	
	
	- Add item to cart, so I can place an order with my selected item(s)
	   - API: POST, {server}:{port}/api/order/add-to-cart (RequestBody sample in Postman)
	   - Postman: Order/Add item to my cart
	   - Note: 
		  - I handled only item with available stock can be selected/added to cart with validation
		  - For more handled situations please look the internal documentation i've written (OrderService.addToCart())

2. If we breakdown this requirement, we need the following APIs:
	- Get list item in cart, so I can see my item in the cart before checkout
	  - API: GET, {server}:{port}/api/order/get-my-cart
	  - Postman : Order/Get list item in my cart
	  - Note: 
	       - I handled the situation where the item will be ordered is available with add itemAvailable field in json response to mark if the item is available or not
	       - For more handled situations Please look the internal documentation in i've written in OrderService.getMyCart()
	
	- Place order/checkout the order, so I can order the items in my cart
	  - API: PUT, {server}:{port}/api/order/update-order-status (RequestBody sample in Postman)
	  - Postman: Order/Checkout
	  - Note:
		 - I handled situation where if there's an item with not sufficient stock(not available) it will throw an error
		 - For more handled situations please look the internal documentation i've written in OrderService.updateOrderStatus()

	- Get list my order, so I can choose which order that I want to confirm the payment
	  - API: GET, {server}:{port}/api/order/get-list-my-order
	  - Postman: Order/Get list my order

	- Confirm order payment, so I can confirm my selected order payment
	  - API: PUT, {server}:{port}/api/order/update-order-status (RequestBody sample in Postman)
	  - Postman: Order/Confirm payment
	  - Note:
		 - I handled situation where the order's payment due date is not expired
		 - I decreased the item actual_stock when the order payment has been confirmed
		 - For more handled situations please look the internal documentation i've written in OrderService.updateOrderStatus()

Situation must be handled:
1. Susan and Manda is ordering apple concurrently
2. Apple stock = 5, How do you serve Susan and Manda order?

Answer:
My humble solution is we do double check when the order is placed, 
if all the stock is available then customer can checkout and if not we can throw error message if the stock is not sufficient, this solution implented by giving the following order status:
- PENDING: where the order hasn't been checkout
- ORDER_RECEIVED: where the order has been checkout. Things happen in this status are:
	- Set payment expired date, we give the customer time to pay the order so if the order hasn't been paid yet the item will return to the inventory
	- Decrease the item stock, we decrease the item stock in the inventory, BUT customer can change their mind to cancel the order by not paying the order or cancelling it then we should update the item stock over and over again to it was before. In order to avoid that thing I made the solution, I created view for item data that contains column:
		- actual_stock: this column shows the actual stock in inventory, only changes when item stock is updated and people confirm their order payment
		- reserved_stock: this column shows the stock that has been reserved for people's order(order_status="ORDER_RECEIVED"), we can use it to show how many stock has been reserved by people's  order and use it as validation for if admin update the item stock, the stock must not be smaller than reserved stock
		- available_stock: this column shows the current stock, only change when people place or cancel an order. Used to show current stock to the customer. With the all column above we don't have to update the actual stock in inventory, all we need only update the order_status to cancel
- PAYMENT_RECEIVED: where the order has been paid

I also create scheduler in database layer to cancel the orders with status 'ORDER_RECEIVED' and expired payment due date it means the customer hasn't paid the order yet.

I know my solution is not the best it all depends on the business model and I think(as a junior) it's almost impossible for the server receiving and processing 2 same requests that use same resource at the same time, it will cause deadlock and it will ended in processing 1 request. So in the end who will get the apple between Susan and Manda is depending on who placed the order the first.

Note:
- Postman link: https://www.getpostman.com/collections/6ac6b374a9e10b115049 for example request, param, body
- All requests require authentication except for the authencation request such as login and register
- Users available:
	- username: superadmin, password: 123superadmin123
	- username: susan, password: 123susan123
	- username: manda, password: 123manda123
