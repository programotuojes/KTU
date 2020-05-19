import { RESET_EMPLOYEE, SET_EMPLOYEE, UPDATE_EMPLOYEE } from '../actionTypes';
import { apiUrl } from '../../utils/network';
import { parseEmployee } from '../../utils/util';

export function updateEmployee(key, value) {
  return {
    type: UPDATE_EMPLOYEE,
    key,
    value,
  };
}

export function resetEmployee() {
  return {
    type: RESET_EMPLOYEE,
  };
}

export function fetchEmployee(id) {
  return (dispatch) => {
    fetch(`${apiUrl}/employees/${id}`)
      .then((res) => {
        if (res.ok) return res.json();
        // TODO else show snackbar
      })
      .then((employee) => dispatch({ type: SET_EMPLOYEE, employee: parseEmployee(employee[0]) }))
      .catch((error) => console.log(error));
  };
}
