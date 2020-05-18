function parseMoney(amount) {
  return (amount / 100).toFixed(2);
}

export function getAlignment(column) {
  switch (column) {
    case 'id':
    case 'card_number':
    case 'phone':
    case 'age':
    case 'bank_account':
    case 'reports_to':
    case 'shop_id':
    case 'designer_id':
    case 'style_id':
    case 'provider_id':
    case 'customer_id':
    case 'order_id':
    case 'clothes_id':
    case 'msrp':
    case 'buy_price':
    case 'price_each':
    case 'quantity':
    case 'quantity_in_stock':
    case 'amount_refunded':
    case 'awards_won':
      return 'right';
    case 'opening_time':
    case 'closing_time':
      return 'center';
    default:
      return 'inherit';
  }
}

export function formatData(column, data) {
  switch (column) {
    case 'amount_refunded':
      if (!data) return 'Not refunded';
    // Falls through
    case 'msrp':
    case 'buy_price':
    case 'price_each':
      return parseMoney(data) + ' €';
    case 'date_ordered':
    case 'date_shipped':
      if (data) return new Date(data).toLocaleString();
      else return 'Not shipped';
    case 'birthday':
      return new Date(data).toLocaleDateString();
    default:
      return data;
  }
}

export function formatTitle(text) {
  if (text.toString() === 'msrp') return 'MSRP';

  return text.toString().replace(/_/g, ' ').replace(/(id)$/g, 'ID');
}

export function parseOrder(order) {
  if (!order.comments) order.comments = '';
  if (!order.shop_id) order.shop_id = '';
  if (!order.date_shipped) order.date_shipped = '';
  else order.date_shipped = order.date_shipped.slice(0, -8);
  order.date_ordered = order.date_ordered.slice(0, -8);

  return order;
}

export function parseOrderDetails(orderDetails) {
  return orderDetails.map((item) => {
    item.price_each = parseMoney(item.price_each);

    if (item.amount_refunded) item.amount_refunded = parseMoney(item.amount_refunded);
    else item.amount_refunded = '';

    if (!item.refund_reason) item.refund_reason = '';

    return item;
  });
}

export function parseDesigner(designer) {
  if (!designer.birthday) designer.birthday = '';
  else designer.birthday = designer.birthday.slice(0, -14);

  return designer;
}

export function parseEmployee(employee) {
  if (!employee.reports_to) employee.reports_to = '';
  if (!employee.shop_id) employee.shop_id = '';

  if (!employee.birthday) employee.birthday = '';
  else employee.birthday = employee.birthday.slice(0, -14);

  return employee;
}

export function parseClothes(clothes) {
  if (!clothes.quantity_in_stock) clothes.quantity_in_stock = '';

  clothes.msrp = parseMoney(clothes.msrp);
  clothes.buy_price = parseMoney(clothes.buy_price);

  return clothes;
}
