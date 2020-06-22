import { RESET_ORDER, SET_ORDER, UPDATE_ORDER } from '../actionTypes';
import { API_URL } from '../../utils/network';
import { parseOrder } from '../../utils/util';

export function updateOrder(key, value) {
  return {
    type: UPDATE_ORDER,
    key,
    value,
  };
}

export function resetOrder() {
  return {
    type: RESET_ORDER,
  };
}

export function fetchOrder(id) {
  return (dispatch) => {
    fetch(`${API_URL}/orders/${id}`)
      .then((res) => {
        if (res.ok) return res.json();
        // TODO else show snackbar
      })
      .then((order) => dispatch({ type: SET_ORDER, order: parseOrder(order[0]) }))
      .catch((error) => console.log(error));
  };
}
