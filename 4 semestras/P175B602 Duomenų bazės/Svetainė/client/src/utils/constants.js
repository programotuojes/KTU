export const STATUS = ['Awaiting payment', 'Shipping', 'Done'];
export const TYPES = ['Pants', 'T-shirt', 'Hat', 'Skirt', 'Underwear', 'Jacket', 'Shoes'];

export const ORDER_TEMPLATE = {
  date_ordered: new Date().toISOString().slice(0, 16),
  date_shipped: '',
  ship_to: '',
  status: STATUS[0],
  comments: '',
  customer_id: '',
  shop_id: '',
};

export const ORDER_DETAIL_TEMPLATE = {
  order_id: null,
  clothes_id: '',
  quantity: '',
  price_each: '',
  amount_refunded: '',
  refund_reason: '',
};

export const DESIGNER_TEMPLATE = {
  name: '',
  surname: '',
  country: '',
  awards_won: '',
  birthday: '',
};

export const DESIGNER_CLOTHES_TEMPLATE = {
  name: '',
  description: '',
  msrp: '',
  buy_price: '',
  quantity_in_stock: '',
  type: '',
  designer_id: '',
  style_id: '',
  provider_id: '',
};

export const EMPLOYEE_TEMPLATE = {
  reports_to: '',
  name: '',
  surname: '',
  phone: '',
  birthday: '',
  address: '',
  bank_account: '',
  shop_id: '',
};
