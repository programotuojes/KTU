import {
  ADD_DESIGNER_CLOTHING,
  DELETE_DESIGNER_CLOTHING,
  RESET_DESIGNER_CLOTHES,
  SET_DESIGNER_CLOTHING,
  UPDATE_DESIGNER_CLOTHING,
} from '../actionTypes';
import { API_URL } from '../../utils/network';
import { parseClothes } from '../../utils/util';

export function addDesignerClothing() {
  return {
    type: ADD_DESIGNER_CLOTHING,
  };
}

export function updateDesignerClothing(index, key, value) {
  return {
    type: UPDATE_DESIGNER_CLOTHING,
    index,
    key,
    value,
  };
}

export function deleteDesignerClothing(index) {
  return {
    type: DELETE_DESIGNER_CLOTHING,
    index,
  };
}

export function resetDesignerClothing() {
  return {
    type: RESET_DESIGNER_CLOTHES,
  };
}

export function fetchClothes(id) {
  return (dispatch) => {
    fetch(`${API_URL}/clothes/${id}`)
      .then((res) => {
        if (res.ok) return res.json();
        // TODO else show snackbar
      })
      .then((clothes) =>
        dispatch({ type: SET_DESIGNER_CLOTHING, clothes: [parseClothes(clothes[0])] })
      )
      .catch((error) => console.log(error));
  };
}
