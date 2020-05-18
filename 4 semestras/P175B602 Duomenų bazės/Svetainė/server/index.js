const express = require('express');
const db = require('./mysql');
const cors = require('cors');
const app = express();
app.use(cors());
app.use(express.json());

// Route imports
// const clothesRoutes = require('./routes/clothes');

// Routes
// app.use('/clothes', clothesRoutes);

app.get('/api/employees/names', (req, res) => {
  db.query(db.getEmployeeNames, null, res);
});

app.get('/api/designers/names', (req, res) => {
  db.query(db.getDesignerNames, null, res);
});

app.get('/api/shops/names', (req, res) => {
  db.query(db.getShopNames, null, res);
});

app.get('/api/customers/names', (req, res) => {
  db.query(db.getCustomerNames, null, res);
});

app.get('/api/clothes/names', (req, res) => {
  db.query(db.getClothesNames, null, res);
});

app.get('/api/styles/names', (req, res) => {
  db.query(db.getStyleNames, null, res);
});

app.get('/api/providers/names', (req, res) => {
  db.query(db.getProviderNames, null, res);
});

app.post('/api/add/clothes', (req, res) => {
  const designerId = req.body.clothes[0].designer_id;
  if (!Number(req.body.clothes[0].designer_id)) req.body.clothes[0].designer_id = null;

  db.query(db.addClothes, [Object.values(db.parseClothes(req.body, designerId))], res);
});

app.put('/api/edit/clothes/:id', (req, res) => {
  const id = req.params.id;
  db.query(db.setClothes, [db.parseClothesToAdd(req.body.clothes), id], res);
});

app.post('/api/add/employees', (req, res) => {
  db.query(db.addEmployee, [[Object.values(db.parseEmployee(req.body))]], res);
});

app.put('/api/edit/employees', (req, res) => {
  const id = req.body.employee.id;
  db.query(db.setEmployee, [db.parseEmployee(req.body), id], res);
});

app.post('/api/add/styles', (req, res) => {
  db.query(db.addStyle, Object.values(req.body), res);
});

app.post('/api/add/designers', (req, res) => {
  db.saveDesigner(req.body, res);
});


app.post('/api/add/orders', (req, res) => {
  db.saveOrder(req.body, res);
});

app.put('/api/edit/orders', (req, res) => {
  db.updateOrder(req.body, res);
});

app.post('/api/add/designer', (req, res) => {
  db.query(db.addDesigner, [[Object.values(db.parseDesigner(req.body))]], res);
});

app.put('/api/edit/designer', (req, res) => {
  const id = req.body.designer.id;
  db.query(db.setDesigner, [db.parseDesigner(req.body), id], res);
});

app.get('/api/order_detail/:id', (req, res) => {
  db.query(db.getOrderDetails, [req.params.id], res);
});

app.get('/api/:table', (req, res) => {
  db.query(db.getAll, req.params.table, res);
});

app.get('/api/:table/column_names', (req, res) => {
  db.query(db.getColumnNames, [req.params.table], res);
});

app.get('/api/clothes/types', (req, res) => {
  db.query(db.getClothingTypes, null, res);
});

app.get('/api/:table/:id', (req, res) => {
  db.query(db.getById, [req.params.table, req.params.id], res);
});

app.delete('/api/:table/:id', (req, res) => {
  db.query(db.deleteById, [req.params.table, req.params.id], res);
});

app.listen(3001);
