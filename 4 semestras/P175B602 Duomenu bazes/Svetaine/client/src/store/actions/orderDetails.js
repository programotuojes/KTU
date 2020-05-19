import {
  ADD_ORDER_DETAIL,
  DELETE_ORDER_DETAIL,
  RESET_ORDER_DETAILS,
  SET_ORDER_DETAILS,
  UPDATE_ORDER_DETAIL,
} from '../actionTypes';
import { apiUrl } from '../../utils/network';
import { parseOrderDetails } from '../../utils/util';

export function addOrderDetail() {
  return {
    type: ADD_ORDER_DETAIL,
  };
}

export function updateOrderDetail(index, key, value) {
  return {
    type: UPDATE_ORDER_DETAIL,
    index,
    key,
    value,
  };
}

export function deleteOrderDetail(index) {
  return {
    type: DELETE_ORDER_DETAIL,
    index,
  };
}

export function resetOrderDetails() {
  return {
    type: RESET_ORDER_DETAILS,
  };
}

export function fetchOrderDetails(order_id) {
  return (dispatch) => {
    // TODO check if res.ok
    fetch(`${apiUrl}/order_detail/${order_id}`)
      .then((res) => res.json())
      .then((details) => dispatch({ type: SET_ORDER_DETAILS, details: parseOrderDetails(details) }))
      .catch(() => dispatch({ type: SET_ORDER_DETAILS, details: [] }));
  };
}
