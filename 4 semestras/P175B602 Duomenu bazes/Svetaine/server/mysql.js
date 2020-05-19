const mysql = require('mysql');

const connection = mysql.createConnection({
  host: 'localhost',
  user: 'root',
  password: '',
  database: 'nuostabus_modulis',
});

function executeQuery(sql, values, response) {
  connection.query(sql, values, (error, results) => {
    if (error) {
      console.log(error);
      response.status(400).send(error.sqlMessage);
      return;
    }

    response.send(results);
  });
}

function parseEmployee(values) {
  delete values.employee.id;

  if (values.employee.name) values.employee.name = values.employee.name.trim();
  if (values.employee.surname) values.employee.surname = values.employee.surname.trim();
  if (values.employee.bank_account)
    values.employee.bank_account = values.employee.bank_account.trim();
  if (values.employee.address) values.employee.address = values.employee.address.trim();

  if (!values.employee.name) values.employee.name = null;
  if (!values.employee.surname) values.employee.surname = null;
  if (!values.employee.bank_account) values.employee.bank_account = null;
  if (!values.employee.address) values.employee.address = null;
  if (!values.employee.phone) values.employee.phone = null;
  if (!values.employee.reports_to) values.employee.reports_to = null;
  if (!values.employee.shop_id) values.employee.shop_id = null;

  if (!new Date(values.employee.birthday).getTime()) values.employee.birthday = null;

  return values.employee;
}

function parseOrder(values) {
  delete values.order.id;

  if (values.order.comments) values.order.comments = values.order.comments.trim();
  if (values.order.ship_to) values.order.ship_to = values.order.ship_to.trim();

  if (!values.order.ship_to) values.order.ship_to = null;
  if (!values.order.comments) values.order.comments = null;

  if (!values.order.customer_id) values.order.customer_id = null;
  if (!values.order.shop_id) values.order.shop_id = null;
  if (!new Date(values.order.date_ordered).getTime()) values.order.date_ordered = null;
  if (!new Date(values.order.date_shipped).getTime()) values.order.date_shipped = null;

  return values.order;
}

function parseOrderDetails(values, orderId) {
  return values.orderDetails.map((item) => {
    item.order_id = Number(orderId);

    if (!Number(item.clothes_id)) item.clothes_id = null;
    if (!Number(item.quantity)) item.quantity = null;
    if (!Number(item.price_each)) item.price_each = null;
    else item.price_each = Math.round(item.price_each * 100);
    if (!Number(item.amount_refunded)) item.amount_refunded = null;
    else item.amount_refunded = Math.round(item.amount_refunded * 100);
    if (!item.refund_reason) item.refund_reason = null;

    return Object.values(item);
  });
}

function parseDesigner(values) {
  delete values.designer.id;

  if (values.designer.name) values.designer.name = values.designer.name.trim();
  if (values.designer.surname) values.designer.surname = values.designer.surname.trim();
  if (values.designer.country) values.designer.country = values.designer.country.trim();

  if (!values.designer.name) values.designer.name = null;
  if (!values.designer.surname) values.designer.surname = null;
  if (!values.designer.country) values.designer.country = null;

  if (!values.designer.awards_won) values.designer.awards_won = 0;
  if (!new Date(values.designer.birthday).getTime()) values.designer.birthday = null;

  return values.designer;
}

function parseDesignerClothes(values, designer_id) {
  return values.clothes.map((item) => {
    item.designer_id = Number(designer_id);

    if (item.name) item.name = item.name.trim();
    if (item.description) item.description = item.description.trim();

    if (!item.name) item.name = null;
    if (!item.description) item.description = null;

    if (!Number(item.msrp)) item.msrp = null;
    else item.msrp = Math.round(item.msrp * 100);

    if (!Number(item.buy_price)) item.buy_price = null;
    else item.buy_price = Math.round(item.buy_price * 100);

    if (!Number(item.quantity_in_stock)) item.quantity_in_stock = null;
    if (!Number(item.style_id)) item.style_id = null;
    if (!Number(item.provider_id)) item.provider_id = null;
    if (!Number(item.designer_id)) item.designer_id = null;
    if (!item.type) item.type = null;

    return Object.values(item);
  });
}

function parseClothesToAdd(item) {
  if (item.name) item.name = item.name.trim();
  if (item.description) item.description = item.description.trim();

  if (!item.name) item.name = null;
  if (!item.description) item.description = null;

  if (!Number(item.msrp)) item.msrp = null;
  else item.msrp = Math.round(item.msrp * 100);

  if (!Number(item.buy_price)) item.buy_price = null;
  else item.buy_price = Math.round(item.buy_price * 100);

  if (!item.quantity_in_stock) item.quantity_in_stock = null;
  if (!Number(item.style_id)) item.style_id = null;
  if (!Number(item.designer_id)) item.designer_id = null;
  if (!Number(item.provider_id)) item.provider_id = null;
  if (!Number(item.designer_id)) item.designer_id = null;
  if (!item.type) item.type = null;

  return item;
}

