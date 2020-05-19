import {
  ADD_DESIGNER_CLOTHING,
  DELETE_DESIGNER_CLOTHING,
  RESET_DESIGNER_CLOTHES, SET_DESIGNER_CLOTHING,
  UPDATE_DESIGNER_CLOTHING
} from '../actionTypes';
import { DESIGNER_CLOTHES_TEMPLATE } from '../../utils/constants';

const initialState = {
  clothes: [DESIGNER_CLOTHES_TEMPLATE],
};

export default function designerClothesReducer(state = initialState, action) {
  let clothes;

  switch (action.type) {
    case ADD_DESIGNER_CLOTHING:
      return {
        ...state,
        clothes: [...state.clothes, DESIGNER_CLOTHES_TEMPLATE],
      };

    case UPDATE_DESIGNER_CLOTHING:
      clothes = [...state.clothes];
      clothes[action.index] = {
        ...clothes[action.index],
        [action.key]: action.value,
      };
      return {
        ...state,
        clothes,
      };

    case DELETE_DESIGNER_CLOTHING:
      clothes = [...state.clothes];
      clothes.splice(action.index, 1);
      return {
        ...state,
        clothes,
      };

    case RESET_DESIGNER_CLOTHES:
      return initialState;

    case SET_DESIGNER_CLOTHING:
      return {
        ...state,
        clothes: action.clothes,
      };

    default:
      return state;
  }
}
