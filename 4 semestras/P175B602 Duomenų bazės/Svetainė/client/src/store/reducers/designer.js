import { RESET_DESIGNER, SET_DESIGNER, UPDATE_DESIGNER } from '../actionTypes';
import { DESIGNER_TEMPLATE } from '../../utils/constants';

export default function orderReducer(state = DESIGNER_TEMPLATE, action) {
  switch (action.type) {
    case SET_DESIGNER:
      return action.designer;

    case UPDATE_DESIGNER:
      return {
        ...state,
        [action.key]: action.value,
      };

    case RESET_DESIGNER:
      return DESIGNER_TEMPLATE;

    default:
      return state;
  }
}
