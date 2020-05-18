export const apiUrl = 'http://localhost:3001/api';

export function get(path, setData) {
  fetch(apiUrl + path)
    .then((res) => res.json())
    .then((data) => setData(data))
    .catch((error) => console.log(error));
}

export async function addOrder(order, orderDetails) {
  return await fetch(apiUrl + '/add/orders', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ order, orderDetails }),
  });
}

export async function updateOrder(order, orderDetails) {
  return fetch(apiUrl + '/edit/orders', {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ order, orderDetails }),
  });
}

export async function addDesigner(designer, clothes) {
  return await fetch(apiUrl + '/add/designers', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ designer, clothes }),
  });
}

export async function addOneDesigner(designer) {
  return await fetch(apiUrl + '/add/designer', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ designer }),
  });
}

export async function updateOneDesigner(designer) {
  return await fetch(apiUrl + '/edit/designer', {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ designer }),
  });
}

export async function addEmployee(employee) {
  return await fetch(apiUrl + '/add/employees', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ employee }),
  });
}

export async function updateEmployee(employee) {
  return await fetch(apiUrl + '/edit/employees', {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ employee }),
  });
}

export async function addClothes(clothes) {
  return await fetch(apiUrl + '/add/clothes', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ clothes: [clothes] }),
  });
}

export async function updateClothes(clothes, id) {
  return await fetch(apiUrl + '/edit/clothes/' + id, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ clothes }),
  });
}
