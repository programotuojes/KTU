import { RESET_ORDER, SET_ORDER, UPDATE_ORDER } from '../actionTypes';
import { ORDER_TEMPLATE } from '../../utils/constants';

export default function orderReducer(state = ORDER_TEMPLATE, action) {
  switch (action.type) {
    case SET_ORDER:
      return action.order;

    case UPDATE_ORDER:
      return {
        ...state,
        [action.key]: action.value,
      };

    case RESET_ORDER:
      return ORDER_TEMPLATE;

    default:
      return state;
  }
}
