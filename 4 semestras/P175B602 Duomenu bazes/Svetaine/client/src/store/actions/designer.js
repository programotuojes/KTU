import { RESET_DESIGNER, SET_DESIGNER, UPDATE_DESIGNER } from '../actionTypes';
import { API_URL } from '../../utils/network';
import { parseDesigner } from '../../utils/util';

export function updateDesigner(key, value) {
  return {
    type: UPDATE_DESIGNER,
    key,
    value,
  };
}

export function resetDesigner() {
  return {
    type: RESET_DESIGNER,
  };
}

export function fetchDesigner(id) {
  return (dispatch) => {
    fetch(`${API_URL}/designers/${id}`)
      .then((res) => {
        if (res.ok) return res.json();
        // TODO else show snackbar
      })
      .then((designer) => dispatch({ type: SET_DESIGNER, designer: parseDesigner(designer[0]) }))
      .catch((error) => console.log(error));
  };
}