function saveDesigner(values, response) {
  connection.beginTransaction((error) => {
    if (error) {
      connection.rollback();
      console.log(error);
      response.status(400).send(error.sqlMessage);
      return;
    }

    connection.query(addDesigner, [[Object.values(parseDesigner(values))]], (error, results) => {
      if (error) {
        connection.rollback();
        console.log(error);
        response.status(400).send(error.sqlMessage);
        return;
      }

      connection.query(addClothes, [parseDesignerClothes(values, results.insertId)], (error) => {
        if (error) {
          connection.rollback();
          console.log(error);
          response.status(400).send(error.sqlMessage);
          return;
        }

        connection.commit((error) => {
          if (error) {
            connection.rollback();
            console.log(error);
            response.status(400).send(error.sqlMessage);
            return;
          }

          response.send('Success!');
        });
      });
    });
  });
}

function saveOrder(values, response) {
  connection.beginTransaction((error) => {
    if (error) {
      connection.rollback();
      console.log(error);
      response.status(400).send(error.sqlMessage);
      return;
    }

    connection.query(addOrder, [[Object.values(parseOrder(values))]], (error, results) => {
      if (error) {
        connection.rollback();
        console.log(error);
        response.status(400).send(error.sqlMessage);
        return;
      }

      connection.query(addOrderDetails, [parseOrderDetails(values, results.insertId)], (error) => {
        if (error) {
          connection.rollback();
          console.log(error);
          response.status(400).send(error.sqlMessage);
          return;
        }

        connection.commit((error) => {
          if (error) {
            connection.rollback();
            console.log(error);
            response.status(400).send(error.sqlMessage);
            return;
          }

          response.send('Success!');
        });
      });
    });
  });
}

function updateOrder(values, response) {
  const orderId = values.order.id;

  connection.beginTransaction((error) => {
    if (error) {
      connection.rollback();
      console.log(error);
      response.status(400).send(error.sqlMessage);
      return;
    }

    connection.query(setOrder, [parseOrder(values), orderId], (error) => {
      if (error) {
        connection.rollback();
        console.log(error);
        response.status(400).send(error.sqlMessage);
        return;
      }

      connection.query(deleteOrderDetails, [orderId], (error) => {
        if (error) {
          connection.rollback();
          console.log(error);
          response.status(400).send(error.sqlMessage);
        }
      });

      connection.query(addOrderDetails, [parseOrderDetails(values, orderId)], (error) => {
        if (error) {
          connection.rollback();
          console.log(error);
          response.status(400).send(error.sqlMessage);
          return;
        }

        connection.commit((error) => {
          if (error) {
            connection.rollback();
            console.log(error);
            response.status(400).send(error.sqlMessage);
            return;
          }

          response.send('Success!');
        });
      });
    });
  });
}

exports.getAll = 'SELECT * FROM ??';
exports.getById = 'SELECT * FROM ?? WHERE id = ?';
exports.getColumnNames = 'SELECT COLUMN_NAME FROM information_schema.COLUMNS WHERE TABLE_NAME = ?';
exports.deleteById = 'DELETE FROM ?? WHERE id = ?';

exports.addStyle = 'INSERT INTO styles (year, name, description) VALUES (?, ?, ?)';
exports.addEmployee =
  'INSERT INTO employees (reports_to, name, surname, phone, birthday, address, bank_account, shop_id) VALUES ?';
const addOrder =
  'INSERT INTO orders (date_ordered, date_shipped, ship_to, status, comments, customer_id, shop_id) VALUES ?';
const addOrderDetails =
  'INSERT INTO order_detail (order_id, clothes_id, quantity, price_each, amount_refunded, refund_reason) VALUES ?';

const addDesigner = 'INSERT INTO designers (name, surname, country, awards_won, birthday) VALUES ?';
const addClothes =
  'INSERT INTO clothes (name, description, msrp, buy_price, quantity_in_stock, type, designer_id, style_id, provider_id) VALUES ?';

const setOrder = 'UPDATE orders SET ? WHERE id = ?';
const setDesigner = 'UPDATE designers SET ? WHERE id = ?';
const deleteOrderDetails = 'DELETE FROM order_detail WHERE order_id = ?';
const setEmployee = 'UPDATE employees SET ? WHERE id = ?';
const setClothes = 'UPDATE clothes SET ? WHERE id = ?';

exports.getOrderDetails = 'SELECT * FROM order_detail WHERE order_id = ?';

exports.getEmployeeNames = 'SELECT id, name, surname FROM employees';
exports.getShopNames = 'SELECT id, country, city, address FROM shops';
exports.getCustomerNames = 'SELECT id, name, surname FROM customers';
exports.getClothesNames = 'SELECT id, name, type FROM clothes';
exports.getStyleNames = 'SELECT id, name, year FROM styles';
exports.getProviderNames = 'SELECT id, name, country FROM providers';
exports.getDesignerNames = 'SELECT id, name, surname FROM designers';

exports.getClothingTypes =
  "SELECT COLUMN_TYPE FROM information_schema.COLUMNS WHERE TABLE_NAME = 'clothes' AND COLUMN_NAME = 'type'";

exports.query = executeQuery;
exports.saveOrder = saveOrder;
exports.updateOrder = updateOrder;
exports.saveDesigner = saveDesigner;
exports.parseDesigner = parseDesigner;
exports.parseClothes = parseDesignerClothes;
exports.parseClothesToAdd = parseClothesToAdd;
exports.parseEmployee = parseEmployee;

exports.addDesigner = addDesigner;
exports.setDesigner = setDesigner;
exports.setEmployee = setEmployee;
exports.setClothes = setClothes;
exports.addClothes = addClothes;
