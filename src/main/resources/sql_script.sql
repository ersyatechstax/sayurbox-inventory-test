use sbox;

drop table v_item;

/*---------------------------Item view---------------------------*/
create or replace view v_item
as
select
i.id,
i.version,
i.secure_id,
i.name,
i.description,
i.stock as actual_stock,
cast(ifnull((select sum(od.quantity) from items it left join order_details od on it.id = od.item_id join orders o on od.order_id = o.id
where o.order_status = 'ORDER_RECEIVED' and it.id = i.id
group by it.name),0) as unsigned) as reserved_stock,
cast(i.stock-ifnull((select sum(od.quantity) from items it left join order_details od on it.id = od.item_id join orders o on od.order_id = o.id
where o.order_status = 'ORDER_RECEIVED' and it.id = i.id
group by it.name),0) as unsigned) as available_stock
from items i
/*----------------------------------------------------------------*/

SET GLOBAL event_scheduler = on;

/*------------------Order status update scheduler-----------------*/
create event expired_order
on schedule
every
1 minute
do
update orders o
set o.order_status = 'CANCELLED'
where o.order_status = 'ORDER_RECEIVED' and o.payment_due_date < now()
/*----------------------------------------------------------------*/