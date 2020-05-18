import {
  ADD_ORDER_DETAIL,
  DELETE_ORDER_DETAIL,
  RESET_ORDER_DETAILS,
  SET_ORDER_DETAILS,
  UPDATE_ORDER_DETAIL,
} from '../actionTypes';
import { ORDER_DETAIL_TEMPLATE } from '../../utils/constants';

const initialState = {
  details: [ORDER_DETAIL_TEMPLATE],
};

export default function orderDetailsReducer(state = initialState, action) {
  let details;

  switch (action.type) {
    case ADD_ORDER_DETAIL:
      return {
        ...state,
        details: [...state.details, ORDER_DETAIL_TEMPLATE],
      };

    case SET_ORDER_DETAILS:
      return {
        ...state,
        details: action.details,
      };

    case UPDATE_ORDER_DETAIL:
      details = [...state.details];
      details[action.index] = {
        ...details[action.index],
        [action.key]: action.value,
      };
      return {
        ...state,
        details,
      };

    case DELETE_ORDER_DETAIL:
      details = [...state.details];
      details.splice(action.index, 1);
      return {
        ...state,
        details,
      };

    case RESET_ORDER_DETAILS:
      return initialState;

    default:
      return state;
  }
}
